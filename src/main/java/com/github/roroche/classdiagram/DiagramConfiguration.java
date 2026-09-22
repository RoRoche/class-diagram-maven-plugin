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
import java.util.ArrayList;
import java.util.List;

/**
 * Maven-facing configuration of one diagram.
 *
 * <p>This mutable type deliberately stays at the Maven boundary. Domain
 * objects copy its values before performing work.</p>
 *
 * @since 0.0.1
 */
@SuppressWarnings({
    "PMD.DataClass",
    "PMD.ConstructorShouldDoInitialization"
})
// @checkstyle MemberNameCheck (500 lines)
public final class DiagramConfiguration {

    /** Diagram name. */
    private String name;

    /** Packages included in the diagram. */
    private final List<String> packages = new ArrayList<>(0);

    /** Packages excluded from the diagram. */
    private final List<String> excludePackages = new ArrayList<>(0);

    /** Class-name patterns excluded from the diagram. */
    private final List<String> excludeClasses = new ArrayList<>(0);

    /** Optional output directory override. */
    private File outputDirectory;

    /** Optional output file name override. */
    private String fileName;

    /**
     * Empty constructor for Maven.
     */
    public DiagramConfiguration() {
        // Empty constructor for Maven
    }

    /**
     * Getter for the diagram name.
     *
     * @return Diagram name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the diagram packages.
     *
     * @return Diagram packages
     */
    public List<String> getPackages() {
        return List.copyOf(this.packages);
    }

    /**
     * Getter for the excluded packages.
     *
     * @return Excluded packages
     */
    public List<String> getExcludePackages() {
        return List.copyOf(this.excludePackages);
    }

    /**
     * Getter for the excluded classes.
     *
     * @return Excluded classes
     */
    public List<String> getExcludeClasses() {
        return List.copyOf(this.excludeClasses);
    }

    /**
     * Getter for the output directory.
     *
     * @return Output directory
     */
    public File getOutputDirectory() {
        return this.outputDirectory;
    }

    /**
     * Getter for the output file name.
     *
     * @return Output file name
     */
    public String getFileName() {
        return this.fileName;
    }
}
