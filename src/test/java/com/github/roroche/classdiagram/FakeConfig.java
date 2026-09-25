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
import org.cactoos.Scalar;

/**
 * Fake diagram configuration.
 *
 * @since 0.0.3
 */
public final class FakeConfig implements Scalar<DiagramConfiguration> {

    /**
     * Diagram name.
     */
    private final String name;

    /**
     * Diagram file.
     */
    private final String file;

    /**
     * Diagram output directory.
     */
    private final File dir;

    /**
     * New fake diagram configuration.
     *
     * @param name Diagram name
     * @param file Diagram file
     * @param dir Diagram output directory
     */
    public FakeConfig(final String name, final String file, final File dir) {
        this.name = name;
        this.file = file;
        this.dir = dir;
    }

    @Override
    public DiagramConfiguration value() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        new DiagramConfigurationWithField(cfg, "name", this.name).value();
        new DiagramConfigurationWithField(cfg, "fileName", this.file).value();
        new DiagramConfigurationWithField(cfg, "outputDirectory", this.dir).value();
        return cfg;
    }
}
