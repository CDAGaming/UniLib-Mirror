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

import com.gitlab.cdagaming.unilib.core.CoreUtils;
import com.gitlab.cdagaming.unilib.impl.ImageFrame;
import io.github.cdagaming.unicore.impl.Pair;
import io.github.cdagaming.unicore.impl.Tuple;
import io.github.cdagaming.unicore.utils.StringUtils;
import io.github.cdagaming.unicore.utils.UrlUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Image Utilities used to Parse External Image Data and rendering tasks
 *
 * @author CDAGaming, wagyourtail
 */
public class ImageUtils {
    /**
     * The Blocking Queue for URL Requests
     * <p>
     * Format: textureName;[textureInputType, textureObj]
     */
    private static final BlockingQueue<Pair<String, Pair<InputType, Object>>> urlRequests = new LinkedBlockingQueue<>();
    /**
     * Cached Images retrieved from URL Texture Retrieval
     * <p>
     * Format: textureName;[[textureInputType, textureObj], [textureIndex, imageData], textureData]
     */
    private static final Map<String, Tuple<Pair<InputType, Object>, Pair<Integer, List<ImageFrame>>, List<ResourceLocation>>> cachedImages = StringUtils.newConcurrentHashMap();
    /**
     * Whether ImageIO has been initialized
     */
    private static boolean hasInitializedImageIO = false;

    static {
        CoreUtils.getThreadFactory().newThread(
                () -> {
                    try {
                        while (!CoreUtils.IS_CLOSING) {
                            final Pair<String, Pair<InputType, Object>> request = urlRequests.take();
                            boolean isGif = request.getFirst().endsWith(".gif");
                            boolean isWebp = request.getFirst().endsWith(".webp");

                            final Pair<Integer, List<ImageFrame>> bufferData = cachedImages.get(request.getFirst()).getSecond();
                            if (bufferData != null) {
                                // Retrieve Data from external source
                                try {
                                    final InputStream streamData;
                                    final Object originData = request.getSecond().getSecond();
                                    switch (request.getSecond().getFirst()) {
                                        case FileData:
                                            streamData = Files.newInputStream(((File) originData).toPath());
                                            break;
                                        case FileStream:
                                            streamData = Files.newInputStream(Paths.get(originData.toString()));
                                            break;
                                        case ByteStream:
                                            final Tuple<Boolean, String, String> base64Data = StringUtils.isBase64(originData.toString());
                                            final byte[] dataSet = base64Data.getFirst() ?
                                                    ImageFrame.decodeBase64(base64Data.getThird(), "UTF-8", false, false) :
                                                    (originData instanceof byte[] byteData ? byteData : StringUtils.getBytes(originData.toString()));
                                            streamData = dataSet != null ? new ByteArrayInputStream(dataSet) : null;
                                            isGif = base64Data.getSecond().contains("gif");
                                            isWebp = base64Data.getSecond().contains("webp");
                                            break;
                                        case Url:
                                            streamData = UrlUtils.getURLStream(originData instanceof URL url ? url : URI.create(originData.toString()).toURL());
                                            isGif = originData.toString().endsWith(".gif");
                                            isWebp = originData.toString().endsWith(".webp");
                                            break;
                                        default:
                                            streamData = null;
                                            break;
                                    }

                                    if (streamData != null) {
                                        if (!hasInitializedImageIO) {
                                            ImageIO.scanForPlugins();
                                            hasInitializedImageIO = true;
                                        }

                                        if (isGif) {
                                            final ImageFrame[] frames = ImageFrame.readGif(streamData);

                                            for (ImageFrame frame : frames) {
                                                try {
                                                    bufferData.getSecond().add(frame);
                                                } catch (Throwable ex) {
                                                    CoreUtils.LOG.debugError(ex);
                                                }
                                            }
                                        } else if (isWebp) {
                                            final ImageFrame[] frames = ImageFrame.readWebp(streamData);

                                            for (ImageFrame frame : frames) {
                                                try {
                                                    bufferData.getSecond().add(frame);
                                                } catch (Throwable ex) {
                                                    CoreUtils.LOG.debugError(ex);
                                                }
                                            }
                                        } else {
                                            bufferData.getSecond().add(new ImageFrame(ImageIO.read(streamData)));
                                        }
                                        cachedImages.get(request.getFirst()).setSecond(bufferData);
                                        cachedImages.get(request.getFirst()).setThird(new ArrayList<>(bufferData.getSecond().size()));
                                    }
                                } catch (Throwable ex) {
                                    CoreUtils.LOG.debugError(ex);
                                }
                            }
                        }
                    } catch (Throwable ex) {
                        CoreUtils.LOG.debugError(ex);
                    }
                }
        ).start();
    }

    /**
     * Retrieves a Texture from an external Url, and caching it for further usage
     *
     * @param instance    The game instance to interpret
     * @param textureName The texture name to Identify this as
     * @param url         The url to retrieve the texture
     * @return The Resulting Texture Data
     */
    public static ResourceLocation getTextureFromUrl(final Minecraft instance, final String textureName, final String url) {
        try {
            return getTextureFromUrl(instance, textureName, URI.create(url).toURL());
        } catch (Throwable ex) {
            CoreUtils.LOG.debugError(ex);
            return ResourceUtils.getEmptyResource();
        }
    }

    /**
     * Retrieves a Texture from an external Url, and caching it for further usage
     *
     * @param instance    The game instance to interpret
     * @param textureName The texture name to Identify this as
     * @param url         The url to retrieve the texture
     * @return The Resulting Texture Data
     */
    public static ResourceLocation getTextureFromUrl(final Minecraft instance, final String textureName, final URL url) {
        try {
            return getTextureFromUrl(instance, textureName, new Pair<>(InputType.Url, url));
        } catch (Throwable ex) {
            CoreUtils.LOG.debugError(ex);
            return ResourceUtils.getEmptyResource();
        }
    }

    /**
     * Retrieves a Texture from an external Url, and caching it for further usage
     *
     * @param instance    The game instance to interpret
     * @param textureName The texture name to Identify this as
     * @param url         The url to retrieve the texture
     * @return The Resulting Texture Data
     */
    public static ResourceLocation getTextureFromUrl(final Minecraft instance, final String textureName, final File url) {
        try {
            return getTextureFromUrl(instance, textureName, new Pair<>(InputType.FileData, url));
        } catch (Throwable ex) {
            CoreUtils.LOG.debugError(ex);
            return ResourceUtils.getEmptyResource();
        }
    }

    /**
     * Retrieves a Texture from an external Url, and caching it for further usage
     *
     * @param instance    The game instance to interpret
     * @param textureName The texture name to Identify this as
     * @param url         The url to retrieve the texture
     * @return The Resulting Texture Data
     */
    public static ResourceLocation getTextureFromUrl(final Minecraft instance, final String textureName, final Object url) {
        if (url instanceof File file) {
            return getTextureFromUrl(instance, textureName, file);
        } else if (url instanceof URL link) {
            return getTextureFromUrl(instance, textureName, link);
        } else {
            if (url.toString().toLowerCase().startsWith("http")) {
                return getTextureFromUrl(instance, textureName, url.toString());
            } else {
                return getTextureFromUrl(
                        instance,
                        textureName,
                        new Pair<>(StringUtils.isBase64(url.toString()).getFirst() ? InputType.ByteStream : InputType.FileStream, url.toString())
                );
            }
        }
    }

    /**
     * Retrieves a Texture from an external Url, and caching it for further usage
     *
     * @param instance    The game instance to interpret
     * @param textureName The texture name to Identify this as
     * @param stream      Streaming Data containing data to read later
     * @return The Resulting Texture Data
     */
    public static ResourceLocation getTextureFromUrl(final Minecraft instance, final String textureName, final Pair<InputType, Object> stream) {
        if (!cachedImages.containsKey(textureName)) {
            // Setup Initial data if not present (Or reset if the stream has changed)
            //
            // Note that the ResourceLocation needs to be
            // initially null here for compatibility reasons
            cachedImages.put(textureName, new Tuple<>(stream, new Pair<>(0, StringUtils.newArrayList()), null));
            try {
                urlRequests.put(new Pair<>(textureName, stream));
            } catch (Throwable ex) {
                CoreUtils.LOG.debugError(ex);
            }
        }

        final Pair<Integer, List<ImageFrame>> bufferData = cachedImages.get(textureName).getSecond();

        if (bufferData == null || bufferData.getSecond() == null || bufferData.getSecond().isEmpty()) {
            return ResourceUtils.getEmptyResource();
        } else if (textureName != null) {
            final boolean isGif = textureName.endsWith(".gif") || stream.getSecond().toString().contains("gif");
            final boolean isWebp = textureName.endsWith(".webp") || stream.getSecond().toString().contains("webp");

            final boolean shouldRepeat = isGif || isWebp;
            final boolean doesContinue = bufferData.getFirst() < bufferData.getSecond().size() - 1;

            final List<ResourceLocation> resources = cachedImages.get(textureName).getThird();
            if (bufferData.getFirst() < resources.size()) {
                final ResourceLocation texLocation = resources.get(bufferData.getFirst());
                if (bufferData.getSecond().get(bufferData.getFirst()).shouldRenderNext()) {
                    if (doesContinue) {
                        bufferData.getSecond().get(bufferData.setFirst(bufferData.getFirst() + 1)).setRenderTime();
                    } else if (shouldRepeat) {
                        bufferData.getSecond().get(bufferData.setFirst(0)).setRenderTime();
                    }
                }
                return texLocation;
            }
            try {
                final DynamicTexture dynTexture = new DynamicTexture(bufferData.getSecond().get(bufferData.getFirst()).getImage());
                final ResourceLocation cachedTexture = instance.getTextureManager().getDynamicTextureLocation(textureName.toLowerCase() + (shouldRepeat ? "_" + cachedImages.get(textureName).getSecond().getFirst() : ""), dynTexture);
                if (bufferData.getSecond().get(bufferData.getFirst()).shouldRenderNext()) {
                    if (doesContinue) {
                        bufferData.getSecond().get(bufferData.setFirst(bufferData.getFirst() + 1)).setRenderTime();
                    } else if (shouldRepeat) {
                        bufferData.setFirst(0);
                    }
                }
                if (!resources.contains(cachedTexture)) {
                    resources.add(cachedTexture);
                }
                return cachedTexture;
            } catch (Throwable ex) {
                CoreUtils.LOG.debugError(ex);
                return ResourceUtils.getEmptyResource();
            }
        } else {
            return ResourceUtils.getEmptyResource();
        }
    }

    /**
     * A Mapping storing the available Input Types for External Image Parsing
     *
     * <p>
     * FileData: Parsing with Raw File Data (IE a File Object Type), to be put into
     * a FileInputStream
     * <p>
     * FileStream: Parsing with the String representation of a file path, to be put
     * into a FileInputStream
     * <p>
     * ByteStream: Parsing with a direct or String representation of a Byte array, to be put
     * into an ByteArrayInputStream. (Byte Buffer can be used with Base64 representation or direct byte conversion)
     * <p>
     * Url: Parsing with a direct or string representation of a {@link URL}, to be converted
     * to an InputStream
     * <p>
     * Unknown: Unknown property, experience can be iffy using this
     */
    public enum InputType {
        /**
         * Constant for the "File (Raw)" Input Type.
         */
        FileData,
        /**
         * Constant for the "File (Stream)" Input Mode.
         */
        FileStream,
        /**
         * Constant for the "Byte Stream" Input Mode.
         */
        ByteStream,
        /**
         * Constant for the "URL" Input Mode.
         */
        Url,
        /**
         * Constant for the "Unknown" Input Mode.
         */
        Unknown
    }
}
