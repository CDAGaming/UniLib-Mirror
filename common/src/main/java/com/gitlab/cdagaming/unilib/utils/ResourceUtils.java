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

package com.gitlab.cdagaming.unilib.utils;

import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.Map;

/**
 * Image Utilities used to Parse texture resource data
 *
 * @author CDAGaming
 */
public class ResourceUtils {
    /**
     * Object representing an empty texture resource
     */
    private static final ResourceLocation EMPTY_RESOURCE = parseResource("");
    /**
     * Object representing prefix registration data
     */
    private static final Map<String, Integer> prefixRegister = StringUtils.newHashMap();

    /**
     * Retrieve a texture resource within the specified namespace and path
     *
     * @param namespace the namespace to interpret
     * @param path      the path to interpret
     * @return The found texture resource
     */
    public static ResourceLocation getResource(final String namespace, final String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    /**
     * Retrieve a texture resource within the "minecraft" namespace using the specified path
     *
     * @param path the path to interpret
     * @return The found texture resource
     */
    public static ResourceLocation getResource(final String path) {
        return getResource("minecraft", path);
    }

    /**
     * Retrieve a texture resource using the specified path
     *
     * @param path the path to interpret
     * @return The found texture resource
     */
    public static ResourceLocation parseResource(final String path) {
        return ResourceLocation.parse(path);
    }

    /**
     * Returns an object representing an empty texture resource
     *
     * @return an object representing an empty texture resource
     */
    public static ResourceLocation getEmptyResource() {
        return EMPTY_RESOURCE;
    }

    /**
     * Retrieve the namespace of the specified resource
     *
     * @param location The texture resource to interpret
     * @return the namespace of the resource
     */
    public static String getNamespace(final ResourceLocation location) {
        return location != null ? location.getNamespace() : "";
    }

    /**
     * Retrieve the path of the specified resource
     *
     * @param location The texture resource to interpret
     * @return the path of the resource
     */
    public static String getPath(final ResourceLocation location) {
        return location != null ? location.getPath() : "";
    }

    /**
     * Detects whether the specified Texture contains valid information
     *
     * @param location The texture resource to interpret
     * @return Whether the specified Texture contains valid information
     */
    public static boolean isValidResource(final ResourceLocation location) {
        return location != null && !StringUtils.isNullOrEmpty(getNamespace(location)) && !StringUtils.isNullOrEmpty(getPath(location));
    }

    /**
     * Register a Dynamic Texture, for use elsewhere in the application
     * <p>Stub Function for removed implementation in 24w46a and above (1.21.4)
     *
     * @param manager        The Texture Manager to attach to
     * @param prefix         The Texture Name Prefix
     * @param dynamicTexture The Dynamic Texture Data
     * @return the created or found texture data
     */
    public static ResourceLocation register(final TextureManager manager, final String prefix, final DynamicTexture dynamicTexture) {
        Integer index = prefixRegister.get(prefix);
        if (index == null) {
            index = 1;
        } else {
            index = index + 1;
        }
        prefixRegister.put(prefix, index);
        final ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace(String.format(Locale.ROOT, "dynamic/%s_%d", prefix, index));
        manager.register(resourceLocation, dynamicTexture);
        return resourceLocation;
    }
}
