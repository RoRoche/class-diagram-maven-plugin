# Class Diagram Maven Plugin

Generate configurable PlantUML class diagrams directly from compiled Java projects.

`class-diagram-maven-plugin` uses [ClassGraph](https://github.com/classgraph/classgraph)
to discover classes and [plantuml-builder](https://github.com/jboz/plantuml-builder)
to generate PlantUML class diagrams.

[![Build Status](https://github.com/RoRoche/class-diagram-maven-plugin/actions/workflows/build-java.yml/badge.svg)](https://github.com/RoRoche/class-diagram-maven-plugin/actions)
[![Python CI](https://github.com/RoRoche/class-diagram-maven-plugin/actions/workflows/python.yml/badge.svg)](https://github.com/RoRoche/class-diagram-maven-plugin/actions/workflows/python.yml)
![Nodes.js CI](https://github.com/RoRoche/class-diagram-maven-plugin/actions/workflows/build-npm.yml/badge.svg)

![EO principles respected here](https://www.elegantobjects.org/badge.svg)
[![DevOps By Rultor.com](https://www.rultor.com/b/RoRoche/class-diagram-maven-plugin)](https://www.rultor.com/p/RoRoche/class-diagram-maven-plugin)
![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)

[![PDD status](https://www.0pdd.com/svg?name=RoRoche/class-diagram-maven-plugin)](https://www.0pdd.com/p?name=RoRoche/class-diagram-maven-plugin)

[![codecov](https://codecov.io/github/RoRoche/class-diagram-maven-plugin/graph/badge.svg?branch=main)](https://app.codecov.io/github/RoRoche/class-diagram-maven-plugin)
[![Mutation testing badge](https://img.shields.io/endpoint?style=flat&url=https%3A%2F%2Fbadge-api.stryker-mutator.io%2Fgithub.com%2FRoRoche%2Fclass-diagram-maven-plugin%2Fmain)](https://dashboard.stryker-mutator.io/reports/github.com/RoRoche/class-diagram-maven-plugin/main)

[![Hits-of-Code](https://hitsofcode.com/github/RoRoche/class-diagram-maven-plugin?branch=main)](https://hitsofcode.com/github/RoRoche/class-diagram-maven-plugin/view?branch=main)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=bugs)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=RoRoche_class-diagram-maven-plugin&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=RoRoche_class-diagram-maven-plugin)

![nullfree status](https://youshallnotpass.dev/nullfree/RoRoche/class-diagram-maven-plugin)
![staticfree status](https://youshallnotpass.dev/staticfree/RoRoche/class-diagram-maven-plugin)
![allfinal status](https://youshallnotpass.dev/allfinal/RoRoche/class-diagram-maven-plugin)
![allpublic status](https://youshallnotpass.dev/allpublic/RoRoche/class-diagram-maven-plugin)
![setterfree status](https://youshallnotpass.dev/setterfree/RoRoche/class-diagram-maven-plugin)
![nomultiplereturn status](https://youshallnotpass.dev/nomultiplereturn/RoRoche/class-diagram-maven-plugin)

[![Maven Central](https://img.shields.io/maven-central/v/com.github.roroche/class-diagram-maven-plugin.svg?label=Maven%20Central)](https://search.maven.org/artifact/com.github.roroche/class-diagram-maven-plugin)
[![Javadoc](https://javadoc.io/badge2/com.github.roroche/class-diagram-maven-plugin/javadoc.svg)](https://javadoc.io/doc/com.github.roroche/class-diagram-maven-plugin)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/RoRoche/class-diagram-maven-plugin/blob/main/LICENSE)

## ✨ Features

- Generate a class diagram from one or more Java packages
- Generate a single aggregated diagram or one diagram per package
- Define named diagrams in the same Maven configuration
- Exclude packages from the scan
- Exclude classes with glob-style patterns
- Customize the output directory and file name
- Fail the build when a configured diagram contains no classes
- Integrate diagram generation into the Maven lifecycle

## 📥 Installation

Add the plugin to the `build` section of your `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.github.roroche</groupId>
            <artifactId>class-diagram-maven-plugin</artifactId>
            <version>${latest.version}</version>
        </plugin>
    </plugins>
</build>
```

Compile the project before running the plugin so it can analyze the classes.

## 🚀 Usage

### Generate a diagram for one package

Configure the package to analyze:

```xml
<plugin>
    <groupId>com.github.roroche</groupId>
    <artifactId>class-diagram-maven-plugin</artifactId>
    <version>${latest.version}</version>
    <configuration>
        <packages>
            <package>com.github.roroche</package>
        </packages>
    </configuration>
</plugin>
```

Then run:

```shell
mvn compile class-diagram:generate
```

By default, the plugin generates:

```text
target/class-diagrams/class-diagram.puml
```

### Analyze several packages

Add each package to the same configuration:

```xml
<configuration>
    <packages>
        <package>com.github.roroche.domain</package>
        <package>com.github.roroche.application</package>
    </packages>
</configuration>
```

The plugin includes both packages in the same `class-diagram.puml` file.

### Generate one diagram per package

Set `perPackage` to `true`:

```xml
<configuration>
    <packages>
        <package>com.github.roroche.domain</package>
        <package>com.github.roroche.application</package>
    </packages>
    <perPackage>true</perPackage>
</configuration>
```

The plugin generates:

```text
target/class-diagrams/
├── com.github.roroche.application.puml
└── com.github.roroche.domain.puml
```

### Generate multiple named diagrams

Use `diagrams` when different parts of the application need separate diagrams:

```xml
<configuration>
    <diagrams>
        <diagram>
            <name>domain</name>
            <packages>
                <package>com.github.roroche.domain</package>
            </packages>
        </diagram>
        <diagram>
            <name>application</name>
            <packages>
                <package>com.github.roroche.application</package>
                <package>com.github.roroche.usecases</package>
            </packages>
        </diagram>
    </diagrams>
</configuration>
```

The plugin generates:

```text
target/class-diagrams/
├── application.puml
└── domain.puml
```

A named diagram can contain one or more packages.

### Exclude packages

Use `excludePackages` to remove packages from the ClassGraph scan:

```xml
<configuration>
    <packages>
        <package>com.github.roroche</package>
    </packages>
    <excludePackages>
        <package>com.github.roroche.generated</package>
        <package>com.github.roroche.internal</package>
    </excludePackages>
</configuration>
```

### Exclude classes

Use `excludeClasses` to remove individual classes or groups of classes:

```xml
<configuration>
    <packages>
        <package>com.github.roroche</package>
    </packages>
    <excludeClasses>
        <class>com.github.roroche.domain.LegacyObject</class>
        <class>**.*Factory</class>
    </excludeClasses>
</configuration>
```

Class exclusions support glob-style patterns, for example:

```text
**.*Test
com.github.roroche.internal.**
com.github.roroche.domain.*Factory
```

### Customize the output

The default output directory is `target/class-diagrams` and the default
output file is `class-diagram.puml`.

You can change both values:

```xml
<configuration>
    <packages>
        <package>com.github.roroche.domain</package>
    </packages>
    <outputDirectory>
        ${project.build.directory}/architecture
    </outputDirectory>
    <fileName>domain-model.puml</fileName>
</configuration>
```

A named diagram can also override the global output:

```xml
<configuration>
    <outputDirectory>
        ${project.build.directory}/architecture
    </outputDirectory>
    <diagrams>
        <diagram>
            <name>domain</name>
            <packages>
                <package>com.github.roroche.domain</package>
            </packages>
            <fileName>domain-model.puml</fileName>
        </diagram>
        <diagram>
            <name>application</name>
            <packages>
                <package>com.github.roroche.application</package>
            </packages>
            <outputDirectory>
                ${project.build.directory}/architecture/application
            </outputDirectory>
        </diagram>
    </diagrams>
</configuration>
```

### Fail when no classes are found

By default, the build fails when a diagram configuration matches no classes.
This helps detect invalid package names or obsolete diagram configurations.

Disable this behavior with:

```xml
<configuration>
    <packages>
        <package>com.github.roroche.domain</package>
    </packages>
    <failOnEmpty>false</failOnEmpty>
</configuration>
```

### Bind diagram generation to the Maven lifecycle

The `generate` goal uses `process-classes` as its default phase.

To generate diagrams automatically during the build:

```xml
<plugin>
    <groupId>com.github.roroche</groupId>
    <artifactId>class-diagram-maven-plugin</artifactId>
    <version>${latest.version}</version>
    <executions>
        <execution>
            <id>generate-class-diagrams</id>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <packages>
            <package>com.github.roroche.domain</package>
        </packages>
    </configuration>
</plugin>
```

A regular Maven build then generates the diagram:

```shell
mvn verify
```

### Configuration reference

| Parameter | Default | Description |
| --- | --- | --- |
| `packages` | — | Packages to analyze in simple mode |
| `excludePackages` | — | Packages to exclude from the scan |
| `excludeClasses` | — | Class names or glob-style patterns to exclude |
| `outputDirectory` | `${project.build.directory}/class-diagrams` | Directory containing generated diagrams |
| `fileName` | `class-diagram.puml` | File name used for an aggregated diagram |
| `perPackage` | `false` | Generate one diagram for each configured package |
| `failOnEmpty` | `true` | Fail the Maven build when no class matches a diagram |
| `diagrams` | — | Explicit named diagram configurations |

### Complete example

The following configuration generates separate domain and application diagrams
while excluding implementation details:

```xml
<plugin>
    <groupId>com.github.roroche</groupId>
    <artifactId>class-diagram-maven-plugin</artifactId>
    <version>${latest.version}</version>
    <executions>
        <execution>
            <id>generate-class-diagrams</id>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <outputDirectory>
            ${project.build.directory}/architecture
        </outputDirectory>
        <diagrams>
            <diagram>
                <name>domain</name>
                <packages>
                    <package>com.github.roroche.domain</package>
                </packages>
                <excludePackages>
                    <package>com.github.roroche.domain.generated</package>
                    <package>com.github.roroche.domain.internal</package>
                </excludePackages>
                <excludeClasses>
                    <class>**.*Factory</class>
                </excludeClasses>
            </diagram>
            <diagram>
                <name>application</name>
                <packages>
                    <package>com.github.roroche.application</package>
                    <package>com.github.roroche.usecases</package>
                </packages>
            </diagram>
        </diagrams>
    </configuration>
</plugin>
```

Running:

```shell
mvn verify
```

produces:

```text
target/architecture/
├── application.puml
└── domain.puml
```

Any PlantUML-compatible tool can then render the generated `.puml` files.

## 🤝 Contributing

Contributions are welcome!

If you'd like to report a bug, suggest a feature, or submit a pull request, please read our
👉 **[Contributing Guide](CONTRIBUTING.md)**

It contains everything you need to know about:

- Development setup
- Coding standards
- Commit conventions
- Pull request process
- Quality requirements

Thank you for helping improve `class-diagram-maven-plugin` 🚀

## ⭐ Star History

<a href="https://www.star-history.com/?repos=RoRoche%2Fclass-diagram-maven-plugin&type=date&legend=top-left">
 <picture>
   <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/chart?repos=RoRoche/class-diagram-maven-plugin&type=date&theme=dark&legend=top-left&sealed_token=7ubkhbhQH710oL9smB5PZxEHxM8NwZmYMXunbqZsJzcX6jfKI24gLttdA0_R-_UM4hJbefYTESV2oHSYl6Z46LfzxaJa1qvmcMJfWUwQHOQ8q4mapp7BvQ" />
   <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/chart?repos=RoRoche/class-diagram-maven-plugin&type=date&legend=top-left&sealed_token=7ubkhbhQH710oL9smB5PZxEHxM8NwZmYMXunbqZsJzcX6jfKI24gLttdA0_R-_UM4hJbefYTESV2oHSYl6Z46LfzxaJa1qvmcMJfWUwQHOQ8q4mapp7BvQ" />
   <img alt="Star History Chart" src="https://api.star-history.com/chart?repos=RoRoche/class-diagram-maven-plugin&type=date&legend=top-left&sealed_token=7ubkhbhQH710oL9smB5PZxEHxM8NwZmYMXunbqZsJzcX6jfKI24gLttdA0_R-_UM4hJbefYTESV2oHSYl6Z46LfzxaJa1qvmcMJfWUwQHOQ8q4mapp7BvQ" />
 </picture>
</a>

## 📄 License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
