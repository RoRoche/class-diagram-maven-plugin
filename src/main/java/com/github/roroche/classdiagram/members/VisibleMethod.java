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

import java.lang.reflect.Method;
import org.cactoos.Scalar;

/**
 * Method accepted by generated-code and visibility filters.
 *
 * @since 0.0.4
 */
public final class VisibleMethod implements Scalar<Boolean> {

    /**
     * Method.
     */
    private final Method method;

    /**
     * Public only.
     */
    private final boolean pub;

    /**
     * New visible method.
     *
     * @param method Method
     * @param pub Public only
     */
    public VisibleMethod(final Method method, final boolean pub) {
        this.method = method;
        this.pub = pub;
    }

    @Override
    public Boolean value() {
        return !this.method.getName().startsWith("$")
            && new VisibleMember(this.method, this.pub).value();
    }
}
