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

package com.gitlab.cdagaming.unilib.utils.gui.integrations;

import com.gitlab.cdagaming.unilib.core.impl.screen.ScreenConstants;
import com.gitlab.cdagaming.unilib.utils.ResourceUtils;
import com.gitlab.cdagaming.unilib.utils.gui.RenderUtils;
import com.gitlab.cdagaming.unilib.utils.gui.widgets.DynamicWidget;
import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import io.github.cdagaming.unicore.utils.MathUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Implementation for a Scrollable Screen Pane
 *
 * @author CDAGaming
 */
public class ScrollPane extends ExtendedScreen {
    private static final ResourceLocation SCROLLER_SPRITE = ResourceUtils.getResource("widget/scroller");
    private static final ResourceLocation SCROLLER_BACKGROUND_SPRITE = ResourceUtils.getResource("widget/scroller_background");
    private static final int DEFAULT_PADDING = 4;
    private static final int DEFAULT_HEADER_HEIGHT = 2;
    private static final int DEFAULT_FOOTER_HEIGHT = 2;
    private static final int DEFAULT_BAR_WIDTH = 6;
    private static final int DEFAULT_HEIGHT_PER_SCROLL = 8;
    private final ScreenConstants.ColorData DEFAULT_HEADER_BACKGROUND;
    private final ScreenConstants.ColorData DEFAULT_FOOTER_BACKGROUND;
    private boolean clickedScrollbar;
    private int padding;
    private float amountScrolled = 0.0F;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param startX  The starting X position of the widget
     * @param startY  The starting Y position of the widget
     * @param width   The width of the widget
     * @param height  The height of the widget
     * @param padding The padding for the widget
     */
    public ScrollPane(final int startX, final int startY, final int width, final int height, final int padding) {
        super();
        setScreenX(startX);
        setScreenY(startY);
        setScreenWidth(width);
        setScreenHeight(height);
        setPadding(padding);

        // Setup Default Depth Decoration Info
        DEFAULT_HEADER_BACKGROUND = new ScreenConstants.ColorData(
                (hasWorld() ? ExtendedScreen.INWORLD_HEADER_SEPARATOR : ExtendedScreen.HEADER_SEPARATOR).toString(),
                0.0D, 32.0D, 2.0D
        );
        DEFAULT_FOOTER_BACKGROUND = new ScreenConstants.ColorData(
                (hasWorld() ? ExtendedScreen.INWORLD_FOOTER_SEPARATOR : ExtendedScreen.FOOTER_SEPARATOR).toString(),
                0.0D, 32.0D, 2.0D
        );
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param startX The starting X position of the widget
     * @param startY The starting Y position of the widget
     * @param width  The width of the widget
     * @param height The height of the widget
     */
    public ScrollPane(final int startX, final int startY, final int width, final int height) {
        this(startX, startY, width, height, DEFAULT_PADDING);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param width   The width of the widget
     * @param height  The height of the widget
     * @param padding The padding for the widget
     */
    public ScrollPane(final int width, final int height, final int padding) {
        this(0, 0, width, height, padding);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param width  The width of the widget
     * @param height The height of the widget
     */
    public ScrollPane(final int width, final int height) {
        this(width, height, DEFAULT_PADDING);
    }

    @Override
    public void resetMouseScroll() {
        super.resetMouseScroll();
        setScroll(getMouseScroll());
    }

    @Override
    public void refreshContentHeight() {
        super.refreshContentHeight();

        setContentHeight((int) (getContentHeight() + getAmountScrolled()));
    }

    @Override
    public double getOffset() {
        return needsScrollbar() ? getAmountScrolled() : super.getOffset();
    }

    @Override
    public float getTintFactor() {
        return super.getTintFactor();
    }

    @Override
    public void drawBackground(final double left, final double right,
                               final double top, final double bottom,
                               final double offset, float tintFactor,
                               final ScreenConstants.ColorData data) {
        drawBackground(
                left, right, top, bottom,
                offset, tintFactor,
                right, bottom,
                data
        );
    }

    @Override
    public void renderBackground(@Nonnull GuiGraphics arg, int i, int j, float f) {
        super.renderMenuBackground(arg);
    }

    /**
     * Retrieve the top-most coordinate for the header decoration
     *
     * @return the top-most coordinate for the header decoration
     */
    protected int getHeaderTop() {
        return getTop() - getHeaderHeight();
    }

    /**
     * Retrieve the height of the header decoration
     *
     * @return the header height
     */
    protected int getHeaderHeight() {
        return DEFAULT_HEADER_HEIGHT;
    }

    /**
     * Retrieve the bottom-most coordinate for the header decoration
     *
     * @return the bottom-most coordinate for the header decoration
     */
    protected int getHeaderBottom() {
        return getHeaderTop() + getHeaderHeight();
    }

    /**
     * Retrieve the top-most coordinate for the footer decoration
     *
     * @return the top-most coordinate for the footer decoration
     */
    protected int getFooterTop() {
        return getBottom();
    }

    /**
     * Retrieve the height of the footer decoration
     *
     * @return the footer height
     */
    protected int getFooterHeight() {
        return DEFAULT_FOOTER_HEIGHT;
    }

    /**
     * Retrieve the bottom-most coordinate for the footer decoration
     *
     * @return the bottom-most coordinate for the footer decoration
     */
    protected int getFooterBottom() {
        return getFooterTop() + getFooterHeight();
    }

    /**
     * Retrieve the rendering info for the Header Background
     *
     * @return the processed {@link ScreenConstants.ColorData} info
     */
    protected ScreenConstants.ColorData getHeaderBackground() {
        return DEFAULT_HEADER_BACKGROUND;
    }

    /**
     * Retrieve the rendering info for the Footer Background
     *
     * @return the processed {@link ScreenConstants.ColorData} info
     */
    protected ScreenConstants.ColorData getFooterBackground() {
        return DEFAULT_FOOTER_BACKGROUND;
    }

    /**
     * Retrieve the rendering info for the Scrollbar Background
     *
     * @return the processed info
     */
    protected ResourceLocation getScrollerBackgroundSprite() {
        return SCROLLER_BACKGROUND_SPRITE;
    }

    /**
     * Retrieve the rendering info for the Scrollbar
     *
     * @return the processed info
     */
    protected ResourceLocation getScrollerSprite() {
        return SCROLLER_SPRITE;
    }

    /**
     * Render the List Separators (Depth Decorations)
     */
    protected void renderListSeparators() {
        drawBackground(
                getLeft(), getRight(), getHeaderTop(), getHeaderBottom(),
                0.0D, 1.0F,
                0.0D, 0.0D,
                getHeaderBackground()
        );
        drawBackground(
                getLeft(), getRight(), getFooterTop(), getFooterBottom(),
                0.0D, 1.0F,
                0.0D, 0.0D,
                getFooterBackground()
        );
    }

    /**
     * Render the Scrollbar Elements, if needed
     */
    protected void renderScrollbar() {
        if (needsScrollbar()) {
            final int scrollBarX = getScrollBarX();
            final int scrollBarWidth = getScrollBarWidth();
            final int bottom = getBottom();
            final int top = getTop();
            final int maxScroll = getMaxScroll();
            final int screenHeight = getScreenHeight();
            final int height = getBarHeight();
            final int barTop = Math.max((int) getAmountScrolled() * (screenHeight - height) / maxScroll + top, top);

            RenderUtils.renderSprite(getCurrentMatrix(), graphics -> {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getScrollerBackgroundSprite(), scrollBarX, top, scrollBarWidth, bottom - top);
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, getScrollerSprite(), scrollBarX, barTop, scrollBarWidth, height);
                if (isOverScrollbar(getMouseX(), getMouseY())) {
                    graphics.requestCursor(getCursorType());
                }
            });
        }
    }

    @Override
    public void postRender() {
        renderListSeparators();
        renderScrollbar();

        super.postRender();
    }

    protected CursorType getCursorType() {
        return isScrolling() ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND;
    }

    /**
     * Retrieve whether a valid mouse click was performed
     *
     * @param button The Mouse Button to interpret
     * @return {@link Boolean#TRUE} if condition was satisfied
     */
    protected boolean isValidMouseClick(final int button) {
        return button == 0;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
        if (isLoaded()) {
            checkScrollbarClick(mouseButtonEvent.x(), mouseButtonEvent.y(), mouseButtonEvent.button());

            return super.mouseClicked(mouseButtonEvent, doubleClick);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
        setScrolling(false);
        return isLoaded() && super.mouseReleased(mouseButtonEvent);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double deltaX, double deltaY) {
        if (isLoaded()) {
            if (isValidMouseClick(mouseButtonEvent.button()) && needsScrollbar() && isScrolling()) {
                if (mouseButtonEvent.y() < getTop()) {
                    setScroll(0.0F);
                } else if (mouseButtonEvent.y() > getBottom()) {
                    setScroll(getMaxScroll());
                } else {
                    final int deltaYInt = (int) (deltaY > 0 ? deltaY + 0.5 : deltaY - 0.5);
                    final int height = getBarHeight();
                    final int scrollLimit = Math.max(1, getMaxScroll());
                    final int heightPerScroll = Math.max(1, scrollLimit / (getScreenHeight() - height));
                    scrollBy(deltaYInt * heightPerScroll);
                }
                return true;
            }
            return super.mouseDragged(mouseButtonEvent, deltaX, deltaY);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double wheelX, double wheelY) {
        scrollBy((float) (-wheelY * getHeightPerScroll()));
        return true;
    }

    /**
     * Retrieve the padding for the widget
     *
     * @return the padding for the widget
     */
    public int getPadding() {
        return padding;
    }

    /**
     * Sets the padding for the widget
     *
     * @param newPadding the new padding for the widget
     */
    public void setPadding(final int newPadding) {
        padding = newPadding;
    }

    /**
     * Retrieve the current starting X position of the scroll bar
     *
     * @return the starting X position of the scroll bar
     */
    public int getScrollBarX() {
        return getRight() - getScrollBarWidth();
    }

    /**
     * Retrieve the scroll bar width
     *
     * @return the scroll bar width
     */
    public int getScrollBarWidth() {
        return needsScrollbar() ? DEFAULT_BAR_WIDTH : 0;
    }

    /**
     * Retrieve the height to scroll by, per scroll
     *
     * @return the height per scroll
     */
    public int getHeightPerScroll() {
        return DEFAULT_HEIGHT_PER_SCROLL;
    }

    /**
     * Append the scroll by the specified amount
     *
     * @param amount The amount to append the scroll by
     */
    public void scrollBy(final float amount) {
        setScroll(getAmountScrolled() + amount);
    }

    /**
     * Set the scroll to the specified amount
     *
     * @param amount the new scroll amount
     */
    public void setScroll(final float amount) {
        final float prevScrollAmount = getAmountScrolled();
        setAmountScrolled(amount);
        bindAmountScrolled();

        if (getAmountScrolled() != prevScrollAmount) {
            final int scrollDiff = (int) (getAmountScrolled() - prevScrollAmount);
            for (DynamicWidget widget : getWidgets()) {
                widget.setControlPosY(widget.getControlPosY() - scrollDiff);
            }
        }
    }

    /**
     * Determine if the scrollbar has been clicked
     *
     * @param mouseX The Event Mouse X Coordinate
     * @param mouseY The Event Mouse Y Coordinate
     * @param button The Event Mouse Button Clicked
     */
    public void checkScrollbarClick(double mouseX, double mouseY, int button) {
        setScrolling(needsScrollbar() && isValidMouseClick(button) && isOverScrollbar(mouseX, mouseY));
    }

    /**
     * Determine if the mouse is over the scrollbar
     *
     * @param mouseX The Event Mouse X Coordinate
     * @param mouseY The Event Mouse Y Coordinate
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public boolean isOverScrollbar(double mouseX, double mouseY) {
        return MathUtils.isWithinValue(mouseX, getScrollBarX(), getScrollBarX() + getScrollBarWidth(), true, false) &&
                MathUtils.isWithinValue(mouseY, getTop(), getBottom(), true, false);
    }

    /**
     * Retrieve whether we are currently scrolling
     *
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public boolean isScrolling() {
        return clickedScrollbar;
    }

    /**
     * Sets whether we are currently scrolling
     *
     * @param scrolling the new "is scrolling" value
     */
    public void setScrolling(final boolean scrolling) {
        clickedScrollbar = scrolling;
    }

    /**
     * Clamp the scroll amount between 0 and {@link ScrollPane#getMaxScroll()}
     */
    public void bindAmountScrolled() {
        setAmountScrolled(MathUtils.clamp(getAmountScrolled(), 0, getMaxScroll()));
    }

    /**
     * Retrieve the current scroll amount
     *
     * @return the current scroll amount
     */
    public float getAmountScrolled() {
        return amountScrolled;
    }

    /**
     * Directly sets the current scroll amount
     * <p>It is recommended to use {@link ScrollPane#scrollBy(float)} or {@link ScrollPane#setScroll(float)} instead
     *
     * @param scrolled the new scroll amount
     */
    public void setAmountScrolled(final float scrolled) {
        this.amountScrolled = scrolled;
    }

    /**
     * Get the maximum scroll height
     *
     * @return the maximum scroll height
     */
    public int getMaxScroll() {
        return Math.max(0, getContentHeight() - (getBottom() - getPadding()));
    }

    /**
     * Retrieve the height of the scrollbar
     *
     * @return the total height of the scrollbar
     */
    public int getBarHeight() {
        if (!needsScrollbar()) return 0;
        final int barHeight = (getScreenHeight() * getScreenHeight()) / getContentHeight();
        return MathUtils.clamp(barHeight, 32, getScreenHeight() - (getPadding() * 2));
    }

    /**
     * Retrieve whether this widget needs a scrollbar
     *
     * @return {@link Boolean#TRUE} if a scrollbar is needed
     */
    public boolean needsScrollbar() {
        return getMaxScroll() > 0;
    }

    @Override
    public int getMaxWidth() {
        return getScreenWidth() - getPadding() - getScrollBarWidth();
    }
}
