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

import java.nio.file.Path;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ClassGraphClasses}.
 *
 * @since 0.0.1
 */
// @checkstyle LineLengthCheck (100 lines)
// @checkstyle ParameterNameCheck (100 lines)
// @checkstyle FullyQualifiedTypeCheck (100 lines)
final class ClassGraphClassesTest {

    @Test
    void discoversClasses() {
        MatcherAssert.assertThat(
            "ClassGraph should discover classes from accepted package",
            classes(List.of(), List.of()),
            Matchers.hasItem(ClassGraphClasses.class)
        );
    }

    @Test
    void excludesClass() {
        MatcherAssert.assertThat(
            "Class pattern should exclude matching class",
            classes(List.of(), List.of("**.ClassGraphClasses")),
            Matchers.not(Matchers.hasItem(ClassGraphClasses.class))
        );
    }

    @Test
    void keepsClassesNotMatchingExcludedClass() {
        MatcherAssert.assertThat(
            "Class pattern should only exclude matching classes",
            classes(List.of(), List.of("**.ClassGraphClasses")),
            Matchers.hasItem(DiagramSpec.class)
        );
    }

    @Test
    void excludesPackage() {
        MatcherAssert.assertThat(
            "Rejected package should remove discovered classes",
            classes(List.of("com.github.roroche.classdiagram"), List.of()),
            Matchers.empty()
        );
    }

    @Test
    void sortsClasses() {
        MatcherAssert.assertThat(
            "Discovered classes should be sorted by name",
            names(classes(List.of(), List.of())),
            Matchers.is(sorted(names(classes(List.of(), List.of()))))
        );
    }

    private static List<Class<?>> classes(final List<String> excludedPackages, final List<String> excludedClasses) {
        return new ClassGraphClasses(
            new DiagramSpec(
                "test",
                List.of("com.github.roroche.classdiagram"),
                excludedPackages,
                excludedClasses,
                Path.of("x")
            ),
            List.of(
                System.getProperty("java.class.path").split(java.io.File.pathSeparator)
            )
        ).value();
    }

    private static List<String> names(final List<Class<?>> values) {
        return values.stream().map(Class::getName).toList();
    }

    private static List<String> sorted(final List<String> values) {
        return values.stream().sorted().toList();
    }
}
