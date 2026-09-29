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
package com.github.roroche.classdiagram.diagram;

import com.github.roroche.classdiagram.configuration.MemberOptions;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PlantUmlDiagram}.
 *
 * @since 0.0.1
 */
final class PlantUmlDiagramTest {

    @Test
    void rendersClass() {
        MatcherAssert.assertThat(
            "Rendered diagram should contain class name",
            new PlantUmlDiagram(
                "sample",
                () -> new ListOf<>(Sample.class),
                true
            ).value(),
            Matchers.containsString("Sample")
        );
    }

    @Test
    void rendersConstructorWhenConfigured() {
        MatcherAssert.assertThat(
            "Constructor option should render constructor signature",
            new PlantUmlDiagram(
                "sample",
                () -> new ListOf<>(Sample.class),
                true,
                new MemberOptions(true, true, true)
            ).value(),
            Matchers.containsString("Sample(String)")
        );
    }

    @Test
    void hidesMembersWhenConfigured() {
        MatcherAssert.assertThat(
            "Disabled members should leave a bare class declaration",
            new PlantUmlDiagram(
                "sample",
                () -> new ListOf<>(Sample.class),
                true,
                new MemberOptions(false, false, false)
            ).value(),
            Matchers.allOf(
                Matchers.containsString("class \"Sample\""),
                Matchers.not(Matchers.containsString("value : String")),
                Matchers.not(Matchers.containsString("value()"))
            )
        );
    }

    @Test
    void rendersEmptyWhenAllowed() {
        MatcherAssert.assertThat(
            "Allowed empty diagram should still render PlantUML",
            new PlantUmlDiagram("empty", ListOf::new, false).value(),
            Matchers.containsString("@startuml")
        );
    }

    @Test
    void rejectsEmptyWhenRequired() {
        MatcherAssert.assertThat(
            "Required non-empty diagram should report its name",
            new PlantUmlDiagramTest.Failure(
                new PlantUmlDiagram("empty", ListOf::new, true)
            ).value(),
            Matchers.hasProperty(
                "message",
                Matchers.is("No classes found for diagram 'empty'")
            )
        );
    }

    static final class Sample {

        /**
         * Value.
         */
        private final String value;

        /**
         * New sample.
         */
        Sample() {
            this("");
        }

        /**
         * New sample.
         *
         * @param value Value
         */
        Sample(final String value) {
            this.value = value;
        }

        /**
         * Value.
         *
         * @return Value
         */
        String value() {
            return this.value;
        }
    }

    /**
     * Failure from PlantUML diagram.
     *
     * @since 0.0.3
     */
    private static final class Failure implements Scalar<Exception> {

        /**
         * PlantUML diagram.
         */
        private final PlantUmlDiagram diagram;

        /**
         * New failure.
         *
         * @param diagram PlantUML diagram
         */
        Failure(final PlantUmlDiagram diagram) {
            this.diagram = diagram;
        }

        @Override
        public Exception value() {
            Exception failure;
            try {
                this.diagram.value();
                failure = new IllegalStateException("No exception");
            } catch (final IllegalStateException err) {
                failure = err;
            }
            return failure;
        }
    }
}
