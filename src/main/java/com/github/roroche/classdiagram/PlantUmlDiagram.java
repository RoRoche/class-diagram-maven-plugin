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

import ch.ifocusit.plantuml.classdiagram.ClassDiagramBuilder;
import java.util.List;

/**
 * PlantUML class diagram backed by plantuml-builder.
 *
 * @since 0.0.1
 */
public final class PlantUmlDiagram implements Diagram {

    /** Classes. */
    private final Classes classes;

    /** Fail on no classes. */
    private final boolean fail;

    /** Diagram name for diagnostics. */
    private final String name;

    /**
     * New diagram.
     *
     * @param name Name
     * @param classes Classes
     * @param fail Fail on empty
     */
    public PlantUmlDiagram(
        final String name,
        final Classes classes,
        final boolean fail
    ) {
        this.name = name;
        this.classes = classes;
        this.fail = fail;
    }

    @Override
    public String value() throws Exception {
        final List<Class<?>> found = this.classes.value();
        if (this.fail && found.isEmpty()) {
            throw new IllegalStateException(
                String.format("No classes found for diagram '%s'", this.name)
            );
        }
        return new ClassDiagramBuilder()
            .addClasses(found.toArray(Class<?>[]::new))
            .build();
    }
}
