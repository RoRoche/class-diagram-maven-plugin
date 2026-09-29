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
package com.github.roroche.classdiagram.members;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import org.cactoos.Scalar;

/**
 * Member accepted by visibility configuration.
 *
 * @since 0.0.4
 */
public final class VisibleMember implements Scalar<Boolean> {

    /**
     * Member.
     */
    private final Member member;

    /**
     * Public only.
     */
    private final boolean pub;

    /**
     * New visible member.
     *
     * @param member Member
     * @param pub Public only
     */
    public VisibleMember(final Member member, final boolean pub) {
        this.member = member;
        this.pub = pub;
    }

    @Override
    public Boolean value() {
        final boolean visible;
        if (this.pub) {
            visible = Modifier.isPublic(this.member.getModifiers());
        } else {
            visible = true;
        }
        return visible;
    }
}
