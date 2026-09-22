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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * Generate PlantUML class diagrams from compiled project classes.
 *
 * @since 0.0.1
 */
@Mojo(
    name = "generate",
    defaultPhase = LifecyclePhase.PROCESS_CLASSES,
    requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
    threadSafe = true
)
@SuppressWarnings("PMD.ConstructorShouldDoInitialization")
// @checkstyle MemberNameCheck (500 lines)
public final class GenerateMojo extends AbstractMojo {

    /** Maven project. */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /** Packages for simple configuration. */
    @Parameter(property = "classDiagram.packages")
    private final List<String> packages = new ArrayList<>(0);

    /** Excluded packages for simple configuration. */
    @Parameter
    private final List<String> excludePackages = new ArrayList<>(0);

    /** Excluded class-name patterns for simple configuration. */
    @Parameter
    private final List<String> excludeClasses = new ArrayList<>(0);

    /** Explicit named diagrams. */
    @Parameter
    private final List<DiagramConfiguration> diagrams = new ArrayList<>(0);

    /** Default output directory. */
    @Parameter(
        defaultValue = "${project.build.directory}/class-diagrams",
        required = true
    )
    private File outputDirectory;

    /** Default aggregate output file. */
    @Parameter(defaultValue = "class-diagram.puml", required = true)
    private String fileName;

    /** Generate one file per configured package. */
    @Parameter(defaultValue = "false")
    private boolean perPackage;

    /** Fail when no class matches a diagram. */
    @Parameter(defaultValue = "true")
    private boolean failOnEmpty;

    /**
     * Empty constructor for Maven.
     */
    public GenerateMojo() {
        // Empty constructor for Maven
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    // @checkstyle IllegalCatchCheck (34 lines)
    public void execute() throws MojoExecutionException {
        try {
            final List<DiagramSpec> specs = new DiagramSpecs(
                this.packages,
                this.excludePackages,
                this.excludeClasses,
                this.diagrams,
                this.outputDirectory,
                this.fileName,
                this.perPackage
            ).value();
            if (specs.isEmpty() || specs.stream().anyMatch(item -> item.packages().isEmpty())) {
                throw new IllegalArgumentException(
                    "Configure at least one package to analyze"
                );
            }
            final List<String> classpath = this.project.getRuntimeClasspathElements();
            for (final DiagramSpec spec : specs) {
                final Path output = new GeneratedDiagram(
                    new PlantUmlDiagram(
                        spec.name(),
                        new ClassGraphClasses(spec, classpath),
                        this.failOnEmpty
                    ),
                    spec.output()
                ).generate();
                this.getLog().info(String.format("Generated %s", output));
            }
        } catch (final DependencyResolutionRequiredException err) {
            throw new MojoExecutionException("Cannot resolve project classpath", err);
        } catch (final Exception err) {
            throw new MojoExecutionException("Cannot generate class diagram", err);
        }
    }
}
