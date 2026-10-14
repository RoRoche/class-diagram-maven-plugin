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

import com.github.roroche.classdiagram.configuration.DiagramConfiguration;
import com.github.roroche.classdiagram.matchers.MojoExecutionHasCause;
import com.github.roroche.classdiagram.matchers.MojoExecutionHasMessage;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.cactoos.Scalar;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;
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
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
final class GenerateMojoTest {

    @Test
    void rejectsMissingPackages(@TempDir final Path temp) {
        MatcherAssert.assertThat(
            "Missing packages should fail generation",
            new GenerateMojoTest.Failure(
                new GenerateMojoTest.Mojo(temp, new MavenProject()).value()
            ).value(),
            new MojoExecutionHasMessage(
                Matchers.is("Cannot generate class diagram")
            )
        );
    }

    @Test
    void rejectsEmptyPerPackageConfiguration(@TempDir final Path temp) {
        final GenerateMojo mojo = new GenerateMojoTest.Mojo(
            temp,
            new GenerateMojoTest.BrokenProject()
        ).value();
        new GenerateMojoTest.Set(mojo, "perPackage", true).run();
        MatcherAssert.assertThat(
            "Empty per-package configuration should fail generation",
            new GenerateMojoTest.Failure(mojo).value(),
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
        final GenerateMojo mojo = new GenerateMojoTest.Mojo(
            temp,
            new GenerateMojoTest.BrokenProject()
        ).value();
        new GenerateMojoTest.FieldAsList<DiagramConfiguration>(mojo, "diagrams").add(
            new DiagramConfiguration()
        );
        MatcherAssert.assertThat(
            "Named diagram without packages should fail generation",
            new GenerateMojoTest.Failure(mojo).value(),
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
        final GenerateMojo mojo = new GenerateMojoTest.Mojo(
            temp,
            new GenerateMojoTest.BrokenProject()
        ).value();
        new GenerateMojoTest.FieldAsList<String>(
            mojo,
            "packages"
        ).add("com.github.roroche.classdiagram");
        MatcherAssert.assertThat(
            "Classpath resolution failure should have a dedicated message",
            new GenerateMojoTest.Failure(mojo).value(),
            new MojoExecutionHasMessage(
                Matchers.is("Cannot resolve project classpath")
            )
        );
    }

    @Test
    void generatesConfiguredDiagram(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = new GenerateMojoTest.Mojo(
            temp,
            new GenerateMojoTest.CompileProject()
        ).value();
        final GenerateMojoTest.RecordingLog log = new GenerateMojoTest.RecordingLog();
        mojo.setLog(log);
        new GenerateMojoTest.FieldAsList<String>(
            mojo,
            "packages"
        ).add("com.github.roroche.classdiagram");
        new GenerateMojoTest.Set(mojo, "failOnEmpty", false).run();
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
                        new ListOf<>(
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

    @Test
    void generatesConfiguredMembers(@TempDir final Path temp) throws Exception {
        final GenerateMojo mojo = new GenerateMojoTest.Mojo(
            temp,
            new GenerateMojoTest.CompileProject()
        ).value();
        new GenerateMojoTest.FieldAsList<String>(
            mojo,
            "packages"
        ).add("com.github.roroche.classdiagram");
        new GenerateMojoTest.Set(mojo, "failOnEmpty", false).run();
        new GenerateMojoTest.Set(mojo, "includeFields", false).run();
        new GenerateMojoTest.Set(mojo, "includeConstructors", true).run();
        new GenerateMojoTest.Set(mojo, "includeMethods", false).run();
        mojo.execute();
        MatcherAssert.assertThat(
            "Mojo member options should affect generated diagram",
            Files.readString(temp.resolve("class-diagram.puml")),
            Matchers.allOf(
                Matchers.containsString("PlantUmlDiagram("),
                Matchers.not(Matchers.containsString("classes : Classes")),
                Matchers.not(Matchers.containsString("value() : String"))
            )
        );
    }

    /**
     * Mojo field as a list.
     *
     * @param <T> Element type
     * @since 0.0.3
     */
    private static final class FieldAsList<T> extends ListEnvelope<T> {

        /**
         * New field as list.
         *
         * @param mojo Mojo containing the field
         * @param name Field name
         * @throws Exception If the field is not accessible
         */
        @SuppressWarnings("unchecked")
        private FieldAsList(final GenerateMojo mojo, final String name) throws Exception {
            super(
                ((Scalar<List<T>>) () -> {
                    final Field field = GenerateMojo.class.getDeclaredField(name);
                    field.setAccessible(true);
                    return (List<T>) field.get(mojo);
                }).value()
            );
        }
    }

    /**
     * Configured mojo.
     *
     * @param temp Output directory.
     * @param project Maven project.
     * @since 0.0.3
     */
    private record Mojo(Path temp, MavenProject project) implements Scalar<GenerateMojo> {

        /**
         * New configured mojo.
         *
         * @param temp Output directory
         * @param project Maven project
         */
        private Mojo {
        }

        @Override
        public GenerateMojo value() {
            final GenerateMojo mojo = new GenerateMojo();
            new GenerateMojoTest.Set(mojo, "project", this.project).run();
            new GenerateMojoTest.Set(mojo, "outputDirectory", this.temp.toFile()).run();
            new GenerateMojoTest.Set(mojo, "fileName", "class-diagram.puml").run();
            return mojo;
        }
    }

    /**
     * Assignment to a mojo field.
     *
     * @since 0.0.5
     */
    private static final class Set implements Runnable {

        /**
         * Mojo to configure.
         */
        private final GenerateMojo mojo;

        /**
         * Field name.
         */
        private final String name;

        /**
         * Field value.
         */
        private final Object value;

        /**
         * New assignment.
         *
         * @param mojo Mojo to configure
         * @param name Field name
         * @param value Field value
         */
        private Set(final GenerateMojo mojo, final String name, final Object value) {
            this.mojo = mojo;
            this.name = name;
            this.value = value;
        }

        @Override
        public void run() {
            try {
                final Field field = GenerateMojo.class.getDeclaredField(this.name);
                field.setAccessible(true);
                field.set(this.mojo, this.value);
            } catch (final ReflectiveOperationException err) {
                throw new IllegalStateException(
                    String.format("Cannot set mojo field %s", this.name), err
                );
            }
        }
    }

    /**
     * Failure from mojo execution.
     *
     * @since 0.0.5
     */
    private static final class Failure implements Scalar<MojoExecutionException> {

        /**
         * Mojo to execute.
         */
        private final GenerateMojo mojo;

        /**
         * New failure.
         *
         * @param mojo Mojo to execute
         * @since 0.0.5
         */
        private Failure(final GenerateMojo mojo) {
            this.mojo = mojo;
        }

        @Override
        public MojoExecutionException value() {
            MojoExecutionException failure;
            try {
                this.mojo.execute();
                failure = new MojoExecutionException("No exception");
            } catch (final MojoExecutionException err) {
                failure = err;
            }
            return failure;
        }
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
            return new ListOf<>(
                System.getProperty("java.class.path").split(File.pathSeparator)
            );
        }

        @Override
        public List<String> getRuntimeClasspathElements() {
            return new ListOf<>();
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
            this.info(Optional.ofNullable(error.getMessage()).orElse(error.toString()));
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
