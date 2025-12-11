package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPageDummy")
@ZenRegister
public class PonderPageDummy extends PonderPageBase {
    private String text = null;

    public PonderPageDummy(String text){
        this.text=text;
    }
    public PonderPageDummy(){
    }
    @Override
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
        BoundingBox2D pageBounds = screen.getPageBounds();

        RenderUtils.setViewPort(pageBounds);

        screen.drawString(screen.mc.fontRenderer,this.text==null?String.format("Debug Page: %s,%s",mouseX,mouseY):this.text,mouseX,mouseY,0xFFFFFFFF);

        RenderUtils.endViewPort();

    }
}
