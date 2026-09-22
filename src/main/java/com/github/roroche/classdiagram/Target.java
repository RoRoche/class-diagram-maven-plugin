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
import java.util.Optional;
import org.cactoos.Scalar;

/**
 * A {@link Scalar} that returns the output directory for diagrams.
 *
 * @since 0.0.1
 */
public final class Target implements Scalar<File> {

    /** Default output directory. */
    private final File directory;

    /** Diagram configuration. */
    private final DiagramConfiguration config;

    /**
     * Default output directory.
     *
     * @param directory Default output directory
     * @param config Diagram configuration
     */
    public Target(final File directory, final DiagramConfiguration config) {
        this.directory = directory;
        this.config = config;
    }

    @Override
    public File value() {
        return Optional.ofNullable(
            this.config.getOutputDirectory()
        ).orElse(this.directory);
    }
}
