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
import com.gitlab.cdagaming.unilib.utils.gui.integrations.ScrollPane;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A Simplified Entry List Widget, using techniques from {@link ScrollPane} and Mojang's GuiSlot system
 *
 * @param <E> The entry type for the widget
 * @author CDAGaming
 */
public abstract class EntryListPane<E extends EntryListPane.Entry<E>> extends ScrollPane {
    /**
     * The default translation for narration usage
     */
    private static final Component USAGE_NARRATION = Component.translatable("narration.selection.usage");
    /**
     * The height of each item in the list
     */
    protected final int itemHeight;
    /**
     * The list of entries in this widget
     */
    private final List<E> children = new EntryListPane<E>.TrackedList();
    /**
     * The height of the header element, or 0 if disabled
     */
    protected int headerHeight;
    /**
     * Whether this widget is rendering a header element
     */
    private boolean renderHeader;
    /**
     * The selected entry in the list
     */
    @Nullable
    private E selected;
    /**
     * The hovered entry in the list
     */
    @Nullable
    private E hovered;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param client     The current game instance
     * @param width      The width of the widget
     * @param height     The height of the widget
     * @param y          The starting Y position of the widget
     * @param itemHeight The height of each entry for the widget
     */
    public EntryListPane(final Minecraft client,
                         final int width, final int height,
                         final int y, final int itemHeight) {
        super(0, y, width, height);
        setGameInstance(client);
        setCanModifyControls(false);
        this.itemHeight = itemHeight;
    }

    /**
     * Sets whether we are rendering a header element to this widget
     *
     * @param renderHeader Whether this widget is rendering a header element
     * @param headerHeight The height of the header element, or 0 if disabled
     */
    protected void setRenderHeader(final boolean renderHeader, final int headerHeight) {
        this.renderHeader = renderHeader;
        this.headerHeight = headerHeight;
        if (!renderHeader) {
            this.headerHeight = 0;
        }
    }

    /**
     * Retrieve the maximum width of each entry row
     *
     * @return the maximum entry row width
     */
    public int getRowWidth() {
        return 220;
    }

    /**
     * Retrieve the selected entry in the list
     *
     * @return the current selection, or null
     */
    @Nullable
    public E getSelected() {
        return selected;
    }

    /**
     * Set the selected entry in the list
     *
     * @param selected The new selection, which can be null
     */
    public void setSelected(@Nullable final E selected) {
        this.selected = selected;
    }

    /**
     * Retrieve the first element in the entry list
     *
     * @return the first element found in the entry list
     */
    public E getFirstElement() {
        return getEntry(0);
    }

    @Nullable
    public E getFocused() {
        return (E) super.getFocused();
    }

    @Nullable
    @Override
    public ComponentPath nextFocusPath(@Nonnull FocusNavigationEvent arg) {
        if (getItemCount() == 0) {
            return null;
        } else if (isFocused() && arg instanceof FocusNavigationEvent.ArrowNavigation lv) {
            final E nextEntry = nextEntry(lv.direction());
            return nextEntry != null ? ComponentPath.path(this, ComponentPath.leaf(nextEntry)) : null;
        } else if (!isFocused()) {
            E selected = getSelected();
            if (selected == null) {
                selected = nextEntry(arg.getVerticalDirectionForInitialFocus());
            }

            return selected == null ? null : ComponentPath.path(this, ComponentPath.leaf(selected));
        } else {
            return null;
        }
    }

    @Override
    public void updateNarration(@Nonnull NarrationElementOutput arg) {
        final E hovered = getHovered();
        if (hovered != null) {
            narrateListElementPosition(arg.nest(), hovered);
            hovered.updateNarration(arg);
        } else {
            final E selected = getSelected();
            if (selected != null) {
                narrateListElementPosition(arg.nest(), selected);
                selected.updateNarration(arg);
            }
        }

        if (isFocused()) {
            arg.add(NarratedElementType.USAGE, USAGE_NARRATION);
        }
    }

    /**
     * Retrieve the list of entries in this widget
     *
     * @return the current entry list
     */
    @Override
    public final List<E> children() {
        return children;
    }

    /**
     * Clears the list of entries in this widget
     */
    protected void clearEntries() {
        children.clear();
        selected = null;
    }

    /**
     * Replace the current entry list with the specified data
     *
     * @param collection The new entry list to interpret
     */
    protected void replaceEntries(final Collection<E> collection) {
        clearEntries();
        children.addAll(collection);
    }

    /**
     * Retrieve the entry at the specified index
     *
     * @param index The index to interpret
     * @return The entry at the specified index, if found
     */
    protected E getEntry(final int index) {
        return children.get(index);
    }

    /**
     * Add an entry to the entry list for this widget
     *
     * @param entry The entry to interpret
     * @return The new entry list size
     */
    protected int addEntry(final E entry) {
        children.add(entry);
        return getItemCount() - 1;
    }

    /**
     * Add an entry to the top of the entry list for this widget
     *
     * @param entry The entry to interpret
     */
    protected void addEntryToTop(final E entry) {
        final float delta = getMaxScroll() - getAmountScrolled();
        children.addFirst(entry);
        setAmountScrolled(getMaxScroll() - delta);
    }

    /**
     * Removes an entry from the top of the entry list for this widget
     *
     * @param entry The entry to interpret
     * @return {@link Boolean#TRUE} if successfully removed
     */
    protected boolean removeEntryFromTop(final E entry) {
        final float delta = getMaxScroll() - getAmountScrolled();
        final boolean hasRemoved = removeEntry(entry);
        setAmountScrolled(getMaxScroll() - delta);
        return hasRemoved;
    }

    /**
     * Retrieve the amount of items in the entry list
     *
     * @return The entry list item count
     */
    protected int getItemCount() {
        return children.size();
    }

    /**
     * Retrieve whether the specified index is that of the current selected item
     *
     * @param index The item index to interpret
     * @return {@link Boolean#TRUE} if the index matches the current selection
     */
    protected boolean isSelectedItem(final int index) {
        return Objects.equals(getSelected(), getEntry(index));
    }

    /**
     * Retrieve the entry at the specified mouse position, if possible
     *
     * @param mouseX The X position to interpret
     * @param mouseY The Y position to interpret
     * @return The entry at the specified position, or null if no entry found
     */
    @Nullable
    protected final E getEntryAtPosition(final double mouseX, final double mouseY) {
        final int rowMiddle = getRowWidth() / 2;
        final int listMiddle = getScreenX() + getScreenWidth() / 2;
        final int left = listMiddle - rowMiddle;
        final int right = listMiddle + rowMiddle;
        final int yPos = (int) Math.floor(mouseY - getScreenY() - headerHeight + getAmountScrolled() - getPadding());
        final int index = yPos / itemHeight;
        return mouseX >= left && mouseX <= right && index >= 0 && yPos >= 0 && index < getItemCount() ? getEntry(index) : null;
    }

    /**
     * Update the Size and Positioning of this Widget
     *
     * @param width  The new width to interpret
     * @param height The new height to interpret
     * @param yPos   The new starting Y position to interpret
     */
    public void updateSizeAndPosition(final int width, final int height, final int yPos) {
        setScreenWidth(width);
        setScreenHeight(height);
        setScreenX(0);
        setScreenY(yPos);
        bindAmountScrolled();
    }

    /**
     * Retrieve the maximum content position for this list
     *
     * @return the maximum content position
     */
    protected int getMaxPosition() {
        return getItemCount() * itemHeight + headerHeight;
    }

    /**
     * Retrieve whether the header element has been clicked
     *
     * @param posX The X position to interpret
     * @param posY The Y position to interpret
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    protected boolean clickedHeader(final int posX, final int posY) {
        return false;
    }

    /**
     * Render the header element to the screen, if able
     *
     * @param arg    The Matrix Stack, used for Rendering
     * @param client The current game instance
     * @param posX   The Event X Coordinate
     * @param posY   The Event Y Coordinate
     */
    protected void renderHeader(final GuiGraphics arg, final Minecraft client, final int posX, final int posY) {
        // N/A
    }

    /**
     * Render additional decorations to the screen
     *
     * @param arg    The Matrix Stack, used for Rendering
     * @param client The current game instance
     * @param posX   The Event X Coordinate
     * @param posY   The Event Y Coordinate
     */
    protected void renderDecorations(final GuiGraphics arg, final Minecraft client, final int posX, final int posY) {
        // N/A
    }

    @Override
    public void preRender() {
        super.preRender();
        hovered = isOverScreen() ? getEntryAtPosition(getMouseX(), getMouseY()) : null;
    }

    @Override
    public void postRender() {
        super.postRender();
        renderDecorations(getCurrentMatrix(), getGameInstance(), getMouseX(), getMouseY());
    }

    @Override
    public void renderExtra() {
        super.renderExtra();

        if (renderHeader) {
            final int posX = getRowLeft();
            final int posY = getScreenY() + getPadding() - (int) getAmountScrolled();
            renderHeader(getCurrentMatrix(), getGameInstance(), posX, posY);
        }
        renderListItems(getCurrentMatrix(), getGameInstance(), getMouseX(), getMouseY(), getPartialTicks());
    }

    /**
     * Center the Scrollbar to the specified entry on the list
     *
     * @param entry The entry to interpret
     */
    protected void centerScrollOn(final E entry) {
        setScroll(children().indexOf(entry) * itemHeight + itemHeight / 2f - getScreenHeight() / 2f);
    }

    /**
     * Ensure that the specified entry is visible on the list
     * <p>Note: This works differently than {@link EntryListPane#centerScrollOn(Entry)}
     *
     * @param entry The entry to interpret
     */
    protected void ensureVisible(final E entry) {
        final int rowTop = getRowTop(children().indexOf(entry));
        final int j = rowTop - getScreenY() - getPadding() - itemHeight;
        if (j < 0) {
            scrollBy(j);
        }

        int k = getBottom() - rowTop - itemHeight - itemHeight;
        if (k < 0) {
            scrollBy(-k);
        }
    }

    @Override
    public int getContentHeight() {
        return getMaxPosition();
    }

    @Override
    public int getMaxScroll() {
        return Math.max(0, getContentHeight() - (getScreenHeight() - getPadding()));
    }

    @Override
    public int getScrollBarX() {
        return getDefaultScrollbarPosition();
    }

    /**
     * Retrieve the default scrollbar position
     *
     * @return The default scrollbar position
     */
    protected int getDefaultScrollbarPosition() {
        return getRealRowRight() + getListOutlinePadding();
    }

    /**
     * Retrieve the outline list padding
     *
     * @return The outline list padding
     */
    private int getListOutlinePadding() {
        return 10;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!isValidMouseClick(mouseButton)) {
            return false;
        } else {
            checkScrollbarClick(mouseX, mouseY, mouseButton);
            if (!isOverScreen()) {
                return false;
            } else {
                final E entry = getEntryAtPosition(mouseX, mouseY);
                if (entry != null) {
                    if (entry.mouseClicked(mouseX, mouseY, mouseButton)) {
                        final E focused = getFocused();
                        if (focused != entry && focused instanceof ContainerEventHandler containerEventHandler) {
                            containerEventHandler.setFocused(null);
                        }
                        setFocused(entry);
                        return true;
                    }
                } else if (clickedHeader(
                        (int) (mouseX - (double) (getScreenX() + getScreenWidth() / 2 - getRowWidth() / 2)),
                        (int) (mouseY - (double) getScreenY()) + (int) getAmountScrolled() - getPadding()
                )) {
                    return true;
                }
            }

            return isScrolling();
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        setScrolling(false);
        return getFocused() != null && getFocused().mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener arg) {
        super.setFocused(arg);
        final int index = children().indexOf(arg);
        if (index >= 0) {
            final E entry = getEntry(index);
            setSelected(entry);
            if (getGameInstance().getLastInputType().isKeyboard()) {
                ensureVisible(entry);
            }
        }
    }

    @Override
    public int getHeightPerScroll() {
        return itemHeight / 2;
    }

    /**
     * Convert the {@link ScreenDirection} to its primitive index
     *
     * @param direction The direction to interpret
     * @return the processed index to interpret
     */
    private int directionToInt(final ScreenDirection direction) {
        return switch (direction) {
            case RIGHT, LEFT -> 0;
            case UP -> -1;
            case DOWN -> 1;
        };
    }

    /**
     * Retrieve the next entry in the specified direction
     *
     * @param direction The direction to travel
     * @return the next entry, or null
     */
    @Nullable
    protected E nextEntry(final int direction) {
        return nextEntry(direction, entry -> true);
    }

    /**
     * Retrieve the next entry in the specified direction
     *
     * @param direction The direction to travel
     * @return the next entry, or null
     */
    @Nullable
    protected E nextEntry(final ScreenDirection direction) {
        return nextEntry(directionToInt(direction));
    }

    /**
     * Retrieve the next entry in the specified direction
     *
     * @param direction The direction to travel
     * @param predicate The condition to interpret the entry with
     * @return the next entry, or null
     */
    @Nullable
    protected E nextEntry(final int direction, final Predicate<E> predicate) {
        return nextEntry(direction, predicate, getSelected());
    }

    /**
     * Retrieve the next entry in the specified direction
     *
     * @param direction The direction to travel
     * @param predicate The condition to interpret the entry with
     * @return the next entry, or null
     */
    @Nullable
    protected E nextEntry(final ScreenDirection direction, final Predicate<E> predicate) {
        return nextEntry(directionToInt(direction), predicate);
    }

    /**
     * Retrieve the next entry in the specified direction
     *
     * @param direction The direction to travel
     * @param predicate The condition to interpret the entry with
     * @param entry     The entry to interpret, if any
     * @return the next entry, or null
     */
    @Nullable
    protected E nextEntry(final int direction, final Predicate<E> predicate, final @Nullable E entry) {
        if (!children().isEmpty() && direction != 0) {
            int index;
            if (entry == null) {
                index = direction > 0 ? 0 : children().size() - 1;
            } else {
                index = children().indexOf(entry) + direction;
            }

            for (int i = index; i >= 0 && i < getItemCount(); i += direction) {
                final E next = children().get(i);
                if (predicate.test(next)) {
                    return next;
                }
            }
        }
        return null;
    }

    /**
     * Retrieve the next entry in the specified direction
     *
     * @param direction The direction to travel
     * @param predicate The condition to interpret the entry with
     * @param entry     The entry to interpret, if any
     * @return the next entry, or null
     */
    @Nullable
    protected E nextEntry(final ScreenDirection direction, final Predicate<E> predicate, final @Nullable E entry) {
        return nextEntry(directionToInt(direction), predicate, entry);
    }

    /**
     * Move the current selection in the specified direction
     *
     * @param direction The direction to travel
     */
    protected void moveSelection(final int direction) {
        final E entry = direction != 0 ? nextEntry(direction) : getSelected();
        if (entry != null) {
            setFocused(entry);
            ensureVisible(entry);
        }
    }

    /**
     * Move the current selection in the specified direction
     *
     * @param direction The direction to travel
     */
    protected void moveSelection(final ScreenDirection direction) {
        moveSelection(directionToInt(direction));
    }

    /**
     * Render the List Items for this widget
     *
     * @param arg          The Matrix Stack, used for Rendering
     * @param client       The current game instance
     * @param mouseX       The Event Mouse X Coordinate
     * @param mouseY       The Event Mouse Y Coordinate
     * @param partialTicks The Rendering Tick Rate
     */
    protected void renderListItems(final GuiGraphics arg, final Minecraft client, final int mouseX, final int mouseY, final float partialTicks) {
        final int rowLeft = getRowLeft();
        final int rowWidth = getRowWidth();
        final int rowHeight = itemHeight - getPadding();

        for (int index = 0; index < getItemCount(); index++) {
            final int rowTop = getRowTop(index);
            final int rowBottom = getRowBottom(index);
            if (rowBottom >= getScreenY() && rowTop <= getBottom()) {
                renderItem(arg, client, mouseX, mouseY, partialTicks, index, rowLeft, rowTop, rowWidth, rowHeight);
            }
        }
    }

    /**
     * Render the specified item for this widget
     *
     * @param arg          The Matrix Stack, used for Rendering
     * @param client       The current game instance
     * @param mouseX       The Event Mouse X Coordinate
     * @param mouseY       The Event Mouse Y Coordinate
     * @param partialTicks The Rendering Tick Rate
     * @param index        The index representing the entry to render
     * @param xPos         The starting X position for the entry
     * @param yPos         The starting Y position for the entry
     * @param entryWidth   The width of the entry
     * @param entryHeight  The height of the entry
     */
    protected void renderItem(final GuiGraphics arg, final Minecraft client, final int mouseX, final int mouseY, final float partialTicks, final int index, final int xPos, final int yPos, final int entryWidth, final int entryHeight) {
        final E entry = getEntry(index);
        entry.renderBack(arg, client, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(getHovered(), entry), partialTicks);
        if (isSelectedItem(index)) {
            final int outerColor = isFocused() ? -1 : -8355712;
            renderSelection(client, yPos, entryWidth, entryHeight, outerColor, -16777216);
        }
        entry.render(arg, client, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(getHovered(), entry), partialTicks);
    }

    /**
     * Render a selection box around the specified position
     *
     * @param client     The current game instance
     * @param yPos       The starting Y position for the entry
     * @param width      The width of the entry
     * @param height     The height of the entry
     * @param outerColor The outer (background) color
     * @param innerColor The inner (foreground) color
     */
    protected void renderSelection(final Minecraft client, final int yPos, final int width, final int height, final int outerColor, final int innerColor) {
        final int left = getScreenX() + (getScreenWidth() - width) / 2;
        final int right = getScreenX() + (getScreenWidth() + width) / 2;
        RenderUtils.drawGradient(getCurrentMatrix(), left, right, yPos - 2, yPos + height + 2, 0.0D, outerColor, outerColor);
        RenderUtils.drawGradient(getCurrentMatrix(), left + 1, right - 1, yPos - 1, yPos + height + 1, 0.0D, innerColor, innerColor);
    }

    /**
     * Retrieve the left-most position for entry list rows
     *
     * @return The left-most row position
     */
    public int getRowLeft() {
        return getScreenX() + getScreenWidth() / 2 - getRowWidth() / 2 + 2;
    }

    /**
     * Retrieve the "real" left-most position for entry list rows
     *
     * @return The "real" left-most row position
     */
    private int getRealRowLeft() {
        return getScreenX() + getScreenWidth() / 2 - getRowWidth() / 2;
    }

    /**
     * Retrieve the right-most position for entry list rows
     *
     * @return The right-most row position
     */
    public int getRowRight() {
        return getRowLeft() + getRowWidth();
    }

    /**
     * Retrieve the "real" right-most position for entry list rows
     *
     * @return The "real" right-most row position
     */
    private int getRealRowRight() {
        return getRealRowLeft() + getRowWidth();
    }

    /**
     * Retrieve the top-most position for entry list rows
     *
     * @param index The index to interpret
     * @return The top-most position for entry list rows
     */
    protected int getRowTop(final int index) {
        return getScreenY() + getPadding() - (int) getAmountScrolled() + index * itemHeight + headerHeight;
    }

    /**
     * Retrieve the bottom-most position for entry list rows
     *
     * @param index The index to interpret
     * @return The bottom-most position for entry list rows
     */
    protected int getRowBottom(final int index) {
        return getRowTop(index) + itemHeight;
    }

    @Nonnull
    @Override
    public NarrationPriority narrationPriority() {
        if (isFocused()) {
            return NarrationPriority.FOCUSED;
        } else {
            return getHovered() != null ? NarrationPriority.HOVERED : NarrationPriority.NONE;
        }
    }

    /**
     * Remove the specified entry from the list
     *
     * @param index The index to interpret
     * @return The removed entry, or null if not found
     */
    @Nullable
    protected E remove(final int index) {
        final E entry = getEntry(index);
        return removeEntry(entry) ? entry : null;
    }

    /**
     * Remove the specified entry from the list
     *
     * @param entry The entry to interpret
     * @return {@link Boolean#TRUE} if removed successfully
     */
    protected boolean removeEntry(final E entry) {
        final boolean hasRemoved = children.remove(entry);
        if (hasRemoved && entry == getSelected()) {
            setSelected(null);
        }
        return hasRemoved;
    }

    /**
     * Retrieve the hovered entry in the list
     *
     * @return the current hovered entry, or null
     */
    @Nullable
    protected E getHovered() {
        return hovered;
    }

    /**
     * Bind the specified Entry to the current widget instance
     *
     * @param entry The entry to interpret
     */
    void bindEntryToSelf(final EntryListPane.Entry<E> entry) {
        entry.list = this;
    }

    /**
     * Narrate the List Element Position
     *
     * @param output The Narration Output
     * @param entry  The entry to interpret
     */
    protected void narrateListElementPosition(final NarrationElementOutput output, final E entry) {
        final List<E> list = children();
        if (list.size() > 1) {
            int i = list.indexOf(entry);
            if (i != -1) {
                output.add(NarratedElementType.POSITION, Component.translatable("narrator.position.list", i + 1, list.size()));
            }
        }
    }

    /**
     * Representation of an Entry for an {@link EntryListPane}
     *
     * @param <E> The entry type for the object
     * @author CDAGaming
     */
    protected abstract static class Entry<E extends EntryListPane.Entry<E>> implements GuiEventListener, NarrationSupplier {
        /**
         * The entry list reference
         */
        @Deprecated
        EntryListPane<E> list;

        @Override
        public void setFocused(boolean focused) {
            // N/A
        }

        public boolean isFocused() {
            return list.getFocused() == this;
        }

        /**
         * Retrieve the narration for this entry
         *
         * @return the narration for this entry
         */
        public abstract Component getNarration();

        @Override
        public void updateNarration(NarrationElementOutput output) {
            output.add(NarratedElementType.TITLE, getNarration());
        }

        /**
         * Render the entry content to the screen
         *
         * @param arg          The Matrix Stack, used for Rendering
         * @param client       The current game index
         * @param index        The index to interpret
         * @param yPos         The starting Y position for the entry
         * @param xPos         The starting X position for the entry
         * @param entryWidth   The width of the entry
         * @param entryHeight  The height of the entry
         * @param mouseX       The Event Mouse X Coordinate
         * @param mouseY       The Event Mouse Y Coordinate
         * @param hovered      Whether the entry is being hovered over
         * @param partialTicks The Rendering Tick Rate
         */
        public abstract void render(final GuiGraphics arg, final Minecraft client,
                                    final int index,
                                    final int yPos, final int xPos,
                                    final int entryWidth, final int entryHeight,
                                    final int mouseX, final int mouseY,
                                    final boolean hovered,
                                    final float partialTicks);

        /**
         * Render the entry background content to the screen
         *
         * @param arg          The Matrix Stack, used for Rendering
         * @param client       The current game index
         * @param index        The index to interpret
         * @param yPos         The starting Y position for the entry
         * @param xPos         The starting X position for the entry
         * @param entryWidth   The width of the entry
         * @param entryHeight  The height of the entry
         * @param mouseX       The Event Mouse X Coordinate
         * @param mouseY       The Event Mouse Y Coordinate
         * @param hovered      Whether the entry is being hovered over
         * @param partialTicks The Rendering Tick Rate
         */
        public void renderBack(final GuiGraphics arg, final Minecraft client,
                               final int index,
                               final int yPos, final int xPos,
                               final int entryWidth, final int entryHeight,
                               final int mouseX, final int mouseY,
                               final boolean hovered,
                               final float partialTicks) {
            // N/A
        }

        /**
         * Determines if the Mouse is over an element, following the defined Arguments
         *
         * @param mouseX The Mouse's Current X Position
         * @param mouseY The Mouse's Current Y Position
         * @return {@link Boolean#TRUE} if the Mouse Position is within the bounds of the object, and thus is over it
         */
        @Override
        public boolean isMouseOver(final double mouseX, final double mouseY) {
            return Objects.equals(list.getEntryAtPosition(mouseX, mouseY), this);
        }

        /**
         * Event to trigger upon the mouse being clicked
         *
         * @param mouseX The Event Mouse X Coordinate
         * @param mouseY The Event Mouse Y Coordinate
         * @param button The Event Mouse Button Clicked
         * @return The Event Result
         */
        @Override
        public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
            return true;
        }
    }

    /**
     * Simple List Implementation for tracking entry data
     *
     * @author CDAGaming
     */
    class TrackedList extends AbstractList<E> {
        /**
         * The localized list storage
         */
        private final List<E> delegate = StringUtils.newArrayList();

        @Override
        public E get(final int index) {
            return this.delegate.get(index);
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public E set(final int index, final E entry) {
            final E prevEntry = this.delegate.set(index, entry);
            EntryListPane.this.bindEntryToSelf(entry);
            return prevEntry;
        }

        @Override
        public void add(final int index, final E entry) {
            this.delegate.add(index, entry);
            EntryListPane.this.bindEntryToSelf(entry);
        }

        @Override
        public E remove(final int index) {
            return this.delegate.remove(index);
        }
    }
}
