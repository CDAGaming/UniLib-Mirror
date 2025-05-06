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

import com.gitlab.cdagaming.unilib.ModUtils;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.entity.EntityPlayer;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.level.World;
import com.mojang.minecraft.util.Session;

/**
 * Game-Related Utilities used to Parse base Game Data
 *
 * @author CDAGaming
 */
public class GameUtils {
    /**
     * Retrieve the Game Client Instance
     *
     * @return the Game Client Instance
     */
    public static Minecraft getMinecraft() {
        return ModUtils.getMinecraft();
    }

    /**
     * Retrieve the Game World Instance
     *
     * @return the Game World Instance
     */
    public static World getWorld() {
        return WorldUtils.getWorld(getMinecraft());
    }

    /**
     * Retrieve the Game Player Instance
     *
     * @return the Game Player Instance
     */
    public static EntityPlayer getPlayer() {
        return WorldUtils.getPlayer(getMinecraft());
    }

    /**
     * Retrieve the Game Session Instance
     *
     * @param client the game client instance
     * @return the Game Session Instance
     */
    public static Session getSession(final Minecraft client) {
        return client != null ? client.session : null;
    }

    /**
     * Retrieve the Game Session Instance
     *
     * @return the Game Session Instance
     */
    public static Session getSession() {
        return getSession(getMinecraft());
    }

    /**
     * Retrieve the Game Session Username
     *
     * @param client the game client instance
     * @return the Game Session Username
     */
    public static String getUsername(final Minecraft client) {
        return getSession(client).username;
    }

    /**
     * Retrieve the Game Session Username
     *
     * @return the Game Session Username
     */
    public static String getUsername() {
        return getUsername(getMinecraft());
    }

    /**
     * Retrieve the Game Session UUID
     *
     * @param client the game client instance
     * @return the Game Session UUID
     */
    public static String getUuid(final Minecraft client) {
        return null;
    }

    /**
     * Retrieve the Game Session UUID
     *
     * @return the Game Session UUID
     */
    public static String getUuid() {
        return getUuid(getMinecraft());
    }

    /**
     * Retrieve the game's current screen instance
     *
     * @param client the game client instance
     * @return the game's current screen instance
     */
    public static GuiScreen getCurrentScreen(final Minecraft client) {
        return client != null ? client.currentScreen : null;
    }

    /**
     * Retrieve the game's current screen instance
     *
     * @return the game's current screen instance
     */
    public static GuiScreen getCurrentScreen() {
        return getCurrentScreen(getMinecraft());
    }

    /**
     * Retrieve whether the game client is "in-focus"
     *
     * @param client the game client instance
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public static boolean isFocused(final Minecraft client) {
        return getCurrentScreen(client) != null && (client.inGameHasFocus || WorldUtils.getPlayer(client) != null);
    }

    /**
     * Retrieve whether the game client is "in-focus", depending on the Game Version
     * <p>Defined as: If an Element is being focused on in a GUI or if a GUI is currently open
     *
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public static boolean isFocused() {
        return isFocused(getMinecraft());
    }

    /**
     * Retrieve whether the game client is "loaded"
     * <p>Defined as whether the screen is non-null or the player is non-null
     *
     * @param client the game client instance
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public static boolean isLoaded(final Minecraft client) {
        return getCurrentScreen(client) != null || WorldUtils.getPlayer(client) != null;
    }

    /**
     * Retrieve whether the game client is "loaded"
     * <p>Defined as whether the screen is non-null or the player is non-null
     *
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public static boolean isLoaded() {
        return isLoaded(getMinecraft());
    }
}
