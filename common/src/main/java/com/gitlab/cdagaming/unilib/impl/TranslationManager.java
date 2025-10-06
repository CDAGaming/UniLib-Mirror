/*
 * MIT License
 *
 * Copyright (c) 2018 - 2025 CDAGaming (cstack2011@yahoo.com)
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

package com.gitlab.cdagaming.unilib.impl;

import com.mojang.minecraft.Minecraft;
import io.github.cdagaming.unicore.utils.TranslationUtils;

/**
 * Utilities for Hooking a {@link TranslationUtils} instance to the Game Resource Manager
 *
 * @param client   The current game instance
 * @param instance The currently linked {@link TranslationUtils} instance
 * @author CDAGaming
 */
public record TranslationManager(Minecraft client,
                                 TranslationUtils instance) {
    /**
     * Initializes a new manager for the {@link TranslationUtils} instance
     *
     * @param client   The current game instance
     * @param instance The {@link TranslationUtils} instance to attach to
     */
    public TranslationManager(final Minecraft client, final TranslationUtils instance) {
        this.client = client;
        this.instance = instance;
    }

    /**
     * The Event to Run on each Client Tick, if passed initialization events
     * <p>
     * Consists of Synchronizing Data, and Updating Translation Data as needed
     */
    public void onTick() {
        instance().onTick();
    }

    /**
     * The Event to run upon reloading resources
     */
    public void onReload() {
        instance().syncTranslations();
    }
}
