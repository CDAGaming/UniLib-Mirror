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
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.WeatherRain;
import net.minecraft.core.world.weather.WeatherSnow;
import net.minecraft.core.world.weather.WeatherStorm;

/**
 * Game-Related Utilities used to Parse World Data
 *
 * @author CDAGaming
 */
public class WorldUtils {
    /**
     * Retrieve the Game World Instance
     *
     * @param client the game client instance
     * @return the Game World Instance
     */
    public static World getWorld(final Minecraft client) {
        return client != null ? client.theWorld : null;
    }

    /**
     * Retrieve the Entity World Instance
     *
     * @param entity The entity to interpret
     * @return the Entity World Instance
     */
    public static World getWorld(final Entity entity) {
        return entity != null ? entity.world : null;
    }

    /**
     * Retrieve the Game Player Instance
     *
     * @param client the game client instance
     * @return the Game Player Instance
     */
    public static EntityPlayer getPlayer(final Minecraft client) {
        return client != null ? client.thePlayer : null;
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
                    EntityDispatcher.getEntityString(entity)
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
            final Weather info = world.getCurrentWeather();
            if (info instanceof WeatherStorm) {
                name = "thunder";
            } else if (info instanceof WeatherRain || info instanceof WeatherSnow) {
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
