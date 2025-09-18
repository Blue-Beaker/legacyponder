package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.info.PonderInfo;

public class PonderPageBase {
    public PonderInfo info;
    
    protected PonderPageBase(PonderInfo info) {
        this.info=info;
    }
    protected PonderPageBase(){
        this(null);
    }

    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
    }

    public void click(int x, int y, int button){
    }

    public void addTextAt(float x, float y, float z, String text){
    }
}
