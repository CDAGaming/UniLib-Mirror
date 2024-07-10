package com.gitlab.cdagaming.unilib.utils;

import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class WorldUtils {
    public static World getWorld(final Minecraft client) {
        return client != null ? client.world : null;
    }

    public static World getWorld(final Entity entity) {
        return entity != null ? entity.world : null;
    }

    public static EntityPlayer getPlayer(final Minecraft client) {
        return client != null ? client.player : null;
    }

    /**
     * Retrieves the entities display name, derived from the original supplied name
     *
     * @param entity          The entity to interpret
     * @param stripFormatting Whether the resulting name should have its formatting stripped
     * @return The formatted entity display name to use
     */
    public static String getEntityName(final Entity entity, final boolean stripFormatting) {
        String result = "";
        if (entity != null) {
            result = StringUtils.getOrDefault(
                    entity.getDisplayName().getFormattedText(),
                    entity.getName()
            );
        }

        if (stripFormatting) {
            result = StringUtils.stripAllFormatting(result);
        }
        return result;
    }

    /**
     * Retrieves the entities display name, derived from the original supplied name
     *
     * @param entity The entity to interpret
     * @return The formatted entity display name to use
     */
    public static String getEntityName(final Entity entity) {
        return getEntityName(entity, true);
    }

    /**
     * Retrieve the weather, utilizing the world
     *
     * @param world The world object to interpret
     * @return the current weather data
     */
    public static String getWeather(final World world) {
        String name = "clear";
        if (world != null) {
            final WorldInfo info = world.getWorldInfo();
            if (info.isThundering()) {
                name = "thunder";
            } else if (info.isRaining()) {
                name = "rain";
            } else {
                name = "clear";
            }
        }
        return name;
    }

    /**
     * Retrieve the weather, utilizing the entity's world
     *
     * @param entity The entity to interpret
     * @return the current weather data
     */
    public static String getWeather(final Entity entity) {
        return getWeather(getWorld(entity));
    }
}
