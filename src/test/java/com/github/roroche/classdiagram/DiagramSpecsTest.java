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
import java.nio.file.Path;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DiagramSpecs}.
 *
 * @since 0.0.1
 */
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
final class DiagramSpecsTest {

    @Test
    void createsAggregateName() {
        MatcherAssert.assertThat(
            "Aggregate name should use default",
            aggregate().name(),
            Matchers.is("class-diagram")
        );
    }

    @Test
    void createsAggregatePackages() {
        MatcherAssert.assertThat(
            "Aggregate should retain packages",
            aggregate().packages(),
            Matchers.contains("com.acme")
        );
    }

    @Test
    void createsAggregateExcludedPackages() {
        MatcherAssert.assertThat(
            "Aggregate should retain excluded packages",
            aggregate().excludedPackages(),
            Matchers.contains("com.acme.internal")
        );
    }

    @Test
    void createsAggregateExcludedClasses() {
        MatcherAssert.assertThat(
            "Aggregate should retain excluded classes",
            aggregate().excludedClasses(),
            Matchers.contains("**.*Test")
        );
    }

    @Test
    void createsAggregateOutput() {
        MatcherAssert.assertThat(
            "Aggregate should resolve configured output",
            aggregate().output(),
            Matchers.is(Path.of("target/diagrams/architecture.puml"))
        );
    }

    @Test
    void createsPerPackageCount() {
        MatcherAssert.assertThat(
            "Per-package mode should create one spec per package",
            perPackage(),
            Matchers.hasSize(2)
        );
    }

    @Test
    void createsPerPackageName() {
        MatcherAssert.assertThat(
            "Per-package name should equal package",
            perPackage().get(0).name(),
            Matchers.is("com.acme.one")
        );
    }

    @Test
    void createsPerPackagePackages() {
        MatcherAssert.assertThat(
            "Per-package spec should contain its package",
            perPackage().get(0).packages(),
            Matchers.contains("com.acme.one")
        );
    }

    @Test
    void createsPerPackageOutput() {
        MatcherAssert.assertThat(
            "Per-package output should use package name",
            perPackage().get(0).output(),
            Matchers.is(Path.of("target/diagrams/com.acme.one.puml"))
        );
    }

    @Test
    void createsNamedName() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use configured name",
            named().name(),
            Matchers.is("domain")
        );
    }

    @Test
    void createsNamedDefaultName() throws Exception {
        MatcherAssert.assertThat(
            "Unnamed spec should use default name",
            named(null, null, null).name(),
            Matchers.is("class-diagram")
        );
    }

    @Test
    void createsNamedDefaultFile() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should derive file from name",
            named("domain", null, null).output(),
            Matchers.is(Path.of("target/diagrams/domain.puml"))
        );
    }

    @Test
    void createsNamedConfiguredFile() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use configured file",
            named("domain", "custom.puml", null).output(),
            Matchers.is(Path.of("target/diagrams/custom.puml"))
        );
    }

    @Test
    void createsNamedConfiguredDirectory() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use configured directory",
            named(
                "domain",
                null,
                new File("target/custom")
            ).output(),
            Matchers.is(Path.of("target/custom/domain.puml"))
        );
    }

    @Test
    void createsNamedPackages() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use named packages",
            named().packages(),
            Matchers.contains("com.acme.domain")
        );
    }

    @Test
    void createsNamedExcludedPackages() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use named excluded packages",
            named().excludedPackages(),
            Matchers.contains("com.acme.domain.internal")
        );
    }

    @Test
    void createsNamedExcludedClasses() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use named excluded classes",
            named().excludedClasses(),
            Matchers.contains("**.*Factory")
        );
    }

    private static DiagramSpec aggregate() {
        return new DiagramSpecs(
            List.of("com.acme"),
            List.of("com.acme.internal"),
            List.of("**.*Test"),
            List.of(),
            new File("target/diagrams"),
            "architecture.puml",
            false
        ).value().get(0);
    }

    private static List<DiagramSpec> perPackage() {
        return new DiagramSpecs(
            List.of("com.acme.one", "com.acme.two"),
            List.of("x"),
            List.of("y"),
            List.of(),
            new File("target/diagrams"),
            "ignored",
            true
        ).value();
    }

    private static DiagramSpec named() throws Exception {
        final DiagramConfiguration cfg = config(
            "domain",
            null,
            null
        );
        list(cfg, "packages").add("com.acme.domain");
        list(cfg, "excludePackages").add("com.acme.domain.internal");
        list(cfg, "excludeClasses").add("**.*Factory");
        return specs(cfg);
    }

    private static DiagramSpec named(
        final String name,
        final String file,
        final File dir
    ) throws Exception {
        return specs(config(name, file, dir));
    }

    private static DiagramSpec specs(final DiagramConfiguration cfg) {
        return new DiagramSpecs(
            List.of("ignored"),
            List.of(),
            List.of(),
            List.of(cfg),
            new File("target/diagrams"),
            "ignored.puml",
            false
        ).value().get(0);
    }

    private static DiagramConfiguration config(
        final String name,
        final String file,
        final File dir
    ) throws Exception {
        final DiagramConfiguration cfg = new DiagramConfiguration();
        set(cfg, "name", name);
        set(cfg, "fileName", file);
        set(cfg, "outputDirectory", dir);
        return cfg;
    }

    @SuppressWarnings("unchecked")
    private static List<String> list(
        final DiagramConfiguration cfg,
        final String name
    ) throws Exception {
        final Field fld = DiagramConfiguration.class.getDeclaredField(name);
        fld.setAccessible(true);
        return (List<String>) fld.get(cfg);
    }

    private static void set(
        final DiagramConfiguration cfg,
        final String name,
        final Object value
    ) throws Exception {
        final Field fld = DiagramConfiguration.class.getDeclaredField(name);
        fld.setAccessible(true);
        fld.set(cfg, value);
    }
}
