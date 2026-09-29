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
 * Member options represented by a description level.
 *
 * @since 0.0.4
 */
public final class MemberOptionsOfLevel implements Scalar<MemberOptions> {

    /**
     * Description level.
     */
    private final DescriptionLevel level;

    /**
     * New member options of level.
     *
     * @param level Description level
     */
    public MemberOptionsOfLevel(final DescriptionLevel level) {
        this.level = level;
    }

    @Override
    public MemberOptions value() {
        final DescriptionLevel resolved = new ConfiguredDescriptionLevel(
            this.level,
            DescriptionLevel.MINIMAL
        ).value();
        final MemberOptions options;
        if (DescriptionLevel.MINIMAL.equals(resolved)) {
            options = new MemberOptions(false, false, false);
        } else if (DescriptionLevel.API.equals(resolved)) {
            options = new MemberOptions(false, true, true, true);
        } else {
            options = new MemberOptions(true, true, true);
        }
        return options;
    }
}
