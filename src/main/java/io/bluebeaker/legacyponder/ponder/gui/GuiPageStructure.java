package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.hover.GuiHoverComponent;
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
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static io.bluebeaker.legacyponder.render.StructureRenderManager.viewPos;

public class GuiPageStructure extends GuiInfoPage<PonderPageStructure> {

    protected final List<GuiHoverComponent> hoverComponents = new ArrayList<>();

    public GuiPageStructure(GuiScreenPonder parent, PonderPageStructure page) {
        super(parent, page);

        for (HoverComponent hoverComponent : page.getHoverComponents()) {
            this.hoverComponents.add(hoverComponent.getGui());
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

        // 准备缓冲区
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);

        // 获取当前矩阵和视口
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);


        StructureRenderManager.cleanTransformations();

        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        int scale = scaled.getScaleFactor();

        for (GuiHoverComponent hoverComponent : this.hoverComponents) {
            Vector3f pos = hoverComponent.internal.pos;
            hoverComponent.internal.setColor(32,255,100);
            int color = hoverComponent.internal.color;

            float[] floats = RenderPosUtils.projectToScreen(pos.x, pos.y, pos.z, modelView, projection, viewport);
            // Try to draw the component
            try {
                float x = floats[0] / scale - pageBounds.x;
                float y = parent.height - floats[1] / scale - pageBounds.y;

                int w = hoverComponent.getDrawable().getWidth();
                int h = hoverComponent.getDrawable().getHeight();

                int hoverX = Math.round(x+60);
                int lineEndY = Math.round(y-30);
                int hoverY = lineEndY-(h/2);
//
                GlStateManager.glLineWidth(scale);

                GlStateManager.disableTexture2D();

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;

                // Line
                bufferbuilder.pos(x,y,0).color(r, g, b,255).endVertex();
                bufferbuilder.pos(hoverX,lineEndY,0).color(r, g, b,255).endVertex();

                // Box
                bufferbuilder.pos(hoverX,hoverY,0).color(r, g, b,255).endVertex();
                bufferbuilder.pos(hoverX+w+4,hoverY,0).color(r, g, b,255).endVertex();
                bufferbuilder.pos(hoverX+w+4,hoverY+h+4,0).color(r, g, b,255).endVertex();
                bufferbuilder.pos(hoverX,hoverY+h+4,0).color(r, g, b,255).endVertex();
                bufferbuilder.pos(hoverX,lineEndY,0).color(r, g, b,255).endVertex();

                tessellator.draw();

                GlStateManager.glLineWidth(1.0F);
                GlStateManager.enableAlpha();
                GuiUtils.drawGradientRect(0,hoverX,hoverY,hoverX+w+4,hoverY+h+4,color|0x80000000,color|0x80000000);
                GlStateManager.disableAlpha();

                GlStateManager.enableTexture2D();


                hoverComponent.draw(this.parent, hoverX+2, hoverY+2);
                // Debug
//                parent.drawString(parent.mc.fontRenderer,"+",x,y,16777215);
//                parent.drawHoveringText(Arrays.toString(floats),10,10);
            } catch (Exception e) {
                LegacyPonder.getLogger().warn("Error drawing hoverComponent {}:",hoverComponent,e);
            }
        }

        RenderUtils.endViewPort();
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        super.mouseScroll(mouseX, mouseY, wheelDelta);
        viewPos.zoom(-wheelDelta*0.01);
        return true;
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) throws IOException {
        return super.onMouseClick(x, y, button);
    }

    @Override
    public boolean onMouseRelease(int x, int y, int state) {
        return super.onMouseRelease(x, y, state);
    }

    @Override
    public boolean onMouseDrag(int x, int y, int clickedMouseButton, long timeSinceLastClick) {
        super.onMouseDrag(x, y, clickedMouseButton, timeSinceLastClick);
        Vec2i mouseDelta = MouseTracker.INSTANCE.getMouseDelta();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaledResolution.getScaleFactor();
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
