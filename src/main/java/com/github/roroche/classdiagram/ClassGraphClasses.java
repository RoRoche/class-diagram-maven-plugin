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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.Comparator;
import java.util.List;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterable.Sorted;
import org.cactoos.list.ListOf;

/**
 * Classes discovered with ClassGraph.
 *
 * @since 0.0.1
 */
public final class ClassGraphClasses implements Classes {

    /** Specification. */
    private final DiagramSpec spec;

    /** Explicit classpath entries. */
    private final List<String> classpath;

    /**
     * New discovery.
     *
     * @param spec Diagram specification
     * @param classpath Classpath entries
     */
    // @checkstyle ConstructorsCodeFreeCheck (8 lines)
    public ClassGraphClasses(
        final DiagramSpec spec,
        final List<String> classpath
    ) {
        this.spec = spec;
        this.classpath = List.copyOf(classpath);
    }

    @Override
    public List<Class<?>> value() {
        ClassGraph graph = new ClassGraph()
            .enableClassInfo()
            .overrideClasspath(this.classpath)
            .acceptPackages(this.spec.packages().toArray(String[]::new));
        if (!this.spec.excludedPackages().isEmpty()) {
            graph = graph.rejectPackages(
                this.spec.excludedPackages().toArray(String[]::new)
            );
        }
        final List<ClassNamePattern> rejected = new ListOf<>(
            new Mapped<>(ClassNamePattern::new, this.spec.excludedClasses())
        );
        final List<Class<?>> classes;
        try (ScanResult scan = graph.scan()) {
            classes = new ListOf<>(
                new Mapped<>(
                    ClassInfo::loadClass,
                    new Sorted<>(
                        Comparator.comparing(ClassInfo::getName),
                        new Filtered<>(
                            info -> !new Rejected(info, rejected).value(),
                            scan.getAllClasses()
                        )
                    )
                )
            );
        }
        return List.copyOf(classes);
    }
}
