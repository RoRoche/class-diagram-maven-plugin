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

import com.github.roroche.eorules.SuppressEoRule;

/**
 * Options for class member rendering.
 *
 * @param selection Selected members.
 * @param visibility Member visibility.
 *
 * @since 0.0.4
 */
@SuppressEoRule(SuppressEoRule.PUBLIC_CONTRACTS)
public record MemberOptions(
    MemberSelection selection,
    MemberVisibility visibility
) {
    /**
     * New member options.
     *
     * @param selection Selected members
     */
    public MemberOptions(final MemberSelection selection) {
        this(selection, MemberVisibility.ALL);
    }

    /**
     * Default options matching the historical renderer.
     */
    public MemberOptions() {
        this(new MemberSelection(MemberKind.FIELD, MemberKind.METHOD));
    }

    /**
     * Whether fields are rendered.
     *
     * @return True when fields are rendered
     */
    public boolean fields() {
        return this.selection.includes(MemberKind.FIELD);
    }

    /**
     * Whether constructors are rendered.
     *
     * @return True when constructors are rendered
     */
    public boolean constructors() {
        return this.selection.includes(MemberKind.CONSTRUCTOR);
    }

    /**
     * Whether methods are rendered.
     *
     * @return True when methods are rendered
     */
    public boolean methods() {
        return this.selection.includes(MemberKind.METHOD);
    }

    /**
     * Whether only public members are rendered.
     *
     * @return True when only public members are rendered
     */
    public boolean publicOnly() {
        return this.visibility.publicOnly();
    }
}
