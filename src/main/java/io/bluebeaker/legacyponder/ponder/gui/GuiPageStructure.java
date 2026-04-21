package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.hover.GuiHoverComponent;
import io.bluebeaker.legacyponder.ponder.hover.HighlightArea;
import io.bluebeaker.legacyponder.ponder.hover.HoverComponent;
import io.bluebeaker.legacyponder.ponder.page.PonderPageStructure;
import io.bluebeaker.legacyponder.render.RenderPosUtils;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static io.bluebeaker.legacyponder.LegacyPonderConfig.ui;
import static io.bluebeaker.legacyponder.render.StructureRenderManager.getWorld;
import static io.bluebeaker.legacyponder.render.StructureRenderManager.viewPos;

public class GuiPageStructure extends GuiInfoPage<PonderPageStructure> {

    /** All hover components in this structure page */
    protected final List<GuiHoverComponent> components = new ArrayList<>();
    /** Currently hovered component */
    protected GuiHoverComponent hoverComp = null;
    /** Is dragging a component? */
    protected boolean dragComp = false;
    /** Is dragging the camera? */
    protected boolean dragCam = false;
    /** Used when dragging a component */
    protected int dragX = 0;
    protected int dragY = 0;

    private FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    private FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    private IntBuffer viewport = BufferUtils.createIntBuffer(16);
    private ItemStack hoverItem = ItemStack.EMPTY;
    protected GuiSlider slider = null;

    public GuiPageStructure(GuiScreenPonder parent, PonderPageStructure page) {
        super(parent, page);

        for (HoverComponent hoverComponent : page.getHoverComponents()) {
            this.components.add(hoverComponent.getGui());
        }
    }

    @Override
    public void onPageRefresh() {
        super.onPageRefresh();
        StructureRenderManager.getWorld().setWorldTime(0);

        PonderStructure structure = StructureLoader.getStructure(page.structureID);
        if(structure!=null){
            StructureRenderManager.getWorld().loadStructure(structure);
        }
        StructureRenderManager.resetCameraOffset();
        StructureRenderManager.updateBuffer();
    }

    @Override
    public void onResize() {
        super.onResize();
        int w = pageBounds.w;
        int h = pageBounds.h;
        this.buttonList.clear();
        int buttonsHeight = 16;
        this.buttonList.add(new GuiButtonExt(10,0,h- buttonsHeight,20, buttonsHeight,"<>"));
        this.buttonList.add(new GuiButtonExt(14,20,h- buttonsHeight,20, buttonsHeight,"0"));
        this.buttonList.add(new GuiButtonExt(11,160,h- buttonsHeight,20, buttonsHeight,"+"));
        this.buttonList.add(new GuiButtonExt(12,40,h- buttonsHeight,20, buttonsHeight,"-"));
        GuiSlider slider = new GuiSlider(13, 60, h - buttonsHeight, 100, buttonsHeight, "", "", -5, 5, viewPos.zoom_power, true, true, slider1 -> viewPos.zoom_power= slider1.getValue());
        slider.precision=2;
        this.slider=slider;
        updateSlider();
        this.buttonList.add(slider);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id==10){
            StructureRenderManager.viewPos.resetAll();
        } else if (button.id == 11) {
            viewPos.zoom(0.1);
        } else if (button.id == 12) {
            viewPos.zoom(-0.1);
        } else if (button.id == 14) {
            viewPos.zoom_power=0;
        }
        updateSlider();
    }

    protected void updateSlider() {
        this.slider.maxValue=Math.max(5,viewPos.zoom_power);
        this.slider.minValue=Math.min(-5,viewPos.zoom_power);
        this.slider.setValue(viewPos.zoom_power);
        this.slider.updateSlider();
        this.slider.displayString=String.format("%.2f",this.slider.getValue());
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

        RenderUtils.setViewPort(pageBounds);

        StructureRenderManager.prepareTransformations(pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);


        StructureRenderManager.renderStructure(partialTicks, pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);

        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        int scale = scaled.getScaleFactor();

        if(!this.page.getHighlightAreas().isEmpty()){
            drawHighlightBoxes(scale);
        }

        // Get Matrix for hover component rendering and raycasting
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        StructureRenderManager.cleanTransformations();


        if(!this.components.isEmpty()){


            GlStateManager.enableTexture2D();
            GlStateManager.disableTexture2D();

            drawHoverLines2(scale, modelView, projection, viewport);

            drawHoverComponents(mouseX, mouseY, modelView, projection, viewport, scale);

        }

        if(hoverComp == null && !parent.isMouseDownInPage() && this.buttonList.stream().noneMatch(GuiButton::isMouseOver)){
            RayTraceResult rayTraceResult = raycastFromCursor(MouseTracker.INSTANCE.x, MouseTracker.INSTANCE.y, modelView, projection, viewport);

            if(rayTraceResult!=null && rayTraceResult.typeOfHit== RayTraceResult.Type.BLOCK){
                BlockPos pos = rayTraceResult.getBlockPos();
                try {
                    this.hoverItem = getWorld().getBlockState(pos).getBlock().getPickBlock(getWorld().getBlockState(pos), rayTraceResult, mc.world, pos, StructureRenderManager.getPlayer());
                }catch (Exception e){
                    LegacyPonder.getLogger().warn("Error getting pick block for pos {}:",pos,e);
                    this.hoverItem = ItemStack.EMPTY;
                }

                if(!hoverItem.isEmpty()){
                    String text = hoverItem.getDisplayName();
                    int textX = mouseX + 5;
                    int textY = mouseY - 15;

                    drawHoverBackground(new Color(0x5028007f), textX-4, textY-4, textX+mc.fontRenderer.getStringWidth(text)+4, textY+mc.fontRenderer.FONT_HEIGHT+4);

                    mc.fontRenderer.drawStringWithShadow(text, textX, textY, Color.white.getRGB());
                }
            }
        }else {
            this.hoverItem=ItemStack.EMPTY;
        }

        super.draw(mouseX, mouseY, partialTicks);
        RenderUtils.endViewPort();

    }

    private void drawHighlightBoxes(int scale) {
        GlStateManager.glLineWidth(scale);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        for (HighlightArea highlightArea : page.getHighlightAreas()) {
            BlockPos pos1 = highlightArea.getPos1();
            BlockPos pos2 = highlightArea.getPos2();
            Color color = highlightArea.getColor();
            float fighting_fix = 0.01f;
            float x1= pos1.getX()-fighting_fix;
            float y1= pos1.getY()-fighting_fix;
            float z1= pos1.getZ()-fighting_fix;
            float x2= pos2.getX()+1+fighting_fix;
            float y2= pos2.getY()+1+fighting_fix;
            float z2= pos2.getZ()+1+fighting_fix;

            bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();

            // Line
            bufferbuilder.pos(x1,y1,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y1,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y2,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x1,y2,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x1,y1,z1).color(r, g, b,255).endVertex();

            bufferbuilder.pos(x1,y1,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y1,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y1,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y1,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y2,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y2,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x2,y2,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x1,y2,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x1,y2,z1).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x1,y2,z2).color(r, g, b,255).endVertex();
            bufferbuilder.pos(x1,y1,z2).color(r, g, b,255).endVertex();

            tessellator.draw();
        }
        GlStateManager.enableTexture2D();
    }

    @Nullable
    private RayTraceResult raycastFromCursor(int x, int y, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport){
        float[] pos0 = RenderPosUtils.unprojectFromScreen(x, y, 0.0F, modelView, projection, viewport);
        float[] pos1 = RenderPosUtils.unprojectFromScreen(x, y, 1.0F, modelView, projection, viewport);
        Vec3d from = new Vec3d(pos0[0], pos0[1], pos0[2]).add(new Vec3d(StructureRenderManager.STRUCTURE_OFFSET));
        Vec3d to = new Vec3d(pos1[0], pos1[1], pos1[2]).add(new Vec3d(StructureRenderManager.STRUCTURE_OFFSET));

        return getWorld().rayTraceBlocks(from, to);
    }

    private void drawHoverLines(int scale, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport) {
        GlStateManager.glLineWidth(scale);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        for (GuiHoverComponent hoverComponent : this.components) {
            Vector3f pos = hoverComponent.internal.pos;
            Color color = hoverComponent.internal.getColor();
            float[] floats = RenderPosUtils.projectToScreen(pos.x, pos.y, pos.z, modelView, projection, viewport);

            float x = floats[0] / scale;
            float y = floats[1] / scale;

            int hoverX = Math.round(x+ hoverComponent.offX);
            int lineEndY = Math.round(y- hoverComponent.offY);

            float[] pos1 = RenderPosUtils.unprojectFromScreen(hoverX* scale, lineEndY* scale, floats[2], modelView, projection, viewport);


            bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();

            // Line
            bufferbuilder.pos(pos.x,pos.y,pos.z).color(r, g, b,255).endVertex();
            bufferbuilder.pos(pos1[0],pos1[1],pos1[2]).color(r, g, b,255).endVertex();

            tessellator.draw();


        }
        GlStateManager.glLineWidth(1.0F);
    }
    private void drawHoverLines2(int scale, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport) {
        GlStateManager.glLineWidth(scale);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        for (GuiHoverComponent hoverComponent : this.components) {
            Vector3f pos = hoverComponent.internal.pos;
            Color color = hoverComponent.internal.getColor();
            float[] floats = RenderPosUtils.projectToScreen(pos.x, pos.y, pos.z, modelView, projection, viewport);

            float x = floats[0] / scale - pageBounds.x;
            float y = parent.height - floats[1] / scale - pageBounds.y;

            int hoverX = Math.round(x+ hoverComponent.offX);
            int hoverY = Math.round(y+ hoverComponent.offY);

            hoverComponent.updatePosition(hoverX,hoverY);

            int x1 = hoverComponent.getDrawable().getXMin();
            int y1 = hoverComponent.getDrawable().getYMin();
            int x2 = hoverComponent.getDrawable().getXMax();
            int y2 = hoverComponent.getDrawable().getYMax();

            int lineEndX = (x1+x2)/2;
            int lineEndY = (y1+y2)/2;

            bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();

            // Line
            bufferbuilder.pos(x,y,0).color(r, g, b, 0).endVertex();
            bufferbuilder.pos(lineEndX,lineEndY,0).color(r, g, b, 255).endVertex();

            tessellator.draw();


        }
        GlStateManager.glLineWidth(1.0F);
    }

    private void drawHoverComponents(int mouseX, int mouseY, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport, int scale) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();

        for (GuiHoverComponent hoverComponent : this.components) {
            Vector3f pos = hoverComponent.internal.pos;
            Color color = hoverComponent.internal.getColor();
            float[] floats = RenderPosUtils.projectToScreen(pos.x, pos.y, pos.z, modelView, projection, viewport);
            // Try to draw the component
            try {
                float x = floats[0] / scale - pageBounds.x;
                float y = parent.height - floats[1] / scale - pageBounds.y;

                int hoverX = Math.round(x+ hoverComponent.offX);
                int hoverY = Math.round(y+ hoverComponent.offY);

                int x1 = hoverComponent.getDrawable().getXMin();
                int y1 = hoverComponent.getDrawable().getYMin();
                int x2 = hoverComponent.getDrawable().getXMax();
                int y2 = hoverComponent.getDrawable().getYMax();

                drawHoverBackground(color, x1-3, y1-3, x2+3, y2+3);

                hoverComponent.draw(this.parent, hoverX, hoverY, mouseX, mouseY);

            } catch (Exception e) {
                LegacyPonder.getLogger().warn("Error drawing hoverComponent {}:",hoverComponent,e);
            }
        }
        // When dragging a component, skip hover detection
        if(dragComp) return;
        // Check if hovering any component, start from the last one to ensure hovering the topmost one
        this.hoverComp =null;
        for (int i = components.size()-1; i >=0; i--) {
            GuiHoverComponent hoveredComponent1 = components.get(i);

            if(this.buttonList.stream().noneMatch(GuiButton::isMouseOver) && hoveredComponent1.getDrawable().getBoundingBox().expand(5).contains(mouseX,mouseY)){
                this.hoverComp = hoveredComponent1;
                break;
            }
        }
        // If hovering a component, call its hover action
        if(this.hoverComp !=null && this.hoverComp.getDrawable().isFocused(parent,mouseX,mouseY)){
            this.hoverComp.getDrawable().onMouseHover(this.parent, mouseX, mouseY);
        }
    }

    private static void drawHoverBackground(Color color, int x1, int y1, int x2, int y2) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int rgb = color.getRGB();

        // Box
        GuiUtils.drawGradientRect(0, x1 +1, y1, x2-1, y1 +1, rgb, rgb);
        GuiUtils.drawGradientRect(0, x1 +1, y2-1, x2-1, y2, rgb, rgb);

        GuiUtils.drawGradientRect(0, x1, y1 +1, x1 +1, y2-1, rgb, rgb);
        GuiUtils.drawGradientRect(0, x2-1, y1 +1, x2, y2-1, rgb, rgb);

        int col2 = (int)(r*0.2F)<<16 | (int)(g*0.2F)<<8 | (int)(b*0.2F) | 0xFF000000;
        GuiUtils.drawGradientRect(0, x1 +1, y1 +1, x2-1, y2-1, col2,col2);
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        super.mouseScroll(mouseX, mouseY, wheelDelta);
        viewPos.zoom((double) wheelDelta /120* ui.wheel_sensivity);
        updateSlider();
        return true;
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) throws IOException {
        if(this.buttonList.stream().anyMatch(GuiButton::isMouseOver))
            return super.onMouseClick(x, y, button);
        if(hoverComp !=null){
            boolean clicked = hoverComp.getDrawable().onMouseClick(this.parent,x,y,button);
            if(clicked) return true;
            dragComp =true;
            dragX =x- hoverComp.offX;
            dragY =y- hoverComp.offY;
            return true;
        }else {
            dragCam = true;
        }
        return super.onMouseClick(x, y, button);
    }

    @Override
    public boolean onMouseRelease(int x, int y, int state) {
        if(dragComp){
            dragComp =false;
            dragX =0;
            dragY =0;
            return true;
        }
        dragCam = false;
        return super.onMouseRelease(x, y, state);
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(parent.isMouseDownInPage()) return;

        if(hoverComp !=null){
            hoverComp.getDrawable().onKeyTyped(this.parent,typedChar,keyCode);
            return;
        }
        try {
            JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
            if(action!= JEIUtils.JEIAction.NONE && !this.hoverItem.isEmpty()){
                JEIUtils.handleJEIAction(hoverItem, action);
                return;
            }
        }catch (Throwable e){
            LegacyPonder.getLogger().warn("Error handling key input {}:",keyCode,e);
        }
        super.onKeyTyped(typedChar, keyCode);
    }

    @Override
    public boolean onMouseDrag(int x, int y, int clickedMouseButton, long timeSinceLastClick) {

        if(dragComp && hoverComp !=null){
            hoverComp.offX =x-dragX;
            hoverComp.offY =y-dragY;
            return true;
        }
        if(!dragCam) return false;

        super.onMouseDrag(x, y, clickedMouseButton, timeSinceLastClick);

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaledResolution.getScaleFactor();
        Vec2i mouseDelta = MouseTracker.INSTANCE.getMouseDelta();
        double deltaX = (float) mouseDelta.x /factor * ui.cursor_sensivity;
        double deltaY = (float) mouseDelta.y /factor * ui.cursor_sensivity;

        if(clickedMouseButton==0){
            viewPos.addYaw((float) deltaX);
            viewPos.addPitch((float) -deltaY);
        }else if (clickedMouseButton<=2){
            viewPos.translate(deltaX/100f,deltaY/100f,0);
        }
        return true;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
//        StructureRenderManager.getWorld().tick();
    }
}
