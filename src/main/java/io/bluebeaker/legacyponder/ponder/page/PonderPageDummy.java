package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;

public class PonderPageDummy extends PonderPageBase {
    private String text = null;

    public PonderPageDummy(String text){
        this.text=text;
    }
    public PonderPageDummy(){
    }
    @Override
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
        screen.drawString(screen.mc.fontRenderer,this.text==null?this.toString():this.text,mouseX,mouseY,0xFFFFFFFF);
    }
}
