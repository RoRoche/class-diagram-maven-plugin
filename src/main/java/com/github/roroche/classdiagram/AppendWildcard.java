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

import org.cactoos.BiFunc;

/**
 * Append a wildcard as regex text.
 *
 * @since 0.0.3
 */
public final class AppendWildcard implements BiFunc<Integer, StringBuilder, Integer> {

    /**
     * Glob expression.
     */
    private final String glob;

    /**
     * New wildcard append operation.
     *
     * @param glob Glob expression
     */
    public AppendWildcard(final String glob) {
        this.glob = glob;
    }

    @Override
    public Integer apply(final Integer idx, final StringBuilder regex) {
        final int step;
        if (idx + 1 < this.glob.length() && this.glob.charAt(idx + 1) == '*') {
            regex.append(".*");
            step = 2;
        } else {
            regex.append("[^.]*");
            step = 1;
        }
        return step;
    }
}
