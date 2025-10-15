package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiInfoPage<T extends PonderPageBase> extends GuiScreen {
    final GuiScreenPonder parent;
    final T page;
    public GuiInfoPage(GuiScreenPonder parent, T page) {
        this.parent = parent;
        this.page = page;
    }

    public void onPageRefresh(){
        this.page.onSelected();
    }

    /** Draws page
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param partialTicks Parent partialTicks
     */
    public void draw(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX,mouseY,partialTicks);
        page.draw(parent,mouseX,mouseY,partialTicks);
    }

    /** Processes scroll event
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param wheelDelta Wheel scrolled amount and direction
     * @return Whether the event was processed
     */
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta){
        return page.mouseScroll(parent,mouseX,mouseY,wheelDelta);
    }

    /** Processes mouse click
     * @param x Mouse X relative to page area
     * @param y Mouse Y relative to page area
     * @param button Clicked button
     * @return Whether the event was processed
     */
    public boolean onMouseClick(int x, int y, int button) throws IOException {
        super.mouseClicked(x,y,button);
        return page.click(parent,x,y,button);
    }

    /** Processes mouse click
     * @param x Mouse X relative to page area
     * @param y Mouse Y relative to page area
     * @param state State
     * @return Whether the event was processed
     */
    public boolean onMouseRelease(int x, int y, int state) {
        super.mouseReleased(x,y,state);
        return page.mouseReleased(parent,x,y,state);
    }

    /** Processes mouse click
     * @param x Mouse X relative to page area
     * @param y Mouse Y relative to page area
     * @param clickedMouseButton Clicked button
     * @param timeSinceLastClick Time since last click
     * @return Whether the event was processed
     */
    public boolean onMouseDrag(int x, int y, int clickedMouseButton, long timeSinceLastClick) {
        return page.mouseDrag(parent,x,y,clickedMouseButton,timeSinceLastClick);
    }
}
