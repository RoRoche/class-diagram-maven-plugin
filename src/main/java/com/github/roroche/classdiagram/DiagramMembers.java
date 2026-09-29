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

import java.util.List;
import org.cactoos.Scalar;

/**
 * Diagram text with configured class members.
 *
 * @since 0.0.4
 */
public final class DiagramMembers implements Scalar<String> {

    /**
     * Diagram text.
     */
    private final String text;

    /**
     * Classes.
     */
    private final List<Class<?>> classes;

    /**
     * Options.
     */
    private final MemberOptions options;

    /**
     * New diagram members.
     *
     * @param text Diagram text
     * @param classes Classes
     * @param options Options
     */
    public DiagramMembers(
        final String text,
        final List<Class<?>> classes,
        final MemberOptions options
    ) {
        this.text = text;
        this.classes = classes;
        this.options = options;
    }

    @Override
    public String value() {
        String diagram = this.text;
        for (final Class<?> type : this.classes) {
            diagram = new DiagramWithMembers(diagram, type, this.options).value();
        }
        return diagram;
    }
}
