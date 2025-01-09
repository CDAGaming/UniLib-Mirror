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

import io.github.cdagaming.unicore.utils.MappingUtils;
import io.github.cdagaming.unicore.utils.MathUtils;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.List;

/**
 * Game-Related Utilities used to Parse Item Data
 *
 * @author CDAGaming
 */
public class ItemUtils {
    /**
     * An Instance of an Empty Item
     */
    public static final Item EMPTY_ITEM = null;
    /**
     * An Instance of an Empty ItemStack
     */
    public static final ItemStack EMPTY_STACK = (ItemStack) null;
    /**
     * A list of items that count as "empty"
     */
    private static final List<Item> EMPTY_ITEMS = StringUtils.newArrayList(
            EMPTY_ITEM
    );

    /**
     * Retrieve or convert the specified object to an {@link ItemStack}
     *
     * @param data the data to interpret
     * @return the converted or retrieved {@link ItemStack}
     */
    public static ItemStack getStackFrom(Object data) {
        ItemStack itemStack = null;
        if (data != null) {
            if (data instanceof Block block) {
                data = getDefaultInstance(block);
            }
            if (data instanceof Item item) {
                data = getDefaultInstance(item);
            }
            if (data instanceof ItemStack stack) {
                itemStack = stack;
            }
        }
        return itemStack;
    }

    /**
     * Retrieve the {@link Item} form of the specified {@link ItemStack}
     *
     * @param stack The {@link ItemStack} to evaluate
     * @return the {@link Item} form of the stack, or an empty item if the stack is null
     */
    public static Item getItemFromStack(final ItemStack stack) {
        return stack != null ? stack.getItem() : EMPTY_ITEM;
    }

    /**
     * Retrieve the stack count of the specified {@link ItemStack}
     *
     * @param stack The {@link ItemStack} to evaluate
     * @return the stack count of the stack, or 0 if the stack is null
     */
    public static int getStackCount(final ItemStack stack) {
        return stack != null ? stack.stackSize : 0;
    }

    /**
     * Retrieve the current item damage from the specified {@link ItemStack}
     *
     * @param stack The {@link ItemStack} to evaluate
     * @return the current item damage from the stack, or 0 if the stack is null
     */
    public static int getStackDamage(final ItemStack stack) {
        return stack != null ? stack.itemDamage : 0;
    }

    /**
     * Determines whether the Specified {@link ItemStack} classifies as NULL or EMPTY
     *
     * @param data The {@link ItemStack} to evaluate
     * @return {@link Boolean#TRUE} if the ItemStack classifies as NULL or EMPTY
     */
    public static boolean isItemEmpty(final Object data) {
        final ItemStack stack = getStackFrom(data);
        if (stack == null || stack.equals(EMPTY_STACK)) {
            return true;
        } else {
            return EMPTY_ITEMS.contains(getItemFromStack(stack)) ||
                    getStackCount(stack) <= 0 ||
                    !MathUtils.isWithinValue(
                            getStackDamage(stack),
                            -32768, 65535
                    );
        }
    }

    /**
     * Returns the Default Variant of the Specified Block
     *
     * @param block The Block to evaluate
     * @return The default variant of the item
     */
    public static ItemStack getDefaultInstance(final Block block) {
        return new ItemStack(block);
    }

    /**
     * Returns the Default Variant of the Specified Item
     *
     * @param item The Item to evaluate
     * @return The default variant of the item
     */
    public static ItemStack getDefaultInstance(final Item item) {
        return new ItemStack(item);
    }

    /**
     * Retrieves the entities display name, derived from the original supplied name
     *
     * @param data            The {@link ItemStack} to interpret
     * @param stripFormatting Whether the resulting name should have its formatting stripped
     * @return The formatted entity display name to use
     */
    public static String getItemName(final Object data, final boolean stripFormatting) {
        final ItemStack stack = getStackFrom(data);
        String result = "";
        if (!isItemEmpty(stack)) {
            result = MappingUtils.getClassName(
                    stack.getItem()
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
     * @param data The {@link ItemStack} to interpret
     * @return The formatted entity display name to use
     */
    public static String getItemName(final Object data) {
        return getItemName(data, true);
    }

    /**
     * Returns whether the specified name contains raw data
     *
     * @param name The name to interpret
     * @return {@link Boolean#TRUE} if the condition is satisfied
     */
    public static boolean isRawTE(final String name) {
        if (!StringUtils.isNullOrEmpty(name)) {
            final String lowerName = name.toLowerCase();
            return lowerName.contains("tile.") ||
                    lowerName.contains("item.") ||
                    lowerName.contains(".") ||
                    lowerName.contains(".name");
        }
        return false;
    }
}
