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
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DiagramSpecs}.
 *
 * @since 0.0.1
 */
final class DiagramSpecsTest {

    @Test
    void createsAggregateName() {
        MatcherAssert.assertThat(
            "Aggregate name should use default",
            new Aggregate().value().name(),
            Matchers.is("class-diagram")
        );
    }

    @Test
    void createsAggregatePackages() {
        MatcherAssert.assertThat(
            "Aggregate should retain packages",
            new Aggregate().value().packages(),
            Matchers.contains("com.acme")
        );
    }

    @Test
    void createsAggregateExcludedPackages() {
        MatcherAssert.assertThat(
            "Aggregate should retain excluded packages",
            new Aggregate().value().excludedPackages(),
            Matchers.contains("com.acme.internal")
        );
    }

    @Test
    void createsAggregateExcludedClasses() {
        MatcherAssert.assertThat(
            "Aggregate should retain excluded classes",
            new Aggregate().value().excludedClasses(),
            Matchers.contains("**.*Test")
        );
    }

    @Test
    void createsAggregateOutput() {
        MatcherAssert.assertThat(
            "Aggregate should resolve configured output",
            new Aggregate().value().output(),
            Matchers.is(Path.of("target/diagrams/architecture.puml"))
        );
    }

    @Test
    void createsPerPackageCount() {
        MatcherAssert.assertThat(
            "Per-package mode should create one spec per package",
            new PerPackage(),
            Matchers.hasSize(2)
        );
    }

    @Test
    void createsPerPackageName() {
        MatcherAssert.assertThat(
            "Per-package name should equal package",
            new PerPackage().get(0).name(),
            Matchers.is("com.acme.one")
        );
    }

    @Test
    void createsPerPackagePackages() {
        MatcherAssert.assertThat(
            "Per-package spec should contain its package",
            new PerPackage().get(0).packages(),
            Matchers.contains("com.acme.one")
        );
    }

    @Test
    void createsPerPackageOutput() {
        MatcherAssert.assertThat(
            "Per-package output should use package name",
            new PerPackage().get(0).output(),
            Matchers.is(Path.of("target/diagrams/com.acme.one.puml"))
        );
    }

    @Test
    void createsNamedName() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use configured name",
            new Spec(new FakeConfig("domain", null, null).value()).value().name(),
            Matchers.is("domain")
        );
    }

    @Test
    void createsNamedDefaultName() throws Exception {
        MatcherAssert.assertThat(
            "Unnamed spec should use default name",
            new Spec(new FakeConfig(null, null, null).value()).value().name(),
            Matchers.is("class-diagram")
        );
    }

    @Test
    void createsNamedDefaultFile() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should derive file from name",
            new Spec(new FakeConfig("domain", null, null).value()).value().output(),
            Matchers.is(Path.of("target/diagrams/domain.puml"))
        );
    }

    @Test
    void createsNamedConfiguredFile() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use configured file",
            new Spec(new FakeConfig("domain", "custom.puml", null).value()).value().output(),
            Matchers.is(Path.of("target/diagrams/custom.puml"))
        );
    }

    @Test
    void createsNamedConfiguredDirectory() throws Exception {
        MatcherAssert.assertThat(
            "Named spec should use configured directory",
            new Spec(
                new FakeConfig(
                    "domain",
                    null,
                    new File("target/custom")
                ).value()
            ).value().output(),
            Matchers.is(Path.of("target/custom/domain.puml"))
        );
    }

    @Test
    void createsNamedPackages() throws Exception {
        final DiagramConfiguration cfg = new FakeConfig("domain", null, null).value();
        new FieldAsList(cfg, "packages").add("com.acme.domain");
        MatcherAssert.assertThat(
            "Named spec should use named packages",
            new Spec(cfg).value().packages(),
            Matchers.contains("com.acme.domain")
        );
    }

    @Test
    void createsNamedExcludedPackages() throws Exception {
        final DiagramConfiguration cfg = new FakeConfig("domain", null, null).value();
        new FieldAsList(cfg, "excludePackages").add("com.acme.domain.internal");
        MatcherAssert.assertThat(
            "Named spec should use named excluded packages",
            new Spec(cfg).value().excludedPackages(),
            Matchers.contains("com.acme.domain.internal")
        );
    }

    @Test
    void createsNamedExcludedClasses() throws Exception {
        final DiagramConfiguration cfg = new FakeConfig("domain", null, null).value();
        new FieldAsList(cfg, "excludeClasses").add("**.*Factory");
        MatcherAssert.assertThat(
            "Named spec should use named excluded classes",
            new Spec(cfg).value().excludedClasses(),
            Matchers.contains("**.*Factory")
        );
    }
}
