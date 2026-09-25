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
import java.util.List;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * Converts a list of class names into a list of classes.
 *
 * @since 0.0.3
 */
// @checkstyle ParameterNameCheck (500 lines)
public final class FakeClasses extends ListEnvelope<Class<?>> {

    /**
     * New classes.
     *
     * @param excludedPackages Excluded packages
     * @param excludedClasses Excluded classes
     */
    public FakeClasses(
        final List<String> excludedPackages,
        final List<String> excludedClasses
    ) {
        super(
            new ClassGraphClasses(
                new DiagramSpec(
                    "test",
                    new ListOf<>("com.github.roroche.classdiagram"),
                    excludedPackages,
                    excludedClasses,
                    Path.of("x")
                ),
                new ListOf<>(
                    System.getProperty("java.class.path").split(File.pathSeparator)
                )
            ).value()
        );
    }
}
