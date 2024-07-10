package com.gitlab.cdagaming.unilib.utils;

import com.gitlab.cdagaming.unilib.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class GameUtils {
    public static Minecraft getMinecraft() {
        return ModUtils.getMinecraft();
    }

    public static World getWorld() {
        return WorldUtils.getWorld(getMinecraft());
    }

    public static EntityPlayer getPlayer() {
        return WorldUtils.getPlayer(getMinecraft());
    }

    public static Session getSession(final Minecraft client) {
        return client != null ? client.getSession() : null;
    }

    public static Session getSession() {
        return getSession(getMinecraft());
    }

    public static String getUsername(final Minecraft client) {
        return getSession(client).getUsername();
    }

    public static String getUsername() {
        return getUsername(getMinecraft());
    }

    public static String getUuid(final Minecraft client) {
        return getSession(client).getPlayerID();
    }

    public static String getUuid() {
        return getUuid(getMinecraft());
    }

    public static GuiScreen getCurrentScreen(final Minecraft client) {
        return client != null ? client.currentScreen : null;
    }

    public static GuiScreen getCurrentScreen() {
        return getCurrentScreen(getMinecraft());
    }

    public static boolean isFocused(final Minecraft client) {
        final GuiScreen screen = getCurrentScreen(client);
        return screen != null && (screen.isFocused() || getPlayer() != null);
    }

    public static boolean isFocused() {
        return isFocused(getMinecraft());
    }
}
