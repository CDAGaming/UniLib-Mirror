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

package com.gitlab.cdagaming.unilib.neoforge;

import com.gitlab.cdagaming.unilib.UniLib;
import com.gitlab.cdagaming.unilib.core.CoreUtils;
import io.github.cdagaming.unicore.utils.MappingUtils;
import io.github.cdagaming.unicore.utils.OSUtils;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * The Primary Application Class and Utilities
 *
 * @author CDAGaming
 */
@Mod("@MOD_ID@")
public class UniLibNeoForge {
    /**
     * Begins Scheduling Ticks on Class Initialization
     */
    public UniLibNeoForge() {
        if (OSUtils.JAVA_SPEC < 1.8f) {
            throw new UnsupportedOperationException("Incompatible JVM!!! @MOD_NAME@ requires Java 8 or above to work properly!");
        }

        if (FMLEnvironment.dist.isClient()) {
            CoreUtils.MOD_COUNT_SUPPLIER = () -> ModList.get().getMods().size();

            UniLib.assertLoaded();
        } else {
            CoreUtils.LOG.info("Disabling @MOD_NAME@, as it is client side only.");
        }
    }
}