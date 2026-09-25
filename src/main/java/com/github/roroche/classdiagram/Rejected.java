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

import io.github.classgraph.ClassInfo;
import java.util.List;
import org.cactoos.Scalar;

/**
 * Rejected class.
 *
 * @since 0.0.3
 */
public final class Rejected implements Scalar<Boolean> {

    /**
     * Class information.
     */
    private final ClassInfo info;

    /**
     * Rejected name patterns.
     */
    private final List<ClassNamePattern> patterns;

    /**
     * New rejected class.
     *
     * @param info Class information
     * @param patterns Rejected name patterns
     */
    public Rejected(
        final ClassInfo info,
        final List<ClassNamePattern> patterns
    ) {
        this.info = info;
        this.patterns = patterns;
    }

    @Override
    public Boolean value() {
        return this.patterns.stream().anyMatch(
            pattern -> pattern.matches(this.info.getName())
        );
    }
}
