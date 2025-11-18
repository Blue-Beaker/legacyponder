package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.gui.MouseTracker;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TemplateLoader;
import io.bluebeaker.legacyponder.utils.Vec2i;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import static io.bluebeaker.legacyponder.render.StructureRenderManager.viewPos;

public class PonderPageStructure extends PonderPageBase{
    private final String structureID;

    public PonderPageStructure(String id){
        this.structureID = id;
    }

    @Override
    public void onSelected(){
        StructureRenderManager.getWorld().setWorldTime(0);
        if(this.structureID !=null) {
            PonderStructure structure = TemplateLoader.getStructure(structureID);
            if(structure!=null){
                StructureRenderManager.getWorld().loadStructure(structure);
            }
        }
        StructureRenderManager.resetCameraOffset();
        StructureRenderManager.updateBuffer();
    }
    @Override
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
        BoundingBox2D pageBounds = screen.getPageBounds();

        RenderUtils.setViewPort(pageBounds);
        StructureRenderManager.renderStructure(partialTicks, pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);

//        StructureRenderManager.getWorld().tick();
        RenderUtils.endViewPort();
    }

    @Override
    public boolean mouseScroll(GuiScreenPonder screen, int mouseX, int mouseY, int wheelDelta) {
        viewPos.zoom(-wheelDelta*0.01);
        return true;
    }

    @Override
    public boolean click(GuiScreenPonder screen, int x, int y, int button) {
        return super.click(screen, x, y, button);
    }

    @Override
    public boolean mouseReleased(GuiScreenPonder screen, int mouseX, int mouseY, int state) {
        return super.mouseReleased(screen, mouseX, mouseY, state);
    }

    @Override
    public boolean mouseDrag(GuiScreenPonder screen, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
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
