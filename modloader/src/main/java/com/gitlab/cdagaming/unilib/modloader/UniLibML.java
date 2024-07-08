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

package com.gitlab.cdagaming.unilib.modloader;

import com.gitlab.cdagaming.unilib.UniLib;
import com.gitlab.cdagaming.unilib.core.CoreUtils;
import io.github.cdagaming.unicore.utils.FileUtils;
import io.github.cdagaming.unicore.utils.MappingUtils;
import io.github.cdagaming.unicore.utils.OSUtils;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.src.ModLoader;

import java.util.List;

/**
 * The Primary Application Class and Utilities
 *
 * @author CDAGaming
 */
public class UniLibML {
    /**
     * Begins Scheduling Ticks on Class Initialization
     */
    public UniLibML() {
        if (OSUtils.JAVA_SPEC < 1.8f) {
            throw new UnsupportedOperationException("Incompatible JVM!!! @MOD_NAME@ requires Java 8 or above to work properly!");
        }

        if (isClient()) {
            MappingUtils.setFilePath("/mappings-modloader.srg");
            CoreUtils.MOD_COUNT_SUPPLIER = () -> ((List<?>) StringUtils.getField(ModLoader.class, null, "modList")).size();

            UniLib.assertLoaded();
        } else {
            CoreUtils.LOG.info("Disabling @MOD_NAME@, as it is client side only.");
        }
    }

    /**
     * Determine whether we are running on the Client-Side
     *
     * @return whether we are running on the Client-Side
     */
    private boolean isClient() {
        return FileUtils.findClass("net.minecraft.client.Minecraft") != null;
    }
}