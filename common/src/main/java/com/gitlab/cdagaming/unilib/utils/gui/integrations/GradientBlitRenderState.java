/*
 * MIT License
 *
 * Copyright (c) 2018 - 2026 CDAGaming (cstack2011@yahoo.com)
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

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.BlitRenderState;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;

/**
 * An extension of {@link BlitRenderState},
 * designed to support gradient functionality.
 *
 * @author CDAGaming
 */
public record GradientBlitRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose,
                                      int x0, int y0, int x1, int y1,
                                      float u0, float u1, float v0, float v1,
                                      int col1, int col2,
                                      @Nullable ScreenRectangle scissorArea,
                                      @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
    public GradientBlitRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose,
                                   int x0, int y0, int x1, int y1,
                                   float u0, float u1, float v0, float v1,
                                   int col1, int col2,
                                   @Nullable ScreenRectangle scissorArea,
                                   @Nullable ScreenRectangle bounds) {
        this.pipeline = pipeline;
        this.textureSetup = textureSetup;
        this.pose = pose;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.u0 = u0;
        this.u1 = u1;
        this.v0 = v0;
        this.v1 = v1;
        this.col1 = col1;
        this.col2 = col2;
        this.scissorArea = scissorArea;
        this.bounds = bounds;
    }

    public GradientBlitRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose,
                                   int x0, int y0, int x1, int y1,
                                   float u0, float u1, float v0, float v1,
                                   int col1, int col2,
                                   @Nullable ScreenRectangle scissorArea) {
        this(pipeline, textureSetup, pose,
                x0, y0, x1, y1,
                u0, u1, v0, v1,
                col1, col2,
                scissorArea,
                BlitRenderState.getBounds(x0, y0, x1, y1, pose, scissorArea));
    }

    public GradientBlitRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2f pose,
                                   int x0, int y0, int x1, int y1,
                                   float u0, float u1, float v0, float v1,
                                   int color,
                                   @Nullable ScreenRectangle scissorArea) {
        this(pipeline, textureSetup, pose,
                x0, y0, x1, y1,
                u0, u1, v0, v1,
                color, color,
                scissorArea);
    }

    @Override
    public void buildVertices(@NotNull VertexConsumer vertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose(), (float) x0(), (float) y0()).setUv(u0(), v0()).setColor(col1());
        vertexConsumer.addVertexWith2DPose(pose(), (float) x0(), (float) y1()).setUv(u0(), v1()).setColor(col2());
        vertexConsumer.addVertexWith2DPose(pose(), (float) x1(), (float) y1()).setUv(u1(), v1()).setColor(col2());
        vertexConsumer.addVertexWith2DPose(pose(), (float) x1(), (float) y0()).setUv(u1(), v0()).setColor(col1());
    }
}
