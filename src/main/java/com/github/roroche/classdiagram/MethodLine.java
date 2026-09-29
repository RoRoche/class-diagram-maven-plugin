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

import java.lang.reflect.Method;
import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextEnvelope;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * PlantUML method line.
 *
 * @since 0.0.4
 */
public final class MethodLine extends TextEnvelope {

    /**
     * New method line.
     *
     * @param method Method
     */
    public MethodLine(final Method method) {
        super(
            new FormattedText(
                new TextOf(
                    (Scalar<String>) () -> {
                        final String format;
                        if (Void.TYPE.equals(method.getReturnType())) {
                            format = "  %s(%s)";
                        } else {
                            format = "  %s(%s) : %s";
                        }
                        return format;
                    }
                ),
                method.getName(),
                new UncheckedText(
                    new MemberParameters(method.getGenericParameterTypes())
                ).asString(),
                new TypeName(method.getGenericReturnType())
            )
        );
    }
}
