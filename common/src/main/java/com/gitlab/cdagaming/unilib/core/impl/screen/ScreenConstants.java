/*
 * MIT License
 *
 * Copyright (c) 2018 - 2026 CDAGaming (cstack2011@yahoo.com)
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

package com.gitlab.cdagaming.unilib.core.impl.screen;

import io.github.cdagaming.unicore.utils.StringUtils;

import java.awt.*;

/**
 * Constant Variables and Methods used for Rendering Operations
 *
 * @author CDAGaming
 */
public class ScreenConstants {
    /**
     * The Default Tooltip Background Info
     */
    public static final ColorData DEFAULT_TOOLTIP_BACKGROUND = new ColorData(
            new ColorSection(16, 0, 16, 240)
    );
    /**
     * The Default Tooltip Border Info
     */
    public static final ColorData DEFAULT_TOOLTIP_BORDER = new ColorData(
            new ColorSection(80, 0, 255, 80),
            new ColorSection(40, 0, 127, 80)
    );
    /**
     * The Default Screen Background Resources
     */
    private static final String DEFAULT_GUI_BACKGROUND = "minecraft:textures/gui/menu_background.png";
    /**
     * The Default Screen Background Info
     */
    public static final ColorData DEFAULT_SCREEN_BACKGROUND = new ColorData(
            getDefaultGUIBackground()
    );
    /**
     * The alternative Screen Background Resources
     */
    private static final String DEFAULT_GUI_BACKGROUND_ALT = "minecraft:textures/gui/menu_list_background.png";
    /**
     * The Default Alternative Screen Background Info
     */
    public static final ColorData DEFAULT_ALT_SCREEN_BACKGROUND = new ColorData(
            getDefaultGUIBackgroundAlt()
    );
    /**
     * The Default Screen Background Resources, while in a world
     */
    private static final String DEFAULT_WORLD_GUI_BACKGROUND = "minecraft:textures/gui/inworld_menu_background.png";
    /**
     * The Default Screen Background Info, while in a world
     */
    public static final ColorData DEFAULT_WORLD_SCREEN_BACKGROUND = new ColorData(
            getDefaultWorldGUIBackground()
    );
    /**
     * The alternative Screen Background Resources, while in a world
     */
    private static final String DEFAULT_WORLD_GUI_BACKGROUND_ALT = "minecraft:textures/gui/inworld_menu_list_background.png";
    /**
     * The Default Alternative Screen Background Info, while in a world
     */
    public static final ColorData DEFAULT_ALT_WORLD_SCREEN_BACKGROUND = new ColorData(
            getDefaultWorldGUIBackgroundAlt()
    );
    /**
     * The default Tooltip Rendering Info
     */
    private static final TooltipData DEFAULT_TOOLTIP = new TooltipData(
            true, DEFAULT_TOOLTIP_BACKGROUND, DEFAULT_TOOLTIP_BORDER
    );
    /**
     * The tooltip Rendering Info for an Empty Background and Border
     */
    private static final TooltipData EMPTY_TOOLTIP = new TooltipData(
            true, null, null
    );

    /**
     * Retrieve The Default Screen Background Resources
     *
     * @return The Default Screen Background Resources
     */
    public static String getDefaultGUIBackground() {
        return DEFAULT_GUI_BACKGROUND;
    }

    /**
     * Retrieve The alternative Screen Background Resources
     *
     * @return The alternative Screen Background Resources
     */
    public static String getDefaultGUIBackgroundAlt() {
        return DEFAULT_GUI_BACKGROUND_ALT;
    }

    /**
     * Retrieve The Default Screen Background Resources, while in a world
     *
     * @return The Default Screen Background Resources, while in a world
     */
    public static String getDefaultWorldGUIBackground() {
        return DEFAULT_WORLD_GUI_BACKGROUND;
    }

    /**
     * Retrieve The alternative Screen Background Resources, while in a world
     *
     * @return The alternative Screen Background Resources, while in a world
     */
    public static String getDefaultWorldGUIBackgroundAlt() {
        return DEFAULT_WORLD_GUI_BACKGROUND_ALT;
    }

    /**
     * Retrieve the tooltip Rendering Info for an Empty Background and Border
     *
     * @return the tooltip Rendering Info for an Empty Background and Border
     */
    public static TooltipData getEmptyTooltip() {
        return EMPTY_TOOLTIP;
    }

    /**
     * Retrieve the Default Tooltip Rendering Info
     *
     * @return the default Tooltip Rendering Info
     */
    public static TooltipData getDefaultTooltip() {
        return DEFAULT_TOOLTIP;
    }

    /**
     * Record Mapping for storing Tooltip Rendering Info
     *
     * @param renderTooltips  Whether rendering tooltips is allowed
     * @param backgroundColor The background {@link ColorData} for the tooltip
     * @param borderColor     The border {@link ColorData} for the tooltip
     */
    public record TooltipData(boolean renderTooltips, ColorData backgroundColor, ColorData borderColor) {
    }

    /**
     * Record Mapping for storing Color Information
     *
     * @param start          The Starting {@link ColorSection} info
     * @param end            The Ending {@link ColorSection} info
     * @param texLocation    The texture location, if any
     * @param texLevel       The Z-Level to use when rendering as a texture
     * @param colorLevel     The Z-Level to use when rendering as color-only
     * @param useFullTexture Whether to render as full-texture or color-only
     * @param textureWidth   The Width of the Texture
     * @param textureHeight  The Height of the Texture
     */
    public record ColorData(ColorSection start, ColorSection end,
                            String texLocation,
                            double texLevel, double colorLevel,
                            boolean useFullTexture,
                            double textureWidth, double textureHeight) {
        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link ColorSection} info
         * @param end            The Ending {@link ColorSection} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         */
        public ColorData(ColorSection start, ColorSection end,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture) {
            this(start, end, texLocation, texLevel, colorLevel, useFullTexture, 32.0D, 32.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link ColorSection} info
         * @param end         The Ending {@link ColorSection} info
         * @param texLocation The texture location, if any
         * @param texLevel    The Z-Level to use when rendering as a texture
         * @param colorLevel  The Z-Level to use when rendering as color-only
         */
        public ColorData(ColorSection start, ColorSection end,
                         String texLocation,
                         double texLevel, double colorLevel) {
            this(start, end, texLocation, texLevel, colorLevel, true);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link ColorSection} info
         * @param end         The Ending {@link ColorSection} info
         * @param texLocation The texture location, if any
         */
        public ColorData(ColorSection start, ColorSection end, String texLocation) {
            this(start, end, texLocation, 0.0D, 300.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start The Starting {@link ColorSection} info
         * @param end   The Ending {@link ColorSection} info
         */
        public ColorData(ColorSection start, ColorSection end) {
            this(start, end, "");
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link ColorSection} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         * @param textureWidth   The Width of the Texture
         * @param textureHeight  The Height of the Texture
         */
        public ColorData(ColorSection start,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture,
                         double textureWidth, double textureHeight) {
            this(start, null, texLocation, texLevel, colorLevel, useFullTexture, textureWidth, textureHeight);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link ColorSection} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         */
        public ColorData(ColorSection start,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture) {
            this(start, texLocation, texLevel, colorLevel, useFullTexture, 32.0D, 32.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link ColorSection} info
         * @param texLocation The texture location, if any
         * @param texLevel    The Z-Level to use when rendering as a texture
         * @param colorLevel  The Z-Level to use when rendering as color-only
         */
        public ColorData(ColorSection start,
                         String texLocation,
                         double texLevel, double colorLevel) {
            this(start, texLocation, texLevel, colorLevel, true);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link ColorSection} info
         * @param texLocation The texture location, if any
         */
        public ColorData(ColorSection start, String texLocation) {
            this(start, texLocation, 0.0D, 300.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start The Starting {@link ColorSection} info
         */
        public ColorData(ColorSection start) {
            this(start, "");
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link Color} info
         * @param end            The Ending {@link Color} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         * @param textureWidth   The Width of the Texture
         * @param textureHeight  The Height of the Texture
         */
        public ColorData(Color start, Color end,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture,
                         double textureWidth, double textureHeight) {
            this(new ColorSection(start), end != null ? new ColorSection(end) : null, texLocation,
                    texLevel, colorLevel, useFullTexture, textureWidth, textureHeight);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link Color} info
         * @param end            The Ending {@link Color} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         */
        public ColorData(Color start, Color end,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture) {
            this(start, end, texLocation, texLevel, colorLevel, useFullTexture, 32.0D, 32.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link Color} info
         * @param end         The Ending {@link Color} info
         * @param texLocation The texture location, if any
         * @param texLevel    The Z-Level to use when rendering as a texture
         * @param colorLevel  The Z-Level to use when rendering as color-only
         */
        public ColorData(Color start, Color end,
                         String texLocation,
                         double texLevel, double colorLevel) {
            this(start, end, texLocation, texLevel, colorLevel, true);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link Color} info
         * @param end         The Ending {@link Color} info
         * @param texLocation The texture location, if any
         */
        public ColorData(Color start, Color end, String texLocation) {
            this(start, end, texLocation, 0.0D, 300.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start The Starting {@link Color} info
         * @param end   The Ending {@link Color} info
         */
        public ColorData(Color start, Color end) {
            this(start, end, "");
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link Color} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         * @param textureWidth   The Width of the Texture
         * @param textureHeight  The Height of the Texture
         */
        public ColorData(Color start,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture,
                         double textureWidth, double textureHeight) {
            this(start, null, texLocation, texLevel, colorLevel, useFullTexture, textureWidth, textureHeight);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start          The Starting {@link Color} info
         * @param texLocation    The texture location, if any
         * @param texLevel       The Z-Level to use when rendering as a texture
         * @param colorLevel     The Z-Level to use when rendering as color-only
         * @param useFullTexture Whether to render as full-texture or color-only
         */
        public ColorData(Color start,
                         String texLocation,
                         double texLevel, double colorLevel,
                         boolean useFullTexture) {
            this(start, texLocation, texLevel, colorLevel, useFullTexture, 32.0D, 32.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link Color} info
         * @param texLocation The texture location, if any
         * @param texLevel    The Z-Level to use when rendering as a texture
         * @param colorLevel  The Z-Level to use when rendering as color-only
         */
        public ColorData(Color start,
                         String texLocation,
                         double texLevel, double colorLevel) {
            this(start, texLocation, texLevel, colorLevel, true);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start       The Starting {@link Color} info
         * @param texLocation The texture location, if any
         */
        public ColorData(Color start, String texLocation) {
            this(start, texLocation, 0.0D, 300.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param start The Starting {@link Color} info
         */
        public ColorData(Color start) {
            this(start, "");
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param texLocation   The texture location, if any
         * @param texLevel      The Z-Level to use when rendering as a texture
         * @param textureWidth  The Width of the Texture
         * @param textureHeight The Height of the Texture
         */
        public ColorData(String texLocation,
                         double texLevel,
                         double textureWidth, double textureHeight) {
            this(new ColorSection(), texLocation, texLevel, 300.0D, true, textureWidth, textureHeight);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param texLocation The texture location, if any
         * @param texLevel    The Z-Level to use when rendering as a texture
         */
        public ColorData(String texLocation, double texLevel) {
            this(texLocation, texLevel, 32.0D, 32.0D);
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param texLocation The texture location, if any
         */
        public ColorData(String texLocation) {
            this(texLocation, 0.0D);
        }

        /**
         * Record Mapping for storing Color Information
         */
        public ColorData() {
            this("");
        }

        /**
         * Record Mapping for storing Color Information
         *
         * @param other The {@link ColorData} to interpret
         */
        public ColorData(ColorData other) {
            this(other.start, other.end, other.texLocation);
        }

        /**
         * Whether the ending {@link ColorSection} is present
         *
         * @return {@link Boolean#TRUE} if present
         */
        public boolean hasEnd() {
            return end != null;
        }

        /**
         * Retrieve the ending {@link ColorSection} info, if present
         *
         * @return the ending {@link ColorSection} info, or the starting info if not present
         */
        @Override
        public ColorSection end() {
            return hasEnd() ? end : start();
        }

        /**
         * Retrieve the starting {@link Color} instance
         *
         * @return the starting {@link Color} instance
         */
        public Color startColor() {
            return start().color();
        }

        /**
         * Retrieve the ending {@link Color} instance
         *
         * @return the ending {@link Color} instance
         */
        public Color endColor() {
            return end().color();
        }

        /**
         * Whether the texture location is present
         *
         * @return {@link Boolean#TRUE} if present
         */
        public boolean hasTexLocation() {
            return !StringUtils.isNullOrEmpty(texLocation);
        }

        /**
         * Retrieve the texture location, if present
         *
         * @return the texture location, or an empty string if not present
         */
        @Override
        public String texLocation() {
            return hasTexLocation() ? texLocation : "";
        }
    }

    /**
     * Record Mapping for storing Color Section Information
     *
     * @param red   The color index for the Red Channel
     * @param green The color index for the Green Channel
     * @param blue  The color index for the Blue Channel
     * @param alpha The color index for the Alpha Channel
     */
    public record ColorSection(int red, int green, int blue, int alpha) {
        /**
         * Record Mapping for storing Color Section Information
         *
         * @param color The {@link Color} to interpret
         */
        public ColorSection(Color color) {
            this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        }

        /**
         * Record Mapping for storing Color Section Information
         *
         * @param other The {@link ColorSection} to interpret
         */
        public ColorSection(ColorSection other) {
            this(other.red, other.green, other.blue, other.alpha);
        }

        /**
         * Record Mapping for storing Color Section Information
         */
        public ColorSection() {
            this(Color.white);
        }

        /**
         * Retrieve the {@link Color} instance
         *
         * @return the {@link Color} instance
         */
        public Color color() {
            return StringUtils.getColorFrom(red(), green(), blue(), alpha());
        }
    }
}
