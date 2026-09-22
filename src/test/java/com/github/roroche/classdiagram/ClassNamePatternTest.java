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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for class-name patterns.
 *
 * @since 0.0.1
 */
final class ClassNamePatternTest {

    @Test
    void matchesDoubleWildcard() {
        Assertions.assertTrue(
            new ClassNamePattern("**.*Test")
                .matches("com.github.roroche.domain.CustomerTest"),
            "Pattern '**.*Test' should match 'com.github.roroche.domain.CustomerTest'"
        );
    }

    @Test
    void matchesPackageWildcard() {
        Assertions.assertTrue(
            new ClassNamePattern("com.github.roroche.internal.**")
                .matches("com.github.roroche.internal.deep.Secret"),
            "Pattern 'com.github.roroche.internal.**' should match 'com.github.roroche.internal.deep.Secret'"
        );
    }

    @Test
    void rejectsDifferentClass() {
        Assertions.assertFalse(
            new ClassNamePattern("**.*Test")
                .matches("com.github.roroche.domain.Customer"),
            "Pattern '**.*Test' should not match 'com.github.roroche.domain.Customer'"
        );
    }
}
