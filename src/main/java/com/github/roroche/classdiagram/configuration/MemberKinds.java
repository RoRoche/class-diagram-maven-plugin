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
package com.github.roroche.classdiagram.configuration;

import java.util.EnumSet;
import java.util.Set;
import org.cactoos.Scalar;

/**
 * Member kinds represented by configured flags.
 *
 * @since 0.0.5
 */
public final class MemberKinds implements Scalar<Set<MemberKind>> {

    /**
     * Field flag.
     */
    private final boolean fields;

    /**
     * Constructor flag.
     */
    private final boolean constructors;

    /**
     * Method flag.
     */
    private final boolean methods;

    /**
     * New member kinds.
     *
     * @param fields Include fields
     * @param constructors Include constructors
     * @param methods Include methods
     */
    public MemberKinds(
        final boolean fields,
        final boolean constructors,
        final boolean methods
    ) {
        this.fields = fields;
        this.constructors = constructors;
        this.methods = methods;
    }

    @Override
    public Set<MemberKind> value() {
        final Set<MemberKind> kinds = EnumSet.noneOf(MemberKind.class);
        if (this.fields) {
            kinds.add(MemberKind.FIELD);
        }
        if (this.constructors) {
            kinds.add(MemberKind.CONSTRUCTOR);
        }
        if (this.methods) {
            kinds.add(MemberKind.METHOD);
        }
        return kinds;
    }
}
