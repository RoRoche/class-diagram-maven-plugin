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
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DiagramMembers}.
 *
 * @since 0.0.4
 */
final class DiagramMembersTest {

    @Test
    void keepsUnknownClassText() {
        MatcherAssert.assertThat(
            "Unknown class should leave diagram text unchanged",
            new DiagramMembers(
                String.format("@startuml%nclass \"Other\"%n@enduml%n"),
                new ListOf<>(DiagramMembersTest.Sample.class),
                new MemberOptions()
            ).value(),
            Matchers.is(String.format("@startuml%nclass \"Other\"%n@enduml%n"))
        );
    }

    @Test
    void replacesClassWithoutBlock() {
        MatcherAssert.assertThat(
            "Class without block should receive configured members",
            new DiagramMembers(
                String.format("@startuml%nclass \"Sample\"%n@enduml%n"),
                new ListOf<>(DiagramMembersTest.Sample.class),
                new MemberOptions(false, true, false)
            ).value(),
            Matchers.is(
                String.format(
                    "@startuml%nclass \"Sample\" {%n  Sample()%n}%n@enduml%n"
                )
            )
        );
    }

    @Test
    void replacesClassAtBeginning() {
        MatcherAssert.assertThat(
            "Class at the beginning should receive configured members",
            new DiagramMembers(
                String.format("class \"Sample\"%n@enduml%n"),
                new ListOf<>(DiagramMembersTest.Sample.class),
                new MemberOptions(false, true, false)
            ).value(),
            Matchers.is(String.format("class \"Sample\" {%n  Sample()%n}%n@enduml%n"))
        );
    }

    @Test
    void replacesInterfaceBlock() {
        MatcherAssert.assertThat(
            "Interface block should receive configured methods",
            new DiagramMembers(
                String.format(
                    "@startuml%ninterface \"Contract\" {%n  old()%n}%n@enduml%n"
                ),
                new ListOf<>(DiagramMembersTest.Contract.class),
                new MemberOptions(false, false, true)
            ).value(),
            Matchers.is(String.format("@startuml%ninterface \"Contract\" {%n  run()%n}%n@enduml%n"))
        );
    }

    @Test
    void removesBlockWhenNoMembersAreRendered() {
        MatcherAssert.assertThat(
            "Class block should be removed when all members are disabled",
            new DiagramMembers(
                String.format("@startuml%nclass \"Sample\" {%n  old%n}%n@enduml%n"),
                new ListOf<>(DiagramMembersTest.Sample.class),
                new MemberOptions(false, false, false)
            ).value(),
            Matchers.is(String.format("@startuml%nclass \"Sample\"%n@enduml%n"))
        );
    }

    /**
     * Sample.
     *
     * @since 0.0.4
     */
    private static final class Sample {

        /**
         * New sample.
         */
        Sample() {
            // Empty constructor.
        }
    }

    /**
     * Contract.
     *
     * @since 0.0.4
     */
    @FunctionalInterface
    @SuppressWarnings("UnusedMethod")
    private interface Contract {

        /**
         * Run.
         */
        void run();
    }
}
