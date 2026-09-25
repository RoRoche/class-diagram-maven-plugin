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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link GeneratedDiagram}.
 *
 * @since 0.0.1
 */
// @checkstyle BracketsStructureCheck (120 lines)
// @checkstyle IllegalCatchCheck (120 lines)
@SuppressWarnings({
    "PMD.AvoidCatchingGenericException",
    "PMD.CloseResource",
    "PMD.OnlyOneReturn",
    "PMD.UnnecessaryLocalRule"
})
final class GeneratedDiagramTest {

    @Test
    void generatesFile(@TempDir final Path temp) throws Exception {
        final Path out = temp.resolve("deep/diagram.puml");
        MatcherAssert.assertThat(
            "Generation should return destination",
            new GeneratedDiagram(() -> "diagram", out).generate(),
            Matchers.is(out)
        );
    }

    @Test
    void writesContent(@TempDir final Path temp) throws Exception {
        final Path out = temp.resolve("diagram.puml");
        new GeneratedDiagram(() -> "diagram", out).generate();
        MatcherAssert.assertThat(
            "Generation should write diagram text",
            Files.readString(out, StandardCharsets.UTF_8),
            Matchers.is("diagram")
        );
    }

    @Test
    void generatesRelativeFile() throws Exception {
        final Path out = Path.of("generated-diagram-test.puml");
        try {
            MatcherAssert.assertThat(
                "Relative output should be returned",
                new GeneratedDiagram(() -> "diagram", out).generate(),
                Matchers.is(out)
            );
        } finally {
            Files.deleteIfExists(out);
        }
    }

    @Test
    void rejectsRootOutput() {
        MatcherAssert.assertThat(
            "Root output cannot be written as a diagram file",
            new GeneratedDiagramTest.Failure(
                new GeneratedDiagram(
                    () -> "diagram",
                    Path.of("/")
                )
            ).value(),
            Matchers.instanceOf(IOException.class)
        );
    }

    @Test
    void generatesSameNestedFileConcurrently(@TempDir final Path temp) throws Exception {
        final Path out = temp.resolve("race/diagram.puml");
        final ExecutorService service = Executors.newFixedThreadPool(2);
        try {
            final List<Path> generated = service.invokeAll(
                new ListOf<>(
                    new GeneratedDiagramTest.Task(out, "first"),
                    new GeneratedDiagramTest.Task(out, "second")
                )
            ).stream().map(future -> {
                try {
                    return future.get();
                } catch (final Exception err) {
                    throw new IllegalStateException(err);
                }
            }).toList();
            MatcherAssert.assertThat(
                "Concurrent generation should return destinations and create output",
                new MapOf<String, Object>(
                    new MapEntry<>("generated", generated),
                    new MapEntry<>("exists", Files.exists(out))
                ),
                new AllOf<Map<String, Object>>(
                    Matchers.hasEntry("generated", new ListOf<>(out, out)),
                    Matchers.hasEntry("exists", true)
                )
            );
        } finally {
            service.shutdownNow();
        }
    }

    /**
     * Generated diagram task.
     *
     * @since 0.0.3
     */
    private static final class Task implements Callable<Path> {

        /**
         * Output path.
         */
        private final Path out;

        /**
         * Diagram text.
         */
        private final String value;

        /**
         * New generated diagram task.
         *
         * @param out Output path
         * @param value Diagram text
         */
        Task(final Path out, final String value) {
            this.out = out;
            this.value = value;
        }

        @Override
        public Path call() throws Exception {
            return new GeneratedDiagram(() -> this.value, this.out).generate();
        }
    }

    /**
     * Failure from generated diagram.
     *
     * @since 0.0.3
     */
    // @checkstyle ReturnCountCheck (50 lines)
    // @checkstyle IllegalCatchCheck (50 lines)
    private static final class Failure implements Scalar<Exception> {

        /**
         * Generated diagram.
         */
        private final GeneratedDiagram diagram;

        /**
         * New failure.
         *
         * @param diagram Generated diagram
         */
        Failure(final GeneratedDiagram diagram) {
            this.diagram = diagram;
        }

        @Override
        public Exception value() {
            try {
                this.diagram.generate();
                return new IllegalStateException("No exception");
            } catch (final Exception err) {
                return err;
            }
        }
    }
}
