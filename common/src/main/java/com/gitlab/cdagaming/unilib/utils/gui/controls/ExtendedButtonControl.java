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

import com.gitlab.cdagaming.unilib.ModUtils;
import com.gitlab.cdagaming.unilib.utils.gui.RenderUtils;
import com.gitlab.cdagaming.unilib.utils.gui.integrations.ExtendedScreen;
import com.gitlab.cdagaming.unilib.utils.gui.widgets.DynamicWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

/**
 * Extended Gui Widget for a Clickable Button
 *
 * @author CDAGaming
 */
public class ExtendedButtonControl extends Button implements DynamicWidget {
    /**
     * Optional Arguments used for functions within the Mod, if any
     */
    private String[] optionalArgs;
    /**
     * Event to Deploy when this Control is Clicked, if any
     */
    private Runnable onPushEvent = null;
    /**
     * Event to Deploy when this Control is Hovered Over, if any
     */
    private Runnable onHoverEvent = null;
    /**
     * Whether the mouse is currently within screen bounds
     */
    private boolean isOverScreen = false;

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param buttonId     The ID for the control to Identify as
     * @param x            The Starting X Position for this Control
     * @param y            The Starting Y Position for this Control
     * @param widthIn      The Width for this Control
     * @param heightIn     The Height for this Control
     * @param buttonText   The display text, to display within this control
     * @param optionalArgs The optional Arguments, if any, to associate with this control
     */
    public ExtendedButtonControl(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final String... optionalArgs) {
        super(x, y, widthIn, heightIn, Component.literal(buttonText), (button) -> {
        }, Button.DEFAULT_NARRATION);

        this.optionalArgs = optionalArgs;
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param buttonId     The ID for the control to Identify as
     * @param x            The Starting X Position for this Control
     * @param y            The Starting Y Position for this Control
     * @param widthIn      The Width for this Control
     * @param heightIn     The Height for this Control
     * @param buttonText   The display text, to display within this control
     * @param onPushEvent  The Click Event to Occur when this control is clicked
     * @param optionalArgs The optional Arguments, if any, to associate with this control
     */
    public ExtendedButtonControl(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final Runnable onPushEvent, final String... optionalArgs) {
        this(buttonId, x, y, widthIn, heightIn, buttonText, optionalArgs);
        setOnClick(onPushEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param buttonId     The ID for the control to Identify as
     * @param x            The Starting X Position for this Control
     * @param y            The Starting Y Position for this Control
     * @param widthIn      The Width for this Control
     * @param heightIn     The Height for this Control
     * @param buttonText   The display text, to display within this control
     * @param onPushEvent  The Click Event to Occur when this control is clicked
     * @param onHoverEvent The Hover Event to Occur when this control is hovered over
     * @param optionalArgs The optional Arguments, if any, to associate with this control
     */
    public ExtendedButtonControl(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final Runnable onPushEvent, final Runnable onHoverEvent, final String... optionalArgs) {
        this(buttonId, x, y, widthIn, heightIn, buttonText, onPushEvent, optionalArgs);
        setOnHover(onHoverEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param x            The Starting X Position for this Control
     * @param y            The Starting Y Position for this Control
     * @param widthIn      The Width for this Control
     * @param heightIn     The Height for this Control
     * @param buttonText   The display text, to display within this control
     * @param optionalArgs The optional Arguments, if any, to associate with this control
     */
    public ExtendedButtonControl(final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final String... optionalArgs) {
        this(ExtendedScreen.getNextIndex(), x, y, widthIn, heightIn, buttonText, optionalArgs);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param x            The Starting X Position for this Control
     * @param y            The Starting Y Position for this Control
     * @param widthIn      The Width for this Control
     * @param heightIn     The Height for this Control
     * @param buttonText   The display text, to display within this control
     * @param onPushEvent  The Click Event to Occur when this control is clicked
     * @param optionalArgs The optional Arguments, if any, to associate with this control
     */
    public ExtendedButtonControl(final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final Runnable onPushEvent, final String... optionalArgs) {
        this(x, y, widthIn, heightIn, buttonText, optionalArgs);
        setOnClick(onPushEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param x            The Starting X Position for this Control
     * @param y            The Starting Y Position for this Control
     * @param widthIn      The Width for this Control
     * @param heightIn     The Height for this Control
     * @param buttonText   The display text, to display within this control
     * @param onPushEvent  The Click Event to Occur when this control is clicked
     * @param onHoverEvent The Hover Event to Occur when this control is hovered over
     * @param optionalArgs The optional Arguments, if any, to associate with this control
     */
    public ExtendedButtonControl(final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final Runnable onPushEvent, final Runnable onHoverEvent, final String... optionalArgs) {
        this(x, y, widthIn, heightIn, buttonText, onPushEvent, optionalArgs);
        setOnHover(onHoverEvent);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param id            The ID for the Control to Identify as
     * @param xPos          The Starting X Position for this Control
     * @param yPos          The Starting Y Position for this Control
     * @param displayString The display text, to display within this Control
     */
    public ExtendedButtonControl(final int id, final int xPos, final int yPos, final String displayString) {
        this(id, xPos, yPos, 200, 20, displayString);
    }

    /**
     * Initialization Event for this Control, assigning defined arguments
     *
     * @param xPos          The Starting X Position for this Control
     * @param yPos          The Starting Y Position for this Control
     * @param displayString The display text, to display within this Control
     */
    public ExtendedButtonControl(final int xPos, final int yPos, final String displayString) {
        this(ExtendedScreen.getNextIndex(), xPos, yPos, displayString);
    }

    @Override
    public void preDraw(ExtendedScreen screen) {
        isOverScreen = RenderUtils.isMouseOver(screen);
    }

    @Override
    public void draw(final ExtendedScreen screen) {
        // N/A
    }

    @Override
    public void postDraw(final ExtendedScreen screen) {
        if (isOverScreen() && isHoveringOver()) {
            onHover();
        }
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
        final Minecraft mc = ModUtils.getMinecraft();
        if (mc != null && isControlVisible()) {
            setHoveringOver(isOverScreen() && RenderUtils.isMouseOver(mouseX, mouseY, this));

            renderBg(matrixStack, mc, mouseX, mouseY);
            final int color;

            if (!isControlEnabled()) {
                color = 10526880;
            } else if (isHoveringOrFocusingOver()) {
                color = 16777120;
            } else {
                color = 14737632;
            }

            RenderUtils.renderScrollingString(matrixStack,
                    mc,
                    mc.font, getControlMessage(),
                    getLeft() + 2, getTop(),
                    getRight() - 2, getBottom(),
                    color
            );
        }
    }

    float getBlitOffset() {
        return 0.0F;
    }

    void setBlitOffset(int blitOffset) {
        // N/A
    }

    /**
     * Returns the current Hover state of this control
     * <p>
     * 0 if the button is disabled<p>
     * 1 if the mouse is NOT hovering over this button<p>
     * 2 if it IS hovering over this button.
     */
    protected int getYImage(boolean hoveredOrFocused) {
        if (!active) {
            return 0;
        } else if (isHoveredOrFocused()) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Fired when the mouse button is dragged.<p>
     * Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void renderBg(@Nonnull GuiGraphics matrixStack, @Nonnull Minecraft mc, int mouseX, int mouseY) {
        if (isControlVisible()) {
            RenderUtils.renderSprite(matrixStack, graphics -> graphics.blitSprite(
                    RenderUtils.getButtonTexture(isControlEnabled(), isHoveringOrFocusingOver()),
                    getControlPosX(), getControlPosY(),
                    getControlWidth(), getControlHeight()
            ));
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control.<p>
     * Equivalent of MouseListener.mousePressed(MouseEvent e).
     */
    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return isOverScreen() && isControlEnabled() && isControlVisible() && isHoveringOver();
    }

    @Override
    public int getControlWidth() {
        return width;
    }

    @Override
    public void setControlWidth(final int width) {
        this.width = width;
    }

    @Override
    public int getControlHeight() {
        return height;
    }

    @Override
    public void setControlHeight(final int height) {
        this.height = height;
    }

    @Override
    public int getControlPosX() {
        return this.getX();
    }

    @Override
    public void setControlPosX(final int posX) {
        this.setX(posX);
    }

    @Override
    public int getControlPosY() {
        return this.getY();
    }

    @Override
    public void setControlPosY(final int posY) {
        this.setY(posY);
    }

    /**
     * Get whether the mouse is currently within screen bounds
     *
     * @return {@link Boolean#TRUE} is condition is satisfied
     */
    public boolean isOverScreen() {
        return isOverScreen;
    }

    /**
     * Retrieves, if any, the Optional Arguments assigned within this Control
     *
     * @return The Optional Arguments assigned within this Control, if any
     */
    public String[] getOptionalArgs() {
        return optionalArgs.clone();
    }

    /**
     * Set the Event to occur upon Mouse Click
     *
     * @param event The event to occur
     */
    public void setOnClick(final Runnable event) {
        onPushEvent = event;
    }

    /**
     * Triggers the onClick event to occur
     */
    public void onClick() {
        if (onPushEvent != null) {
            onPushEvent.run();
        }
    }

    /**
     * Event to trigger upon Button Action, including onClick Events
     */
    @Override
    public void onPress() {
        onClick();
    }

    /**
     * Sets the Event to occur upon Mouse Over
     *
     * @param event The event to occur
     */
    public void setOnHover(final Runnable event) {
        onHoverEvent = event;
    }

    /**
     * Triggers the onHover event to occur
     */
    public void onHover() {
        if (onHoverEvent != null) {
            onHoverEvent.run();
        }
    }

    /**
     * Gets the control's current raw display message
     *
     * @return The control's current raw display message
     */
    public Component getControlRawMessage() {
        return this.getMessage();
    }

    /**
     * Sets the control's raw display message to the specified value
     *
     * @param newMessage The new raw display message for this control
     */
    public void setControlRawMessage(final Component newMessage) {
        this.setMessage(newMessage);
    }

    /**
     * Gets the control's current text contents
     *
     * @return The control's current text contents
     */
    public String getControlMessage() {
        return getControlRawMessage().getString();
    }

    /**
     * Sets the control's display message to the specified value
     *
     * @param newMessage The new display message for this control
     */
    public void setControlMessage(final String newMessage) {
        setControlRawMessage(Component.literal(newMessage));
    }

    /**
     * Gets whether the control is currently active or enabled
     *
     * @return Whether the control is currently active or enabled
     */
    public boolean isControlEnabled() {
        return this.active;
    }

    /**
     * Sets the control's current enabled state
     *
     * @param isEnabled The new enable state for this control
     */
    public void setControlEnabled(final boolean isEnabled) {
        this.active = isEnabled;
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
     * Gets whether we are currently hovering over this control
     *
     * @return the current hover state
     */
    public boolean isHoveringOver() {
        return this.isHovered;
    }

    /**
     * Sets whether we are currently hovering over this control
     *
     * @param isHovered the new hover state
     */
    public void setHoveringOver(final boolean isHovered) {
        this.isHovered = isHovered;
    }

    /**
     * Gets whether we are currently focusing over this control
     *
     * @return the current focus state
     */
    public boolean isFocusedOver() {
        return isFocused();
    }

    /**
     * Sets whether we are currently focusing over this control
     *
     * @param isFocused the new focus state
     */
    public void setFocusedOver(final boolean isFocused) {
        setFocused(isFocused);
    }

    /**
     * Gets whether we are currently hovering or focusing over this control
     *
     * @return {@link Boolean#TRUE} if we are hovering or focusing over this control
     */
    public boolean isHoveringOrFocusingOver() {
        return isHoveringOver() || isFocusedOver();
    }

    /**
     * Retrieve the Z Level that this control will be rendering at
     *
     * @return the current Z Level
     */
    public double getZLevel() {
        return this.getBlitOffset();
    }

    /**
     * Set the Z Level that this control will be rendering at
     *
     * @param zLevel the new Z Level
     */
    public void setZLevel(final double zLevel) {
        setBlitOffset((int) zLevel);
    }
}
