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
package com.github.roroche.classdiagram.spec;

import com.github.roroche.classdiagram.configuration.DiagramConfiguration;
import com.github.roroche.classdiagram.configuration.MemberOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;

/**
 * Converts Maven configuration into immutable diagram specifications.
 *
 * @since 0.0.1
 */
// @checkstyle ParameterNameCheck (500 lines)
// @checkstyle MemberNameCheck (500 lines)
public final class DiagramSpecs implements Scalar<List<DiagramSpec>> {

    /**
     * Global packages.
     */
    private final List<String> packages;

    /**
     * Global excluded packages.
     */
    private final List<String> excludedPackages;

    /**
     * Global excluded classes.
     */
    private final List<String> excludedClasses;

    /**
     * Named configurations.
     */
    private final List<DiagramConfiguration> diagrams;

    /**
     * Default output directory.
     */
    private final File directory;

    /**
     * Default output file.
     */
    private final String file;

    /**
     * One output per package.
     */
    private final boolean perPackage;

    /**
     * Member options.
     */
    private final MemberOptions members;

    /**
     * New specifications.
     *
     * @param packages Packages
     * @param excludedPackages Excluded packages
     * @param excludedClasses Excluded classes
     * @param diagrams Named diagrams
     * @param directory Output directory
     * @param file Output file
     * @param perPackage One file per package
     */
    // @checkstyle ParameterNameCheck (18 lines)
    public DiagramSpecs(
        final List<String> packages,
        final List<String> excludedPackages,
        final List<String> excludedClasses,
        final List<DiagramConfiguration> diagrams,
        final File directory,
        final String file,
        final boolean perPackage
    ) {
        this(
            packages,
            excludedPackages,
            excludedClasses,
            diagrams,
            directory,
            file,
            perPackage,
            new MemberOptions()
        );
    }

    /**
     * New specifications.
     *
     * @param packages Packages
     * @param excludedPackages Excluded packages
     * @param excludedClasses Excluded classes
     * @param diagrams Named diagrams
     * @param directory Output directory
     * @param file Output file
     * @param perPackage One file per package
     * @param members Member options
     */
    // @checkstyle ParameterNameCheck (20 lines)
    // @checkstyle ConstructorsCodeFreeCheck (19 lines)
    public DiagramSpecs(
        final List<String> packages,
        final List<String> excludedPackages,
        final List<String> excludedClasses,
        final List<DiagramConfiguration> diagrams,
        final File directory,
        final String file,
        final boolean perPackage,
        final MemberOptions members
    ) {
        this.packages = List.copyOf(packages);
        this.excludedPackages = List.copyOf(excludedPackages);
        this.excludedClasses = List.copyOf(excludedClasses);
        this.diagrams = List.copyOf(diagrams);
        this.directory = directory;
        this.file = file;
        this.perPackage = perPackage;
        this.members = members;
    }

    @SuppressWarnings("PMD.ConfusingTernary")
    @Override
    public List<DiagramSpec> value() {
        final List<DiagramSpec> specs = new ArrayList<>(0);
        if (!this.diagrams.isEmpty()) {
            specs.addAll(
                new ListOf<>(
                    new Mapped<>(
                        config -> new Named(this.directory, config, this.members).value(),
                        this.diagrams
                    )
                )
            );
        } else if (this.perPackage) {
            specs.addAll(
                new ListOf<>(
                    new Mapped<>(
                        pkg -> new Single(
                            pkg,
                            this.excludedPackages,
                            this.excludedClasses,
                            this.directory,
                            this.members
                        ).value(),
                        this.packages
                    )
                )
            );
        } else {
            specs.addAll(
                new ListOf<>(
                    new DiagramSpec(
                        "class-diagram",
                        this.packages,
                        this.excludedPackages,
                        this.excludedClasses,
                        this.directory.toPath().resolve(this.file),
                        this.members
                    )
                )
            );
        }
        return List.copyOf(specs);
    }
}
