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

import java.util.ArrayList;
import java.util.List;
import org.cactoos.Scalar;

/**
 * PlantUML member lines for a class.
 *
 * @since 0.0.4
 */
public final class MemberLines implements Scalar<List<String>> {

    /**
     * Class.
     */
    private final Class<?> type;

    /**
     * Options.
     */
    private final MemberOptions options;

    /**
     * New member lines.
     *
     * @param type Type
     * @param options Options
     */
    public MemberLines(final Class<?> type, final MemberOptions options) {
        this.type = type;
        this.options = options;
    }

    @Override
    public List<String> value() {
        final List<String> lines = new ArrayList<>(0);
        if (this.options.fields()) {
            lines.addAll(new FieldLines(this.type));
        }
        if (this.options.constructors()) {
            lines.addAll(new ConstructorLines(this.type));
        }
        if (this.options.methods()) {
            lines.addAll(new MethodLines(this.type));
        }
        return List.copyOf(lines);
    }
}
