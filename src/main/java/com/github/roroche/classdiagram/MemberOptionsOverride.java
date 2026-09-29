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

import org.cactoos.Scalar;

/**
 * Member options with optional boolean overrides.
 *
 * @since 0.0.4
 */
public final class MemberOptionsOverride implements Scalar<MemberOptions> {

    /**
     * Defaults.
     */
    private final MemberOptions defaults;

    /**
     * Field override.
     */
    private final Boolean fields;

    /**
     * Constructor override.
     */
    private final Boolean constructors;

    /**
     * Method override.
     */
    private final Boolean methods;

    /**
     * New member options override.
     *
     * @param defaults Defaults
     * @param fields Field override
     * @param constructors Constructor override
     * @param methods Method override
     */
    public MemberOptionsOverride(
        final MemberOptions defaults,
        final Boolean fields,
        final Boolean constructors,
        final Boolean methods
    ) {
        this.defaults = defaults;
        this.fields = fields;
        this.constructors = constructors;
        this.methods = methods;
    }

    @Override
    public MemberOptions value() {
        return new MemberOptions(
            new ConfiguredOption(this.fields, this.defaults.fields()).value(),
            new ConfiguredOption(
                this.constructors,
                this.defaults.constructors()
            ).value(),
            new ConfiguredOption(this.methods, this.defaults.methods()).value(),
            this.defaults.publicOnly()
        );
    }
}
