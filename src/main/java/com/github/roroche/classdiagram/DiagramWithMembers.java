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

import org.cactoos.Scalar;
import org.cactoos.text.UncheckedText;

/**
 * Diagram text with members for one class.
 *
 * @since 0.0.4
 */
public final class DiagramWithMembers implements Scalar<String> {

    /**
     * Diagram.
     */
    private final String diagram;

    /**
     * Type.
     */
    private final Class<?> type;

    /**
     * Options.
     */
    private final MemberOptions options;

    /**
     * New diagram with members.
     *
     * @param diagram Diagram
     * @param type Type
     * @param options Options
     */
    public DiagramWithMembers(
        final String diagram,
        final Class<?> type,
        final MemberOptions options
    ) {
        this.diagram = diagram;
        this.type = type;
        this.options = options;
    }

    @Override
    public String value() {
        final int start = this.diagram.indexOf(
            new UncheckedText(new DiagramHeader(this.type)).asString()
        );
        String updated = this.diagram;
        if (start >= 0) {
            final int line = this.diagram.indexOf('\n', start);
            final String declaration = this.diagram.substring(start, line);
            final int end;
            if (declaration.endsWith("{")) {
                end = this.diagram.indexOf(String.format("%n}"), line) + 2;
            } else {
                end = line;
            }
            updated = String.format(
                "%s%s%s",
                this.diagram.substring(0, start),
                new UncheckedText(
                    new DiagramBlock(this.type, this.options)
                ).asString(),
                this.diagram.substring(end)
            );
        }
        return updated;
    }
}
