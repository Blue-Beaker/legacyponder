package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
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
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static io.bluebeaker.legacyponder.render.StructureRenderManager.viewPos;

public class GuiPageStructure extends GuiInfoPage<PonderPageStructure> {

    /** All hover components in this structure page */
    protected final List<GuiHoverComponent> components = new ArrayList<>();
    /** Currently hovered component */
    protected GuiHoverComponent hoverComp = null;
    /** Is dragging a component? */
    protected boolean dragComp = false;
    /** Used when dragging a component */
    protected int dragX = 0;
    protected int dragY = 0;

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
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);

        RenderUtils.setViewPort(pageBounds);

        StructureRenderManager.prepareTransformations(pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);


        StructureRenderManager.renderStructure(partialTicks, pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);

        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        int scale = scaled.getScaleFactor();

        if(!this.page.getHighlightAreas().isEmpty()){
            drawHighlightBoxes(scale);
        }

        if(!this.components.isEmpty()){

            // 准备缓冲区
            FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
            FloatBuffer projection = BufferUtils.createFloatBuffer(16);
            IntBuffer viewport = BufferUtils.createIntBuffer(16);

            // 获取当前矩阵和视口
            GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
            GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
            GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

//            drawHoverLines(scale, modelView, projection, viewport);

            StructureRenderManager.cleanTransformations();


            GlStateManager.enableTexture2D();
            GlStateManager.disableTexture2D();

            drawHoverLines2(scale, modelView, projection, viewport);
            drawHoverComponents(mouseX, mouseY, modelView, projection, viewport, scale);

        }else {
            StructureRenderManager.cleanTransformations();
        }

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

            int w = hoverComponent.getDrawable().getWidth();
            int h = hoverComponent.getDrawable().getHeight();

            int hoverX = Math.round(x+ hoverComponent.offX);
            int hoverY = Math.round(y+ hoverComponent.offY);

            int lineEndX = hoverX+w/2;
            int lineEndY = hoverY+h/2;

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

                int w = hoverComponent.getDrawable().getWidth();
                int h = hoverComponent.getDrawable().getHeight();

                int hoverX = Math.round(x+ hoverComponent.offX);
                int hoverY = Math.round(y+ hoverComponent.offY);

                drawHoverBackground(color, hoverX, hoverY, w, h);

                hoverComponent.draw(this.parent, hoverX+2, hoverY+2, mouseX, mouseY);

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

            if(hoveredComponent1.getDrawable().getBoundingBox().contains(mouseX,mouseY)){
                this.hoverComp = hoveredComponent1;
                break;
            }
        }
        // If hovering a component, call its hover action
        if(this.hoverComp !=null){
            this.hoverComp.getDrawable().onMouseHover(this.parent, mouseX, mouseY);
        }
    }

    private static void drawHoverBackground(Color color, int hoverX, int hoverY, int w, int h) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int rgb = color.getRGB();
        // Box
        GuiUtils.drawGradientRect(0, hoverX +1, hoverY, hoverX + w +3, hoverY +1, rgb, rgb);
        GuiUtils.drawGradientRect(0, hoverX +1, hoverY + h +3, hoverX + w +3, hoverY + h +4, rgb, rgb);

        GuiUtils.drawGradientRect(0, hoverX, hoverY +1, hoverX +1, hoverY + h +3, rgb, rgb);
        GuiUtils.drawGradientRect(0, hoverX + w +3, hoverY +1, hoverX + w +4, hoverY + h +3, rgb, rgb);

        int col2 = (int)(r*0.2F)<<16 | (int)(g*0.2F)<<8 | (int)(b*0.2F) | 0xFF000000;
        GuiUtils.drawGradientRect(0, hoverX +1, hoverY +1, hoverX + w +3, hoverY + h +3, col2,col2);
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        super.mouseScroll(mouseX, mouseY, wheelDelta);
        viewPos.zoom(-wheelDelta*0.01);
        return true;
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) throws IOException {
        if(hoverComp !=null){
            dragComp =true;
            dragX =x- hoverComp.offX;
            dragY =y- hoverComp.offY;
            return true;
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
        return super.onMouseRelease(x, y, state);
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(hoverComp !=null){
            hoverComp.getDrawable().onKeyTyped(this.parent,typedChar,keyCode);
            return;
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

        super.onMouseDrag(x, y, clickedMouseButton, timeSinceLastClick);

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaledResolution.getScaleFactor();
        Vec2i mouseDelta = MouseTracker.INSTANCE.getMouseDelta();
        float deltaX = (float) mouseDelta.x /factor;
        float deltaY = (float) mouseDelta.y /factor;

        if(clickedMouseButton==0){
            viewPos.addYaw(deltaX);
            viewPos.addPitch(-deltaY);
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
