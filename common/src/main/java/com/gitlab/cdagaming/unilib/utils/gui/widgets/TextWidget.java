/*
 * MIT License
 *
 * Copyright (c) 2018 - 2024 CDAGaming (cstack2011@yahoo.com)
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

package com.gitlab.cdagaming.unilib.utils.gui.widgets;

import com.gitlab.cdagaming.unilib.utils.gui.RenderUtils;
import com.gitlab.cdagaming.unilib.utils.gui.controls.ExtendedTextControl;
import com.gitlab.cdagaming.unilib.utils.gui.integrations.ExtendedScreen;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.client.gui.FontRenderer;

/**
 * Implementation for a Row-Style {@link ExtendedTextControl} Widget
 *
 * @author CDAGaming
 */
@SuppressWarnings("DuplicatedCode")
public class TextWidget extends ExtendedTextControl {
    /**
     * The message to display alongside the control
     */
    private String title;
    /**
     * The left-most X position for the additional message
     */
    private int titleLeft;
    /**
     * The right-most X position for the additional message
     */
    private int titleRight;
    /**
     * Whether positional data has been set up
     */
    private boolean setDimensions;
    /**
     * Event to Deploy when the title is Hovered Over, if any
     */
    private Runnable titleHoverEvent = null;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param componentId     The ID for the control to Identify as
     * @param fontRendererObj The Font Renderer Instance
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param title           The text to be rendered with this widget
     * @param titleHoverEvent The Hover Event to Occur when the title is hovered over
     */
    public TextWidget(int componentId, FontRenderer fontRendererObj, int y, int widthIn, int heightIn, String title, Runnable titleHoverEvent) {
        super(componentId, fontRendererObj, 0, y, widthIn, heightIn);
        this.setDimensions = false;
        setTitle(title);
        setOnTitleHover(titleHoverEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param componentId     The ID for the control to Identify as
     * @param fontRendererObj The Font Renderer Instance
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param title           The text to be rendered with this widget
     */
    public TextWidget(int componentId, FontRenderer fontRendererObj, int y, int widthIn, int heightIn, String title) {
        this(componentId, fontRendererObj, y, widthIn, heightIn, title, null);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param fontRendererObj The Font Renderer Instance
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param title           The text to be rendered with this widget
     * @param titleHoverEvent The Hover Event to Occur when the title is hovered over
     */
    public TextWidget(FontRenderer fontRendererObj, int y, int widthIn, int heightIn, String title, Runnable titleHoverEvent) {
        super(fontRendererObj, 0, y, widthIn, heightIn);
        this.setDimensions = false;
        setTitle(title);
        setOnTitleHover(titleHoverEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param fontRendererObj The Font Renderer Instance
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param title           The text to be rendered with this widget
     */
    public TextWidget(FontRenderer fontRendererObj, int y, int widthIn, int heightIn, String title) {
        this(fontRendererObj, y, widthIn, heightIn, title, null);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param fontRendererObj The Font Renderer Instance
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param keyEvent        The event to run when characters are typed in this control
     * @param title           The text to be rendered with this widget
     * @param titleHoverEvent The Hover Event to Occur when the title is hovered over
     */
    public TextWidget(FontRenderer fontRendererObj, int y, int widthIn, int heightIn, Runnable keyEvent, String title, Runnable titleHoverEvent) {
        super(fontRendererObj, 0, y, widthIn, heightIn, keyEvent);
        this.setDimensions = false;
        setTitle(title);
        setOnTitleHover(titleHoverEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param fontRendererObj The Font Renderer Instance
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param keyEvent        The event to run when characters are typed in this control
     * @param title           The text to be rendered with this widget
     */
    public TextWidget(FontRenderer fontRendererObj, int y, int widthIn, int heightIn, Runnable keyEvent, String title) {
        this(fontRendererObj, y, widthIn, heightIn, keyEvent, title, null);
    }

    /**
     * Whether a title exists for this control
     *
     * @return {@link Boolean#TRUE} if condition is satisfied
     */
    public boolean hasTitle() {
        return !StringUtils.isNullOrEmpty(getTitle());
    }

    /**
     * Retrieve the text to be displayed alongside this control
     *
     * @return the current attached message
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the text to be displayed alongside this control
     *
     * @param title The new attached message
     * @return the modified instance
     */
    public TextWidget setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the Event to occur upon Title Mouse Over
     *
     * @param event The event to occur
     */
    public void setOnTitleHover(final Runnable event) {
        titleHoverEvent = event;
    }

    /**
     * Triggers the titleHoverEvent event to occur
     */
    public void onTitleHover() {
        if (titleHoverEvent != null) {
            titleHoverEvent.run();
        }
    }

    @Override
    public void preDraw(ExtendedScreen screen) {
        // Ensure correct positioning
        if (!setDimensions) {
            final int middle = (screen.getScreenWidth() / 2);
            setControlPosX(middle + 3); // Left; Textbox
            titleLeft = middle - 180; // Left; Title Text (Offset: +3)
            titleRight = middle - 6; // Left; Textbox (Offset: -6)
            setDimensions = true;
        }
        super.preDraw(screen);
    }

    @Override
    public void draw(ExtendedScreen screen) {
        if (hasTitle() && setDimensions) {
            final String mainTitle = getTitle();

            screen.renderScrollingString(
                    mainTitle,
                    titleLeft + (screen.getStringWidth(mainTitle) / 2),
                    titleLeft, getTop(),
                    titleRight, getBottom(),
                    0xFFFFFF
            );
        }
        super.draw(screen);
    }

    @Override
    public void postDraw(ExtendedScreen screen) {
        if (hasTitle() && setDimensions) {
            if (screen.isOverScreen() && RenderUtils.isMouseOver(
                    screen.getMouseX(), screen.getMouseY(),
                    titleLeft, getTop(),
                    titleRight - titleLeft, getControlHeight()
            )) {
                onTitleHover();
            }
        }
        super.postDraw(screen);
    }
}
