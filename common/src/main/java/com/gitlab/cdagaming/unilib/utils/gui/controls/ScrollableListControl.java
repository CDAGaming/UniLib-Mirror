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

package com.gitlab.cdagaming.unilib.utils.gui.controls;

import com.gitlab.cdagaming.unilib.utils.gui.RenderUtils;
import com.gitlab.cdagaming.unilib.utils.gui.integrations.ExtendedScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Gui Widget for a Scrollable List
 *
 * @author CDAGaming
 */
@SuppressWarnings("DuplicatedCode")
public class ScrollableListControl extends EntryListPane<ScrollableListControl.StringEntry> {
    /**
     * The default slot height
     */
    public static final int DEFAULT_SLOT_HEIGHT = 18;
    /**
     * The Currently Selected Value in the List
     */
    public String currentValue;
    /**
     * The Items available to select within the List Gui
     */
    public List<String> itemList;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param mc           The Minecraft Instance for this Control
     * @param parentScreen The parent screen instance for this control
     * @param width        The Width of this Control
     * @param height       The Height of this Control
     * @param topIn        How far from the top of the Screen the List should render at
     * @param bottomIn     How far from the bottom of the Screen the List should render at
     * @param slotHeightIn The height of each slot in the list
     * @param itemList     The List of items to allocate for the slots in the Gui
     * @param currentValue The current value, if any, to select upon initialization of the Gui
     */
    public ScrollableListControl(@Nonnull final Minecraft mc, final ExtendedScreen parentScreen, final int width, final int height, final int topIn, final int bottomIn, final int slotHeightIn, final List<String> itemList, final String currentValue) {
        super(mc, width, height, topIn, slotHeightIn);
        setParent(parentScreen);
        setList(itemList);
        this.currentValue = currentValue;
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param mc           The Minecraft Instance for this Control
     * @param parentScreen The parent screen instance for this control
     * @param width        The Width of this Control
     * @param height       The Height of this Control
     * @param topIn        How far from the top of the Screen the List should render at
     * @param bottomIn     How far from the bottom of the Screen the List should render at
     * @param itemList     The List of items to allocate for the slots in the Gui
     * @param currentValue The current value, if any, to select upon initialization of the Gui
     */
    public ScrollableListControl(@Nonnull final Minecraft mc, final ExtendedScreen parentScreen, final int width, final int height, final int topIn, final int bottomIn, final List<String> itemList, final String currentValue) {
        this(
                mc,
                parentScreen,
                width, height,
                topIn, bottomIn,
                DEFAULT_SLOT_HEIGHT,
                itemList,
                currentValue
        );
    }

    @Override
    public void setSelected(StringEntry entry) {
        super.setSelected(entry);
        if (entry != null) {
            currentValue = entry.name;
        }
    }

    /**
     * Retrieves the Amount of Items in the List
     *
     * @return The Amount of Items in the List
     */
    @Override
    protected int getItemCount() {
        return itemList.size();
    }

    /**
     * Sets the item list to be rendered (And resets the scroll if needed)
     *
     * @param itemList The list to interpret
     * @return {@link Boolean#TRUE} if list was modified
     */
    public boolean setList(List<String> itemList) {
        if (itemList == null) {
            itemList = StringUtils.newArrayList();
        }
        if (!itemList.equals(this.itemList)) {
            this.itemList = itemList;
            // Reset the scrollbar to prevent OOB issues
            scrollBy(Integer.MIN_VALUE);

            if (isLoaded()) {
                syncEntries();
            }
            return true;
        }
        return false;
    }

    @Override
    public void refreshContentHeight() {
        super.refreshContentHeight();
        syncEntries();
    }

    /**
     * Setup Mappings between the entries original name, and it's entry variant
     */
    public void syncEntries() {
        clearEntries();

        for (String item : StringUtils.newArrayList(itemList)) {
            final StringEntry dataEntry = new StringEntry(item);
            addEntry(dataEntry);
            if (item.equals(currentValue)) {
                setSelected(dataEntry);
            }
        }

        if (getSelected() != null) {
            centerScrollOn(getSelected());
        }
    }

    /**
     * Renders a Slot Entry for this Control
     *
     * @param matrices     The Matrix Stack, used for Rendering
     * @param originalName The original entry name, before processing
     * @param xPos         The Starting X Position to render the Object at
     * @param yPos         The Starting Y Position to render the Object at
     * @param widthIn      The Width for the Object to render to
     * @param heightIn     The Height for the Object to render to
     * @param mouseXIn     The Mouse's Current X Position
     * @param mouseYIn     The Mouse's Current Y Position
     * @param hovered      Whether the entry is being hovered over
     * @param partialTicks The Rendering Tick Rate
     */
    public void renderSlotItem(@Nonnull final PoseStack matrices, final String originalName, final int xPos, final int yPos, final int widthIn, final int heightIn, final int mouseXIn, final int mouseYIn, final boolean hovered, final float partialTicks) {
        RenderUtils.renderScrollingString(matrices,
                getGameInstance(),
                getFontRenderer(),
                originalName,
                xPos + (RenderUtils.getStringWidth(getFontRenderer(), originalName) / 2),
                xPos, yPos,
                xPos + widthIn - 4,
                yPos + heightIn,
                0xFFFFFF
        );
    }

    /**
     * Gui Entry for a Scrollable List
     *
     * @author CDAGaming
     */
    public class StringEntry extends EntryListPane.Entry<StringEntry> {
        /**
         * The name of this Entry
         */
        private final String name;

        /**
         * Initialization Event for this Control, assigning defined arguments
         *
         * @param name The name to assign to this Entry
         */
        public StringEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(@Nonnull final PoseStack matrices, Minecraft client, int index, int yPos, int xPos, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTicks) {
            ScrollableListControl.this.renderSlotItem(matrices, name, xPos, yPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTicks);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (isValidMouseClick(button)) {
                this.onPressed();
                return true;
            } else {
                return false;
            }
        }

        /**
         * The Event to occur when this Entry is pressed
         */
        private void onPressed() {
            ScrollableListControl.this.setSelected(this);
        }
    }
}
