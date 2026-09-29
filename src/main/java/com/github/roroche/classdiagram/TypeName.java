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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.cactoos.text.UncheckedText;

/**
 * Compact PlantUML type name.
 *
 * @since 0.0.4
 */
public final class TypeName {

    /**
     * Type.
     */
    private final Type type;

    /**
     * New type name.
     *
     * @param type Type
     */
    public TypeName(final Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final String name;
        if (this.type instanceof Class<?>) {
            name = new UncheckedText(
                new ClassTypeName((Class<?>) this.type)
            ).asString();
        } else if (this.type instanceof ParameterizedType parameterized) {
            name = new UncheckedText(new ParameterizedTypeName(parameterized)).asString();
        } else {
            name = this.type.getTypeName();
        }
        return name;
    }
}
