package com.gitlab.cdagaming.unilib.utils;

import io.github.cdagaming.unicore.utils.MathUtils;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemUtils {
    /**
     * An Instance of an Empty Item
     */
    public static final Item EMPTY_ITEM = null;
    /**
     * An Instance of an Empty ItemStack
     */
    public static final ItemStack EMPTY_STACK = ItemStack.EMPTY;
    /**
     * A list of items that count as "empty"
     */
    private static final List<Item> EMPTY_ITEMS = StringUtils.newArrayList(
            EMPTY_ITEM,
            Items.AIR
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

    public static Item getItemFromStack(final ItemStack stack) {
        return stack != null ? stack.getItem() : EMPTY_ITEM;
    }

    public static int getStackCount(final ItemStack stack) {
        return stack != null ? stack.getCount() : 0;
    }

    public static int getStackDamage(final ItemStack stack) {
        return stack != null ? stack.getItemDamage() : 0;
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
            result = StringUtils.getOrDefault(
                    stack.getDisplayName()
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
