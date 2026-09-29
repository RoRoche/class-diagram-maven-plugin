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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.roroche.classdiagram;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DiagramWithMembers}.
 *
 * @since 0.0.4
 */
final class DiagramWithMembersTest {

    @Test
    void replacesOneClassDeclaration() {
        MatcherAssert.assertThat(
            "Diagram text should be replaced for the matching class",
            new DiagramWithMembers(
                String.format("@startuml%nclass \"Sample\"%n@enduml%n"),
                DiagramWithMembersTest.Sample.class,
                new MemberOptions(false, true, false)
            ).value(),
            Matchers.is(
                String.format("@startuml%nclass \"Sample\" {%n  Sample()%n}%n@enduml%n")
            )
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
}
