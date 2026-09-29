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

import com.github.roroche.classdiagram.configuration.MemberOptions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link VisibleMethod}.
 *
 * @since 0.0.4
 */
final class VisibleMethodTest {

    @Test
    void acceptsPublicMethod() throws Exception {
        MatcherAssert.assertThat(
            "Public-only mode should accept public methods",
            new VisibleMethod(
                MemberOptions.class.getDeclaredMethod("fields"),
                true
            ).value(),
            Matchers.is(true)
        );
    }

    @Test
    void rejectsPrivateMethod() throws Exception {
        MatcherAssert.assertThat(
            "Public-only mode should reject non-public methods",
            new VisibleMethod(
                VisibleMethodTest.Sample.class.getDeclaredMethod("closed"),
                true
            ).value(),
            Matchers.is(false)
        );
    }

    @Test
    void rejectsGeneratedMethod() throws Exception {
        MatcherAssert.assertThat(
            "Generated methods should be rejected",
            new VisibleMethod(
                VisibleMethodTest.Sample.class.getDeclaredMethod("$probe"),
                false
            ).value(),
            Matchers.is(false)
        );
    }

    /**
     * Sample.
     *
     * @since 0.0.4
     */
    @SuppressWarnings({
        "PMD.AvoidDollarSigns",
        "PMD.MethodNamingConventions",
        "UnusedMethod"
    })
    // @checkstyle IllegalIdentifierNameCheck (30 lines)
    // @checkstyle MethodNameCheck (30 lines)
    private static final class Sample {

        void closed() {
            // Empty method.
        }

        /**
         * Probe method.
         */
        void $probe() {
            // Empty method.
        }
    }
}
