package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.gui.MouseTracker;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TemplateLoader;
import io.bluebeaker.legacyponder.utils.Vec2i;
import io.bluebeaker.legacyponder.render.StructureRenderManager;

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
            }else {
                StructureRenderManager.getWorld().loadTemplate(TemplateLoader.getTemplate(structureID));
            }
        }
        StructureRenderManager.getWorld().tick();
        StructureRenderManager.updateBuffer();
    }
    @Override
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
        BoundingBox2D pageBounds = screen.getPageBounds();

        RenderUtils.setViewPort(pageBounds);
        StructureRenderManager.renderStructure(partialTicks, pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);
        RenderUtils.endViewPort();
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
        if(clickedMouseButton==0){
            viewPos.addYaw(mouseDelta.x);
            viewPos.addPitch(-mouseDelta.y);
        }else if (clickedMouseButton==1){
            viewPos.translate(mouseDelta.x/100f,mouseDelta.y/100f,0);
        }
        return true;
    }
}
