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

import java.io.File;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for diagram specifications.
 *
 * @since 0.0.1
 */
final class DiagramSpecsTest {

    @Test
    void createsAggregateDiagram() {
        Assertions.assertEquals(
            "class-diagram",
            new DiagramSpecs(
                new ListOf<>("com.acme"),
                new ListOf<>(),
                new ListOf<>(),
                new ListOf<>(),
                new File("target/diagrams"),
                "architecture.puml",
                false
            ).value().get(0).name(),
            "The diagram name should be 'class-diagram'"
        );
    }

    @Test
    void createsSpecificDiagram() {
        Assertions.assertTrue(
            new DiagramSpecs(
                new ListOf<>("com.acme"),
                new ListOf<>(),
                new ListOf<>(),
                new ListOf<>(),
                new File("target/diagrams"),
                "architecture.puml",
                false
            ).value().get(0).output().endsWith(
                "target/diagrams/architecture.puml"
            ),
            "The output file should be 'target/diagrams/architecture.puml'"
        );
    }

    @Test
    void createsOneDiagramPerPackage() {
        Assertions.assertEquals(
            2,
            new DiagramSpecs(
                new ListOf<>("com.github.roroche.domain", "com.github.roroche.app"),
                new ListOf<>(),
                new ListOf<>(),
                new ListOf<>(),
                new File("target/diagrams"),
                "ignored.puml",
                true
            ).value().size(),
            "There should be 2 diagrams, one for each package"
        );
    }
}
