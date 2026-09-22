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

import java.util.regex.Pattern;
import org.cactoos.Scalar;

/**
 * Compiled regex from a glob-like class-name pattern.
 *
 * @since 0.0.1
 */
final class ClassNameRegex implements Scalar<Pattern> {

    /**
     * Regex metacharacters to escape.
     */
    private final String special;

    /**
     * Glob expression.
     */
    private final String glob;

    /**
     * New regex.
     *
     * @param glob Glob expression
     */
    ClassNameRegex(final String glob) {
        this.special = "\\.^$|?+()[]{}";
        this.glob = glob;
    }

    @Override
    public Pattern value() {
        return Pattern.compile(this.regex());
    }

    private String regex() {
        final StringBuilder regex = new StringBuilder("^");
        int idx = 0;
        while (idx < this.glob.length()) {
            idx += this.append(idx, regex);
        }
        return regex.append('$').toString();
    }

    private int append(final int idx, final StringBuilder regex) {
        final int step;
        final char chr = this.glob.charAt(idx);
        if (chr == '*') {
            step = this.appendWildcard(idx, regex);
        } else {
            this.appendLiteral(chr, regex);
            step = 1;
        }
        return step;
    }

    private int appendWildcard(final int idx, final StringBuilder regex) {
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

    private void appendLiteral(final char chr, final StringBuilder regex) {
        if (this.special.indexOf(chr) >= 0) {
            regex.append('\\');
        }
        regex.append(chr);
    }
}
