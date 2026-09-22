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
import org.cactoos.scalar.Unchecked;

/**
 * Glob-like class-name pattern.
 *
 * @since 0.0.1
 */
public final class ClassNamePattern {

    /**
     * Compiled expression.
     */
    private final Pattern pattern;

    /**
     * New pattern.
     *
     * @param glob Glob expression
     */
    @SuppressWarnings({
        "PMD.AvoidReassigningLoopVariables",
        "PMD.CognitiveComplexity"
    })
    public ClassNamePattern(final String glob) {
        this(
            new Unchecked<>(
                () -> {
                    final StringBuilder regex = new StringBuilder("^");
                    for (int idx = 0; idx < glob.length(); idx += 1) {
                        final char chr = glob.charAt(idx);
                        if (chr == '*') {
                            if (idx + 1 < glob.length() && glob.charAt(idx + 1) == '*') {
                                regex.append(".*");
                                idx += 1;
                            } else {
                                regex.append("[^.]*");
                            }
                        } else {
                            if ("\\.^$|?+()[]{}".indexOf(chr) >= 0) {
                                regex.append('\\');
                            }
                            regex.append(chr);
                        }
                    }
                    return Pattern.compile(regex.append('$').toString());
                }
            ).value()
        );
    }

    /**
     * New pattern.
     *
     * @param pattern Compiled pattern
     */
    public ClassNamePattern(final Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Match a class name.
     *
     * @param name Fully-qualified class name
     * @return Whether it matches
     */
    public boolean matches(final String name) {
        return this.pattern.matcher(name).matches();
    }
}
