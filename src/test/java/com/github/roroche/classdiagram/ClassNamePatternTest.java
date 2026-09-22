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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ClassNamePattern}.
 *
 * @since 0.0.1
 */
final class ClassNamePatternTest {

    @Test
    void matchesDoubleWildcard() {
        MatcherAssert.assertThat(
            "Double wildcard should cross package boundaries",
            new ClassNamePattern("**.*Test").matches("com.acme.deep.CustomerTest"),
            Matchers.is(true)
        );
    }

    @Test
    void matchesSingleWildcard() {
        MatcherAssert.assertThat(
            "Single wildcard should match inside one package segment",
            new ClassNamePattern("com.acme.*Factory").matches("com.acme.UserFactory"),
            Matchers.is(true)
        );
    }

    @Test
    void matchesTrailingSingleWildcard() {
        MatcherAssert.assertThat(
            "Trailing single wildcard should match the rest of one segment",
            new ClassNamePattern("com.acme.*").matches("com.acme.User"),
            Matchers.is(true)
        );
    }

    @Test
    void rejectsSingleWildcardAcrossDot() {
        MatcherAssert.assertThat(
            "Single wildcard should not cross a package boundary",
            new ClassNamePattern("com.acme.*Factory").matches("com.acme.deep.UserFactory"),
            Matchers.is(false)
        );
    }

    @Test
    void escapesRegexCharacters() {
        MatcherAssert.assertThat(
            "Glob metacharacters should be treated literally",
            new ClassNamePattern("a.b+$Thing").matches("a.b+$Thing"),
            Matchers.is(true)
        );
    }

    @Test
    void escapesLeadingBackslash() {
        MatcherAssert.assertThat(
            "Leading backslash should be treated literally",
            new ClassNamePattern("\\Name").matches("\\Name"),
            Matchers.is(true)
        );
    }

    @Test
    void rejectsDifferentName() {
        MatcherAssert.assertThat(
            "Different class name should not match",
            new ClassNamePattern("**.*Test").matches("com.acme.Customer"),
            Matchers.is(false)
        );
    }

    @Test
    void usesCompiledPattern() {
        MatcherAssert.assertThat(
            "Compiled constructor should delegate matching to the supplied pattern",
            new ClassNamePattern(Pattern.compile("^Exact$")).matches("Exact"),
            Matchers.is(true)
        );
    }
}
