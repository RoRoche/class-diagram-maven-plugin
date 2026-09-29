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
package com.github.roroche.classdiagram.members;

import com.github.roroche.classdiagram.MemberParameters;
import java.lang.reflect.Constructor;
import org.cactoos.text.TextEnvelope;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * PlantUML constructor line.
 *
 * @since 0.0.4
 */
public final class ConstructorLine extends TextEnvelope {

    /**
     * New constructor line.
     *
     * @param type Declaring type
     * @param ctor Constructor
     */
    public ConstructorLine(final Class<?> type, final Constructor<?> ctor) {
        super(
            new TextOf(
                String.format(
                    "  %s(%s)",
                    type.getSimpleName(),
                    new UncheckedText(
                        new MemberParameters(ctor.getGenericParameterTypes())
                    ).asString()
                )
            )
        );
    }
}
