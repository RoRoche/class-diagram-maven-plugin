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

import org.cactoos.BiProc;

/**
 * Append a literal as regex text.
 *
 * @since 0.0.3
 */
public final class AppendLiteral implements BiProc<Character, StringBuilder> {

    /**
     * Regex metacharacters to escape.
     */
    private final String special;

    /**
     * New literal append operation.
     */
    public AppendLiteral() {
        this("\\.^$|?+()[]{}");
    }

    /**
     * New literal append operation.
     *
     * @param special Regex metacharacters to escape
     */
    public AppendLiteral(final String special) {
        this.special = special;
    }

    @Override
    public void exec(final Character chr, final StringBuilder regex) {
        if (this.special.indexOf(chr) >= 0) {
            regex.append('\\');
        }
        regex.append(chr);
    }
}
