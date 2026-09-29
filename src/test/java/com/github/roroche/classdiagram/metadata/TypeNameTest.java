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

import java.lang.reflect.Field;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TypeName}.
 *
 * @since 0.0.4
 */
final class TypeNameTest {

    @Test
    void rendersClassName() {
        MatcherAssert.assertThat(
            "Class type should use simple name",
            new TypeName(String.class).toString(),
            Matchers.is("String")
        );
    }

    @Test
    void rendersArrayName() {
        MatcherAssert.assertThat(
            "Array type should use compact component name",
            new TypeName(String[].class).toString(),
            Matchers.is("String[]")
        );
    }

    @Test
    void rendersParameterizedName() throws Exception {
        MatcherAssert.assertThat(
            "Parameterized type should use compact argument names",
            new TypeName(TypeNameTest.field("names").getGenericType()).toString(),
            Matchers.is("List<String>")
        );
    }

    @Test
    void rendersVariableName() throws Exception {
        MatcherAssert.assertThat(
            "Type variable should keep its declared name",
            new TypeName(TypeNameTest.field("value").getGenericType()).toString(),
            Matchers.is("T")
        );
    }

    private static Field field(final String name) throws Exception {
        return TypeNameTest.Sample.class.getDeclaredField(name);
    }

    /**
     * Sample.
     *
     * @param <T> Type
     * @since 0.0.4
     */
    @SuppressWarnings({
        "PMD.UnusedPrivateField",
        "UnusedVariable"
    })
    private static final class Sample<T> {

        /**
         * Names.
         */
        private List<String> names;

        /**
         * Value.
         */
        private T value;
    }
}
