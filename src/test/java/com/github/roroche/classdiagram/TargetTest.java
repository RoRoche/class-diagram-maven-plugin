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
import java.lang.reflect.Field;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Target}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
final class TargetTest {

    @Test
    void returnsDefaultDirectory() {
        final File dir = new File("target/default");
        MatcherAssert.assertThat(
            "Default directory should be used without override",
            new Target(dir, new DiagramConfiguration()).value(),
            Matchers.is(dir)
        );
    }

    @Test
    void returnsConfiguredDirectory() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        final File dir = new File("target/custom");
        final Field fld = DiagramConfiguration.class.getDeclaredField("outputDirectory");
        fld.setAccessible(true);
        fld.set(cfg, dir);
        MatcherAssert.assertThat(
            "Configured directory should override default",
            new Target(new File("target/default"), cfg).value(),
            Matchers.is(dir)
        );
    }
}
