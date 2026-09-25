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

import org.cactoos.iterable.Sorted;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ClassGraphClasses}.
 *
 * @since 0.0.1
 */
final class ClassGraphClassesTest {

    @Test
    void discoversClasses() {
        MatcherAssert.assertThat(
            "ClassGraph should discover classes from accepted package",
            new FakeClasses(new ListOf<>(), new ListOf<>()),
            Matchers.hasItem(ClassGraphClasses.class)
        );
    }

    @Test
    void excludesClass() {
        MatcherAssert.assertThat(
            "Class pattern should exclude matching class",
            new FakeClasses(new ListOf<>(), new ListOf<>("**.ClassGraphClasses")),
            Matchers.not(Matchers.hasItem(ClassGraphClasses.class))
        );
    }

    @Test
    void keepsClassesNotMatchingExcludedClass() {
        MatcherAssert.assertThat(
            "Class pattern should only exclude matching classes",
            new FakeClasses(new ListOf<>(), new ListOf<>("**.ClassGraphClasses")),
            Matchers.hasItem(DiagramSpec.class)
        );
    }

    @Test
    void excludesPackage() {
        MatcherAssert.assertThat(
            "Rejected package should remove discovered classes",
            new FakeClasses(new ListOf<>("com.github.roroche.classdiagram"), new ListOf<>()),
            Matchers.empty()
        );
    }

    @Test
    void sortsClasses() {
        MatcherAssert.assertThat(
            "Discovered classes should be sorted by name",
            new ClassNames(new FakeClasses(new ListOf<>(), new ListOf<>())),
            Matchers.is(
                new ListOf<>(
                    new Sorted<>(
                        new ClassNames(
                            new FakeClasses(new ListOf<>(), new ListOf<>())
                        )
                    )
                )
            )
        );
    }
}
