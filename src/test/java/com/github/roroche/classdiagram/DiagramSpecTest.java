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
import java.util.ArrayList;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DiagramSpec}.
 *
 * @since 0.0.1
 */
final class DiagramSpecTest {

    @Test
    void returnsName() {
        MatcherAssert.assertThat(
            "Name should be retained",
            new DiagramSpecTest.Spec().value().name(),
            Matchers.is("domain")
        );
    }

    @Test
    void returnsPackages() {
        MatcherAssert.assertThat(
            "Packages should be retained",
            new DiagramSpecTest.Spec().value().packages(),
            Matchers.contains("a")
        );
    }

    @Test
    void returnsExcludedPackages() {
        MatcherAssert.assertThat(
            "Excluded packages should be retained",
            new DiagramSpecTest.Spec().value().excludedPackages(),
            Matchers.contains("b")
        );
    }

    @Test
    void returnsExcludedClasses() {
        MatcherAssert.assertThat(
            "Excluded classes should be retained",
            new DiagramSpecTest.Spec().value().excludedClasses(),
            Matchers.contains("c")
        );
    }

    @Test
    void returnsOutput() {
        MatcherAssert.assertThat(
            "Output should be retained",
            new DiagramSpecTest.Spec().value().output(),
            Matchers.is(Path.of("x.puml"))
        );
    }

    @Test
    void copiesPackages() {
        final List<String> values = new ArrayList<>(new ListOf<>("a"));
        final DiagramSpec item = new DiagramSpec(
            "x",
            values,
            new ListOf<>(),
            new ListOf<>(),
            Path.of("x")
        );
        values.add("b");
        MatcherAssert.assertThat(
            "Packages should be defensively copied",
            item.packages(),
            Matchers.contains("a")
        );
    }

    @Test
    void copiesExcludedPackages() {
        final List<String> values = new ArrayList<>(new ListOf<>("a"));
        final DiagramSpec item = new DiagramSpec(
            "x",
            new ListOf<>(),
            values,
            new ListOf<>(),
            Path.of("x")
        );
        values.add("b");
        MatcherAssert.assertThat(
            "Excluded packages should be defensively copied",
            item.excludedPackages(),
            Matchers.contains("a")
        );
    }

    @Test
    void copiesExcludedClasses() {
        final List<String> values = new ArrayList<>(new ListOf<>("a"));
        final DiagramSpec item = new DiagramSpec(
            "x",
            new ListOf<>(),
            new ListOf<>(),
            values,
            Path.of("x")
        );
        values.add("b");
        MatcherAssert.assertThat(
            "Excluded classes should be defensively copied",
            item.excludedClasses(),
            Matchers.contains("a")
        );
    }

    /**
     * Fake diagram specification.
     *
     * @since 0.0.3
     */
    private static final class Spec implements Scalar<DiagramSpec> {

        @Override
        public DiagramSpec value() {
            return new DiagramSpec(
                "domain",
                new ListOf<>("a"),
                new ListOf<>("b"),
                new ListOf<>("c"),
                Path.of("x.puml")
            );
        }
    }
}
