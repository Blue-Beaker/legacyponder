package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.ponder.page.PonderPageStructure;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

import static io.bluebeaker.legacyponder.render.StructureRenderManager.viewPos;

public class GuiPageStructure extends GuiInfoPage<PonderPageStructure> {
    public GuiPageStructure(GuiScreenPonder parent, PonderPageStructure page) {
        super(parent, page);
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
        StructureRenderManager.renderStructure(partialTicks, pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);

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
}
