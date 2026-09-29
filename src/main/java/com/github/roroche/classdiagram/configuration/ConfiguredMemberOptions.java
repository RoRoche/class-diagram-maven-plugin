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

import org.cactoos.Scalar;

/**
 * Member options resolved from global and per-diagram configuration.
 *
 * @since 0.0.4
 */
public final class ConfiguredMemberOptions implements Scalar<MemberOptions> {

    /**
     * Defaults from the global configuration.
     */
    private final MemberOptions defaults;

    /**
     * Configuration.
     */
    private final DiagramConfiguration config;

    /**
     * New configured member options.
     *
     * @param defaults Default options
     * @param config Diagram configuration
     */
    public ConfiguredMemberOptions(
        final MemberOptions defaults,
        final DiagramConfiguration config
    ) {
        this.defaults = defaults;
        this.config = config;
    }

    @Override
    public MemberOptions value() {
        return new MemberOptionsOverride(
            new ConfiguredMemberDefaults(this.defaults, this.config).value(),
            this.config.isIncludeFields(),
            this.config.isIncludeConstructors(),
            this.config.isIncludeMethods()
        ).value();
    }
}
