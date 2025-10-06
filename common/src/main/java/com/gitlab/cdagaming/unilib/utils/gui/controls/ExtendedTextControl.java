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

import com.gitlab.cdagaming.unilib.utils.gui.integrations.ExtendedScreen;
import com.gitlab.cdagaming.unilib.utils.gui.integrations.GuiTextField;
import com.gitlab.cdagaming.unilib.utils.gui.widgets.DynamicWidget;
import io.github.cdagaming.unicore.impl.Pair;
import io.github.cdagaming.unicore.utils.StringUtils;
import net.minecraft.src.FontRenderer;

/**
 * Extended Gui Widget for a Text Field
 *
 * @author CDAGaming
 */
public class ExtendedTextControl extends GuiTextField implements DynamicWidget {
    /**
     * The default character limit for all controls of this type
     */
    private static final int DEFAULT_TEXT_LIMIT = 2048;
    /**
     * The event to occur when a key event occurs
     */
    private Runnable onKeyEvent;

    /**
     * The visibility for this control
     */
    private boolean visible = true;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param componentId     The ID for the control to Identify as
     * @param fontRendererObj The Font Renderer Instance
     * @param x               The Starting X Position for this Control
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     */
    public ExtendedTextControl(final int componentId, final FontRenderer fontRendererObj, final int x, final int y, final int widthIn, final int heightIn) {
        super(fontRendererObj, x, y, widthIn, heightIn, "");
        setControlMaxLength(DEFAULT_TEXT_LIMIT);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param fontRendererObj The Font Renderer Instance
     * @param x               The Starting X Position for this Control
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     */
    public ExtendedTextControl(final FontRenderer fontRendererObj, final int x, final int y, final int widthIn, final int heightIn) {
        this(ExtendedScreen.getNextIndex(), fontRendererObj, x, y, widthIn, heightIn);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param fontRendererObj The Font Renderer Instance
     * @param x               The Starting X Position for this Control
     * @param y               The Starting Y Position for this Control
     * @param widthIn         The Width for this Control
     * @param heightIn        The Height for this Control
     * @param keyEvent        The event to run when characters are typed in this control
     */
    public ExtendedTextControl(final FontRenderer fontRendererObj, final int x, final int y, final int widthIn, final int heightIn, final Runnable keyEvent) {
        this(fontRendererObj, x, y, widthIn, heightIn);
        setOnKeyTyped(keyEvent);
    }

    @Override
    public int getControlWidth() {
        return StringUtils.getValidInteger(StringUtils.getField(
                GuiTextField.class, this,
                "width", "field_22077_f", "g"
        )).getSecond();
    }

    @Override
    public void setControlWidth(final int width) {
        StringUtils.updateField(
                GuiTextField.class, this,
                width,
                "width", "field_22077_f", "g"
        );
    }

    @Override
    public int getControlHeight() {
        return StringUtils.getValidInteger(StringUtils.getField(
                GuiTextField.class, this,
                "height", "field_22076_g", "h"
        )).getSecond();
    }

    @Override
    public void setControlHeight(final int height) {
        StringUtils.updateField(
                GuiTextField.class, this,
                height,
                "height", "field_22076_g", "h"
        );
    }

    @Override
    public void preDraw(ExtendedScreen screen) {
        // N/A
    }

    @Override
    public void draw(ExtendedScreen screen) {
        // N/A
    }

    @Override
    public void postDraw(ExtendedScreen screen) {
        // N/A
    }

    @Override
    public int getControlPosX() {
        return StringUtils.getValidInteger(StringUtils.getField(
                GuiTextField.class, this,
                "xPos", "field_22079_d", "d"
        )).getSecond();
    }

    @Override
    public void setControlPosX(final int posX) {
        StringUtils.updateField(
                GuiTextField.class, this,
                posX,
                "xPos", "field_22079_d", "d"
        );
    }

    @Override
    public int getControlPosY() {
        return StringUtils.getValidInteger(StringUtils.getField(
                GuiTextField.class, this,
                "yPos", "field_22078_e", "f"
        )).getSecond();
    }

    @Override
    public void setControlPosY(final int posY) {
        StringUtils.updateField(
                GuiTextField.class, this,
                posY,
                "yPos", "field_22078_e", "f"
        );
    }

    /**
     * Gets the control's current text contents
     *
     * @return The control's current text contents
     */
    public String getControlMessage() {
        return this.getText();
    }

    /**
     * Sets the control's display message to the specified value
     *
     * @param newMessage The new display message for this control
     */
    public void setControlMessage(final String newMessage) {
        this.setText("");
        this.writeText(StringUtils.getOrDefault(newMessage));
    }

    /**
     * Gets whether the control is currently active or enabled
     *
     * @return Whether the control is currently active or enabled
     */
    public boolean isControlEnabled() {
        return this.isEnabled;
    }

    /**
     * Sets the control's current enabled state
     *
     * @param isEnabled The new enable state for this control
     */
    public void setControlEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Gets whether the control is currently visible
     *
     * @return Whether the control is currently visible
     */
    public boolean isControlVisible() {
        return this.visible;
    }

    /**
     * Sets the control's current visibility state
     *
     * @param isVisible The new visibility state for this control
     */
    public void setControlVisible(final boolean isVisible) {
        this.visible = isVisible;
    }

    /**
     * Gets the control's maximum text length
     *
     * @return The control's maximum text contents
     */
    public int getControlMaxLength() {
        final Object reflectedInfo = StringUtils.getField(GuiTextField.class, this, "maxStringLength", "field_22074_i", "j");
        if (reflectedInfo != null) {
            final Pair<Boolean, Integer> integerData = StringUtils.getValidInteger(reflectedInfo);
            return integerData.getFirst() ? integerData.getSecond() : 0;
        }
        return 0;
    }

    /**
     * Sets the control's message maximum length to the specified value
     *
     * @param newLength The new maximum length for this control's message
     */
    public void setControlMaxLength(final int newLength) {
        this.setMaxStringLength(newLength);
    }

    /**
     * Gets whether the control is currently being focused upon
     *
     * @return The control's focus status
     */
    public boolean isControlFocused() {
        return this.isFocused;
    }

    /**
     * Sets whether the control should be focused upon
     *
     * @param focused the new focus state for the control
     */
    public void setControlFocused(final boolean focused) {
        this.setFocused(focused);
    }

    /**
     * Sets the Event to occur upon typing keys
     *
     * @param event The event to occur
     */
    public void setOnKeyTyped(final Runnable event) {
        onKeyEvent = event;
    }

    /**
     * Triggers the onKey event to occur
     */
    public void onKeyTyped() {
        if (onKeyEvent != null) {
            onKeyEvent.run();
        }
    }

    /**
     * The event to occur when a character is typed within this control
     *
     * @param typedChar The typed character, if any
     * @param keyCode   The keycode, if any
     */
    @Override
    public void textboxKeyTyped(char typedChar, int keyCode) {
        super.textboxKeyTyped(typedChar, keyCode);
        onKeyTyped();
    }
}
