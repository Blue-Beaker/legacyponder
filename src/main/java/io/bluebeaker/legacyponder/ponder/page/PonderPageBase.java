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

    public void onSelected(){}

    /** Draws page
     * @param screen The ponder screen
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param partialTicks Parent partialTicks
     */
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
    }

    public boolean click(GuiScreenPonder screen, int x, int y, int button){
        return false;
    }

    public void addTextAt(float x, float y, float z, String text){
    }

    public boolean mouseScroll(GuiScreenPonder screen, int mouseX, int mouseY, int wheelDelta){
        return false;
    }

    public boolean mouseReleased(GuiScreenPonder screen, int mouseX, int mouseY, int state) {
        return false;
    }

    public boolean mouseDrag(GuiScreenPonder screen, int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        return false;
    }
}
