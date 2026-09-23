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
package com.github.roroche.classdiagram.matchers;

import org.apache.maven.plugin.MojoExecutionException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher for {@link MojoExecutionException} messages.
 *
 * @since 0.0.1
 */
// @checkstyle ProtectedMethodInFinalClassCheck (50 lines)
public final class MojoExecutionHasMessage
    extends TypeSafeDiagnosingMatcher<MojoExecutionException> {

    /**
     * Message matcher.
     */
    private final Matcher<String> origin;

    /**
     * New matcher.
     *
     * @param origin Message matcher
     */
    public MojoExecutionHasMessage(final Matcher<String> origin) {
        this.origin = origin;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a mojo execution exception with message ");
        description.appendDescriptionOf(this.origin);
    }

    @Override
    protected boolean matchesSafely(
        final MojoExecutionException item,
        final Description mismatch
    ) {
        final String message = item.getMessage();
        final boolean matches = this.origin.matches(message);
        if (!matches) {
            mismatch.appendText("message ");
            this.origin.describeMismatch(message, mismatch);
        }
        return matches;
    }
}
