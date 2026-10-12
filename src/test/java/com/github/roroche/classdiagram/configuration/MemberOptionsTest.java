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
package com.github.roroche.classdiagram.configuration;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MemberOptions}.
 *
 * @since 0.0.5
 */
final class MemberOptionsTest {

    @Test
    void usesDefaultSelectedMembers() {
        MatcherAssert.assertThat(
            "Default options should render fields and methods",
            new MemberOptions().fields() && new MemberOptions().methods(),
            Matchers.is(true)
        );
    }

    @Test
    void usesDefaultSkippedMembers() {
        MatcherAssert.assertThat(
            "Default options should not render constructors or restrict visibility",
            new MemberOptions().constructors() || new MemberOptions().publicOnly(),
            Matchers.is(false)
        );
    }

    @Test
    void usesAllSelectedKinds() {
        final MemberOptions options = new MemberOptions(
            new MemberSelection(
                MemberKind.FIELD,
                MemberKind.CONSTRUCTOR,
                MemberKind.METHOD
            )
        );
        MatcherAssert.assertThat(
            "Selected options should render every selected member kind",
            options.fields() && options.constructors() && options.methods(),
            Matchers.is(true)
        );
    }

    @Test
    void usesEmptySelection() {
        final MemberOptions options = new MemberOptions(new MemberSelection());
        MatcherAssert.assertThat(
            "Empty selection should render no member kind",
            options.fields() || options.constructors() || options.methods(),
            Matchers.is(false)
        );
    }

    @Test
    void usesConfiguredVisibility() {
        MatcherAssert.assertThat(
            "Public-only visibility should be exposed by member options",
            new MemberOptions(
                new MemberSelection(MemberKind.METHOD),
                MemberVisibility.PUBLIC_ONLY
            ).publicOnly(),
            Matchers.is(true)
        );
    }
}
