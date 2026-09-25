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

import java.lang.reflect.Field;
import org.cactoos.scalar.ScalarEnvelope;

/**
 * Diagram configuration with a field set to a value.
 *
 * @since 0.0.3
 */
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
public final class DiagramConfigurationWithField extends ScalarEnvelope<DiagramConfiguration> {

    /**
     * New diagram configuration with a field set to a value.
     *
     * @param name Field name
     * @param value Field value
     */
    public DiagramConfigurationWithField(
        final String name,
        final Object value
    ) {
        this(new DiagramConfiguration(), name, value);
    }

    /**
     * New diagram configuration with a field set to a value.
     *
     * @param cfg Diagram configuration
     * @param name Field name
     * @param value Field value
     */
    public DiagramConfigurationWithField(
        final DiagramConfiguration cfg,
        final String name,
        final Object value
    ) {
        super(() -> {
            final Field fld = DiagramConfiguration.class.getDeclaredField(name);
            fld.setAccessible(true);
            fld.set(cfg, value);
            return cfg;
        });
    }
}
