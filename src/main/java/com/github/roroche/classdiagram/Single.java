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
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;

/**
 * Single-package diagram specification.
 *
 * @since 0.0.3
 */
// @checkstyle MemberNameCheck (500 lines)
// @checkstyle ParameterNameCheck (500 lines)
public final class Single implements Scalar<DiagramSpec> {

    /**
     * Package.
     */
    private final String pkg;

    /**
     * Excluded packages.
     */
    private final List<String> excludedPackages;

    /**
     * Excluded classes.
     */
    private final List<String> excludedClasses;

    /**
     * Output directory.
     */
    private final File directory;

    /**
     * New single-package diagram specification.
     *
     * @param pkg Package
     * @param excludedPackages Excluded packages
     * @param excludedClasses Excluded classes
     * @param directory Output directory
     */
    public Single(
        final String pkg,
        final List<String> excludedPackages,
        final List<String> excludedClasses,
        final File directory
    ) {
        this.pkg = pkg;
        this.excludedPackages = excludedPackages;
        this.excludedClasses = excludedClasses;
        this.directory = directory;
    }

    @Override
    public DiagramSpec value() {
        return new DiagramSpec(
            this.pkg,
            new ListOf<>(this.pkg),
            this.excludedPackages,
            this.excludedClasses,
            this.directory.toPath().resolve(String.format("%s.puml", this.pkg))
        );
    }
}
