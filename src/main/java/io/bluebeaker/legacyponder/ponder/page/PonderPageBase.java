package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageDefault;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;

public abstract class PonderPageBase {

    public PonderPageBase() {
    }

    public void addTextAt(float x, float y, float z, String text){
    }

    public GuiInfoPage<? extends PonderPageBase> getGuiPage(GuiScreenPonder parent){
        return new GuiPageDefault<>(parent,this);
    }

    /** Draws page
     * @param screen The ponder screen
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param partialTicks Parent partialTicks
     */
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
    }
}
