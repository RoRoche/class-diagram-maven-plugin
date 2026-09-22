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
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DiagramConfiguration}.
 *
 * @since 0.0.1
 */
// @checkstyle LineLengthCheck (150 lines)
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
final class DiagramConfigurationTest {

    @Test
    void returnsNullNameByDefault() {
        MatcherAssert.assertThat(
            "Default name should be null",
            new DiagramConfiguration().getName(),
            Matchers.nullValue()
        );
    }

    @Test
    void returnsEmptyPackagesByDefault() {
        MatcherAssert.assertThat(
            "Default packages should be empty",
            new DiagramConfiguration().getPackages(),
            Matchers.empty()
        );
    }

    @Test
    void returnsEmptyExcludedPackagesByDefault() {
        MatcherAssert.assertThat(
            "Default excluded packages should be empty",
            new DiagramConfiguration().getExcludePackages(),
            Matchers.empty()
        );
    }

    @Test
    void returnsEmptyExcludedClassesByDefault() {
        MatcherAssert.assertThat(
            "Default excluded classes should be empty",
            new DiagramConfiguration().getExcludeClasses(),
            Matchers.empty()
        );
    }

    @Test
    void returnsNullDirectoryByDefault() {
        MatcherAssert.assertThat(
            "Default output directory should be null",
            new DiagramConfiguration().getOutputDirectory(),
            Matchers.nullValue()
        );
    }

    @Test
    void returnsNullFileByDefault() {
        MatcherAssert.assertThat(
            "Default file name should be null",
            new DiagramConfiguration().getFileName(),
            Matchers.nullValue()
        );
    }

    @Test
    void returnsConfiguredName() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        set(cfg, "name", "domain");
        MatcherAssert.assertThat(
            "Configured name should be returned",
            cfg.getName(),
            Matchers.is("domain")
        );
    }

    @Test
    void returnsConfiguredPackages() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        list(cfg, "packages").add("com.acme");
        MatcherAssert.assertThat(
            "Configured packages should be returned",
            cfg.getPackages(),
            Matchers.contains("com.acme")
        );
    }

    @Test
    void returnsConfiguredExcludedPackages() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        list(cfg, "excludePackages").add("com.acme.internal");
        MatcherAssert.assertThat(
            "Configured excluded packages should be returned",
            cfg.getExcludePackages(),
            Matchers.contains("com.acme.internal")
        );
    }

    @Test
    void returnsConfiguredExcludedClasses() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        list(cfg, "excludeClasses").add("**.*Test");
        MatcherAssert.assertThat(
            "Configured excluded classes should be returned",
            cfg.getExcludeClasses(),
            Matchers.contains("**.*Test")
        );
    }

    @Test
    void returnsConfiguredDirectory() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        final File dir = new File("target/custom");
        set(cfg, "outputDirectory", dir);
        MatcherAssert.assertThat(
            "Configured directory should be returned",
            cfg.getOutputDirectory(),
            Matchers.is(dir)
        );
    }

    @Test
    void returnsConfiguredFile() throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        set(cfg, "fileName", "domain.puml");
        MatcherAssert.assertThat(
            "Configured file should be returned",
            cfg.getFileName(),
            Matchers.is("domain.puml")
        );
    }

    @SuppressWarnings("unchecked")
    private static List<String> list(final DiagramConfiguration cfg, final String name) throws Exception {
        final Field fld = DiagramConfiguration.class.getDeclaredField(name);
        fld.setAccessible(true);
        return (List<String>) fld.get(cfg);
    }

    private static void set(final DiagramConfiguration cfg, final String name, final Object value) throws Exception {
        final Field fld = DiagramConfiguration.class.getDeclaredField(name);
        fld.setAccessible(true);
        fld.set(cfg, value);
    }
}
