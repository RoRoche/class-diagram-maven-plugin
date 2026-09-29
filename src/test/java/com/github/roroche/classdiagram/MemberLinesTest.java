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

import java.util.List;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MemberLines}.
 *
 * @since 0.0.4
 */
final class MemberLinesTest {

    @Test
    void rendersSelectedMembers() {
        MatcherAssert.assertThat(
            "Selected members should be rendered as PlantUML lines",
            new MemberLines(
                MemberLinesTest.Sample.class,
                new MemberOptions(true, true, true)
            ).value(),
            Matchers.contains(
                "  names : List<String>",
                "  values : String[]",
                "  Sample(String[])",
                "  count() : int",
                "  rename(String)",
                "  self() : T"
            )
        );
    }

    @Test
    void rendersNoMembersWhenDisabled() {
        MatcherAssert.assertThat(
            "Disabled member options should render no lines",
            new MemberLines(
                MemberLinesTest.Sample.class,
                new MemberOptions(false, false, false)
            ).value(),
            Matchers.empty()
        );
    }

    @Test
    void skipsInstrumentationFields() {
        MatcherAssert.assertThat(
            "Instrumentation fields should not be rendered",
            new MemberLines(
                MemberLinesTest.Choice.class,
                new MemberOptions(true, false, false)
            ).value(),
            Matchers.not(Matchers.hasItem("  $VALUES : Choice[]"))
        );
    }

    @Test
    void skipsInstrumentationMethods() {
        MatcherAssert.assertThat(
            "Instrumentation methods should not be rendered",
            new MemberLines(
                MemberLinesTest.Probed.class,
                new MemberOptions(false, false, true)
            ).value(),
            Matchers.empty()
        );
    }

    /**
     * Sample.
     *
     * @param <T> Type
     * @since 0.0.4
     */
    @SuppressWarnings("UnusedMethod")
    private static final class Sample<T> {

        /**
         * Names.
         */
        private final List<String> names;

        /**
         * Values.
         */
        private final String[] values;

        /**
         * New sample.
         *
         * @param values Values
         */
        Sample(final String... values) {
            this.names = new ListOf<>();
            this.values = values.clone();
        }

        /**
         * Count.
         *
         * @return Count
         */
        int count() {
            return this.values.length;
        }

        /**
         * Rename.
         *
         * @param name Name
         */
        void rename(final String name) {
            this.names.add(name);
        }

        /**
         * Self.
         *
         * @return Self
         */
        T self() {
            return null;
        }
    }

    /**
     * Choice.
     *
     * @since 0.0.4
     */
    private enum Choice {

        /**
         * First.
         */
        FIRST
    }

    /**
     * Probed class.
     *
     * @since 0.0.4
     */
    @SuppressWarnings({
        "PMD.AvoidDollarSigns",
        "PMD.MethodNamingConventions",
        "UnusedMethod"
    })
    // @checkstyle IllegalIdentifierNameCheck (20 lines)
    // @checkstyle MethodNameCheck (20 lines)
    private static final class Probed {

        /**
         * Probe method.
         */
        void $probe() {
            // Empty method.
        }
    }
}
