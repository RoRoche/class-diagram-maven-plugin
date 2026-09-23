/*
 * MIT License
 *
 * Copyright (c) 2026 Romain Rochegude
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.roroche.classdiagram;

import com.github.roroche.classdiagram.matchers.MojoExecutionHasCause;
import com.github.roroche.classdiagram.matchers.MojoExecutionHasMessage;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link GenerateMojo}.
 *
 * @since 0.0.1
 */
// @checkstyle ReturnCountCheck (400 lines)
@SuppressWarnings({
    "PMD.AvoidAccessibilityAlteration",
    "PMD.OnlyOneReturn",
    "PMD.UnnecessaryLocalRule"
})
final class GenerateMojoTest {

    @Test
    void rejectsMissingPackages(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = GenerateMojoTest.mojo(temp, new MavenProject());
        MatcherAssert.assertThat(
            "Missing packages should fail generation",
            GenerateMojoTest.failure(mojo),
            new MojoExecutionHasMessage(
                Matchers.is("Cannot generate class diagram")
            )
        );
    }

    @Test
    void rejectsEmptyPerPackageConfiguration(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = GenerateMojoTest.mojo(
            temp,
            new GenerateMojoTest.BrokenProject()
        );
        GenerateMojoTest.set(mojo, "perPackage", true);
        final MojoExecutionException failure = GenerateMojoTest.failure(mojo);
        MatcherAssert.assertThat(
            "Empty per-package configuration should fail generation",
            failure,
            Matchers.allOf(
                new MojoExecutionHasMessage(
                    Matchers.is("Cannot generate class diagram")
                ),
                new MojoExecutionHasCause(
                    Matchers.hasProperty(
                        "message",
                        Matchers.is("Configure at least one package to analyze")
                    )
                )
            )
        );
    }

    @Test
    void rejectsNamedDiagramWithoutPackages(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = GenerateMojoTest.mojo(
            temp,
            new GenerateMojoTest.BrokenProject()
        );
        GenerateMojoTest.<DiagramConfiguration>list(mojo, "diagrams").add(
            new DiagramConfiguration()
        );
        final MojoExecutionException failure = GenerateMojoTest.failure(mojo);
        MatcherAssert.assertThat(
            "Named diagram without packages should fail generation",
            failure,
            Matchers.allOf(
                new MojoExecutionHasMessage(
                    Matchers.is("Cannot generate class diagram")
                ),
                new MojoExecutionHasCause(
                    Matchers.hasProperty(
                        "message",
                        Matchers.is("Configure at least one package to analyze")
                    )
                )
            )
        );
    }

    @Test
    void reportsClasspathResolutionFailure(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = GenerateMojoTest.mojo(
            temp,
            new GenerateMojoTest.BrokenProject()
        );
        GenerateMojoTest.list(mojo, "packages").add("com.github.roroche.classdiagram");
        MatcherAssert.assertThat(
            "Classpath resolution failure should have a dedicated message",
            GenerateMojoTest.failure(mojo),
            new MojoExecutionHasMessage(
                Matchers.is("Cannot resolve project classpath")
            )
        );
    }

    @Test
    void generatesConfiguredDiagram(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = GenerateMojoTest.mojo(
            temp,
            new GenerateMojoTest.CompileProject()
        );
        final GenerateMojoTest.RecordingLog log = new GenerateMojoTest.RecordingLog();
        mojo.setLog(log);
        GenerateMojoTest.list(mojo, "packages").add("com.github.roroche.classdiagram");
        GenerateMojoTest.set(mojo, "failOnEmpty", false);
        mojo.execute();
        MatcherAssert.assertThat(
            "Mojo should generate and log the default diagram",
            new MapOf<String, Object>(
                new MapEntry<>("generated", Files.exists(temp.resolve("class-diagram.puml"))),
                new MapEntry<>("infos", log.infos())
            ),
            Matchers.is(
                new MapOf<String, Object>(
                    new MapEntry<>("generated", true),
                    new MapEntry<>(
                        "infos",
                        List.of(
                            String.format(
                                "Generated %s",
                                temp.resolve("class-diagram.puml")
                            )
                        )
                    )
                )
            )
        );
    }

    private static GenerateMojo mojo(final Path temp, final MavenProject project) throws Exception {
        final GenerateMojo mojo = new GenerateMojo();
        GenerateMojoTest.set(mojo, "project", project);
        GenerateMojoTest.set(mojo, "outputDirectory", temp.toFile());
        GenerateMojoTest.set(mojo, "fileName", "class-diagram.puml");
        return mojo;
    }

    private static MojoExecutionException failure(final GenerateMojo mojo) {
        try {
            mojo.execute();
            return new MojoExecutionException("No exception");
        } catch (final MojoExecutionException err) {
            return err;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> list(final GenerateMojo mojo, final String name) throws Exception {
        final Field field = GenerateMojo.class.getDeclaredField(name);
        field.setAccessible(true);
        return (List<T>) field.get(mojo);
    }

    private static void set(final GenerateMojo mojo, final String name, final Object value)
        throws Exception {
        final Field field = GenerateMojo.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(mojo, value);
    }

    private static final class BrokenProject extends MavenProject {

        @Override
        public List<String> getCompileClasspathElements()
            throws DependencyResolutionRequiredException {
            throw new DependencyResolutionRequiredException(null);
        }

        @Override
        public List<String> getRuntimeClasspathElements()
            throws DependencyResolutionRequiredException {
            throw new DependencyResolutionRequiredException(null);
        }
    }

    private static final class CompileProject extends MavenProject {

        @Override
        public List<String> getCompileClasspathElements() {
            return List.of(
                System.getProperty("java.class.path").split(File.pathSeparator)
            );
        }

        @Override
        public List<String> getRuntimeClasspathElements() {
            return List.of();
        }
    }

    private static final class RecordingLog implements Log {

        /**
         * Recorded info messages.
         */
        private final List<String> infos = new ArrayList<>(0);

        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public void debug(final CharSequence content) {
            // Not used in these tests.
        }

        @Override
        public void debug(final CharSequence content, final Throwable error) {
            // Not used in these tests.
        }

        @Override
        public void debug(final Throwable error) {
            // Not used in these tests.
        }

        @Override
        public boolean isInfoEnabled() {
            return true;
        }

        @Override
        public void info(final CharSequence content) {
            this.infos.add(content.toString());
        }

        @Override
        public void info(final CharSequence content, final Throwable error) {
            this.info(content);
        }

        @Override
        public void info(final Throwable error) {
            this.info(error.getMessage());
        }

        @Override
        public boolean isWarnEnabled() {
            return false;
        }

        @Override
        public void warn(final CharSequence content) {
            // Not used in these tests.
        }

        @Override
        public void warn(final CharSequence content, final Throwable error) {
            // Not used in these tests.
        }

        @Override
        public void warn(final Throwable error) {
            // Not used in these tests.
        }

        @Override
        public boolean isErrorEnabled() {
            return false;
        }

        @Override
        public void error(final CharSequence content) {
            // Not used in these tests.
        }

        @Override
        public void error(final CharSequence content, final Throwable error) {
            // Not used in these tests.
        }

        @Override
        public void error(final Throwable error) {
            // Not used in these tests.
        }

        List<String> infos() {
            return List.copyOf(this.infos);
        }
    }
}
