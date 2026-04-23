package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PageDummy")
@ZenRegister
public class PageDummy extends PageBase {
    private String text = null;

    public PageDummy(String text){
        this.text=text;
    }
    public PageDummy(){
    }
    @Override
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY, float partialTicks){
        BoundingBox2D pageBounds = screen.getPageBounds();

        RenderUtils.setViewPort(pageBounds);

        screen.drawString(screen.mc.fontRenderer,this.text==null?String.format("Debug Page: %s,%s",mouseX,mouseY):this.text,mouseX,mouseY,0xFFFFFFFF);

        RenderUtils.endViewPort();

    }
}
