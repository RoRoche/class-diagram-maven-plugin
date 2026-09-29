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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MemberOptionsOfLevel}.
 *
 * @since 0.0.4
 */
final class MemberOptionsOfLevelTest {

    @Test
    void rendersMinimalOptions() {
        MatcherAssert.assertThat(
            "Minimal level should disable all member lines",
            new MemberOptionsOfLevel(DescriptionLevel.MINIMAL).value(),
            Matchers.is(new MemberOptions(false, false, false))
        );
    }

    @Test
    void rendersApiOptions() {
        MatcherAssert.assertThat(
            "API level should render public constructors and methods only",
            new MemberOptionsOfLevel(DescriptionLevel.API).value(),
            Matchers.is(new MemberOptions(false, true, true, true))
        );
    }

    @Test
    void rendersFullOptions() {
        MatcherAssert.assertThat(
            "Full level should render every class member",
            new MemberOptionsOfLevel(DescriptionLevel.FULL).value(),
            Matchers.is(new MemberOptions(true, true, true))
        );
    }

    @Test
    void rendersDefaultOptions() {
        MatcherAssert.assertThat(
            "Missing level should use minimal options",
            new MemberOptionsOfLevel(null).value(),
            Matchers.is(new MemberOptions(false, false, false))
        );
    }
}
