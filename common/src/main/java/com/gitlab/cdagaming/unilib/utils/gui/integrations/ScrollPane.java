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
import com.gitlab.cdagaming.unilib.utils.gui.widgets.DynamicWidget;
import io.github.cdagaming.unicore.utils.MathUtils;
import io.github.cdagaming.unicore.utils.StringUtils;

import java.awt.*;

/**
 * Implementation for a Scrollable Screen Pane
 *
 * @author CDAGaming
 */
public class ScrollPane extends ExtendedScreen {
    private static final Color NONE = StringUtils.getColorFrom(0, 0, 0, 0);
    private static final int DEFAULT_PADDING = 4;
    private static final int DEFAULT_HEADER_HEIGHT = 4;
    private static final int DEFAULT_FOOTER_HEIGHT = 4;
    private static final int DEFAULT_BAR_WIDTH = 6;
    private static final int DEFAULT_HEIGHT_PER_SCROLL = 8;
    private final ScreenConstants.ColorData DEFAULT_HEADER_BACKGROUND;
    private final ScreenConstants.ColorData DEFAULT_FOOTER_BACKGROUND;
    private final ScreenConstants.ColorData DEFAULT_SCROLLBAR_BACKGROUND;
    private final ScreenConstants.ColorData DEFAULT_SCROLLBAR_BORDER;
    private final ScreenConstants.ColorData DEFAULT_SCROLLBAR_FOREGROUND;
    private boolean clickedScrollbar;
    private int padding;
    private float amountScrolled = 0.0F;
    // remove in 1.13+
    private int mousePrevX = 0;
    // remove in 1.13+
    private int mousePrevY = 0;

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
                Color.black, NONE,
                getScreenBackground().texLocation(),
                0.0D, -100.0D,
                false
        );
        DEFAULT_FOOTER_BACKGROUND = new ScreenConstants.ColorData(
                NONE, Color.black,
                getScreenBackground().texLocation(),
                0.0D, -100.0D,
                false
        );

        // Setup Default Scrollbar Info
        DEFAULT_SCROLLBAR_BACKGROUND = new ScreenConstants.ColorData(
                Color.black, Color.black,
                getScreenBackground().texLocation(),
                0.0D, 0.0D,
                false
        );
        DEFAULT_SCROLLBAR_BORDER = new ScreenConstants.ColorData(
                Color.gray, Color.gray,
                getScreenBackground().texLocation(),
                0.0D, 0.0D,
                false
        );
        DEFAULT_SCROLLBAR_FOREGROUND = new ScreenConstants.ColorData(
                Color.lightGray, Color.lightGray,
                getScreenBackground().texLocation(),
                0.0D, 0.0D,
                false
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
        return 0.5f;
    }

    /**
     * Retrieve the top-most coordinate for the header decoration
     *
     * @return the top-most coordinate for the header decoration
     */
    protected int getHeaderTop() {
        return getTop();
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
        return getBottom() - getFooterHeight();
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
     * @return the processed {@link ScreenConstants.ColorData} info
     */
    protected ScreenConstants.ColorData getScrollbarBackground() {
        return DEFAULT_SCROLLBAR_BACKGROUND;
    }

    /**
     * Retrieve the rendering info for the Scrollbar Border
     *
     * @return the processed {@link ScreenConstants.ColorData} info
     */
    protected ScreenConstants.ColorData getScrollbarBorder() {
        return DEFAULT_SCROLLBAR_BORDER;
    }

    /**
     * Retrieve the rendering info for the Scrollbar Foreground
     *
     * @return the processed {@link ScreenConstants.ColorData} info
     */
    protected ScreenConstants.ColorData getScrollbarForeground() {
        return DEFAULT_SCROLLBAR_FOREGROUND;
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
            final int scrollBarRight = scrollBarX + getScrollBarWidth();
            final int bottom = getBottom();
            final int top = getTop();
            final int maxScroll = getMaxScroll();
            final int screenHeight = getScreenHeight();
            final int height = getBarHeight();
            final int barTop = Math.max((int) getAmountScrolled() * (screenHeight - height) / maxScroll + top, top);
            final int barBottom = barTop + height;

            drawBackground(
                    scrollBarX, scrollBarRight, top, bottom,
                    0.0D, 1.0F,
                    0.0D, 0.0D,
                    getScrollbarBackground()
            );
            drawBackground(
                    scrollBarX, scrollBarRight, barTop, barBottom,
                    0.0D, 1.0F,
                    0.0D, 0.0D,
                    getScrollbarBorder()
            );
            drawBackground(
                    scrollBarX, scrollBarRight - 1, barTop, barBottom - 1,
                    0.0D, 1.0F,
                    0.0D, 0.0D,
                    getScrollbarForeground()
            );
        }
    }

    @Override
    public void postRender() {
        renderListSeparators();
        renderScrollbar();

        super.postRender();
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

    // remove in 1.13+
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isLoaded()) {
            checkScrollbarClick(mouseX, mouseY, mouseButton);
            mousePrevX = mouseX;
            mousePrevY = mouseY;

            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    // remove in 1.13+
    @Override
    public void mouseMovedOrButtonReleased(int mouseX, int mouseY, int mouseButton) {
        setScrolling(false);
        if (isLoaded()) {
            super.mouseMovedOrButtonReleased(mouseX, mouseY, mouseButton);
        }
    }

    // remove in 1.13+
    @Override
    protected void method_4259(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {
        if (isLoaded()) {
            mouseDragged(mouseX, mouseY, mouseButton, mouseX - mousePrevX, mouseY - mousePrevY);
            mousePrevX = mouseX;
            mousePrevY = mouseY;

            super.method_4259(mouseX, mouseY, mouseButton, timeSinceLastClick);
        }
    }

    public void mouseDragged(int mouseX, int mouseY, int button, int deltaX, int deltaY) {
        if (isValidMouseClick(button) && needsScrollbar() && isScrolling()) {
            if (mouseY < getTop()) {
                setScroll(0.0F);
            } else if (mouseY > getBottom()) {
                setScroll(getMaxScroll());
            } else {
                final int height = getBarHeight();
                final int scrollLimit = Math.max(1, getMaxScroll());
                final int heightPerScroll = Math.max(1, scrollLimit / (getScreenHeight() - height));
                scrollBy(deltaY * heightPerScroll);
            }
        }
    }

    @Override
    public void mouseScrolled(int mouseX, int mouseY, int wheelY) {
        scrollBy(-wheelY * getHeightPerScroll());
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
