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
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.ListEnvelope;

/**
 * Returns a field of a {@link DiagramConfiguration} as a list.
 *
 * @since 0.0.3
 */
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
public final class FieldAsList extends ListEnvelope<String> {

    /**
     * New field as list.
     *
     * @param cfg Diagram configuration
     * @param name Field name
     * @throws Exception If the field is not accessible
     */
    public FieldAsList(
        final DiagramConfiguration cfg,
        final String name
    ) throws Exception {
        super(
            ((Scalar<List<String>>) () -> {
                final Field fld = DiagramConfiguration.class.getDeclaredField(name);
                fld.setAccessible(true);
                return (List<String>) fld.get(cfg);
            }).value()
        );
    }
}
