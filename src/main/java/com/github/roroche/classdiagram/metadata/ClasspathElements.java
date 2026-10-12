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
package com.github.roroche.classdiagram.metadata;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;
import org.cactoos.Scalar;

/**
 * Maven project classpath elements.
 *
 * @since 0.0.5
 */
public final class ClasspathElements implements Scalar<List<String>> {

    /**
     * Maven project.
     */
    private final MavenProject project;

    /**
     * New classpath elements.
     *
     * @param project Maven project
     */
    public ClasspathElements(final MavenProject project) {
        this.project = project;
    }

    @Override
    public List<String> value() throws DependencyResolutionRequiredException {
        final Set<String> elements = LinkedHashSet.newLinkedHashSet(0);
        elements.addAll(this.project.getCompileClasspathElements());
        elements.addAll(this.project.getRuntimeClasspathElements());
        return List.copyOf(elements);
    }
}
