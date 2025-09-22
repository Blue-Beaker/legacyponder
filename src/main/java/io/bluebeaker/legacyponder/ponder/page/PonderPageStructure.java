package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TemplateLoader;
import io.bluebeaker.legacyponder.utils.Vec2i;
import io.bluebeaker.legacyponder.world.StructureRenderManager;
import net.minecraft.world.gen.structure.template.Template;

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
    public boolean mouseDrag(GuiScreenPonder screen, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        Vec2i mouseDelta = screen.getMouseDelta();
        StructureRenderManager.rotationYaw+=mouseDelta.x;
        StructureRenderManager.rotationPitch+=mouseDelta.y;
        return true;
    }
}
