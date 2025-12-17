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
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
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

        for (GuiHoverComponent hoverComponent : this.hoverComponents) {
            HoverComponent comp = hoverComponent.internal;
            float[] floats = RenderPosUtils.projectToScreen(comp.x, comp.y, comp.z, modelView, projection, viewport);

            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            int factor = scaledResolution.getScaleFactor();
            try {
                int x = (int) floats[0] / factor - pageBounds.x;
                int y = pageBounds.h - (int) floats[1] / factor + pageBounds.y;

                hoverComponent.draw(this.parent, x, y);
                // Debug
                parent.drawString(parent.mc.fontRenderer,"+",x,y,16777215);
                parent.drawHoveringText(Arrays.toString(floats),10,10);
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
