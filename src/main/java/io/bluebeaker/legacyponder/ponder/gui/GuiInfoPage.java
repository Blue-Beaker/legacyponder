package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiInfoPage<T extends PonderPageBase> extends GuiScreen {
    final GuiScreenPonder parent;
    final T page;
    protected BoundingBox2D pageBounds = BoundingBox2D.EMPTY;
    private final String formattedDescription;
    public GuiInfoPage(GuiScreenPonder parent, T page) {
        this.parent = parent;
        this.page = page;
        this.formattedDescription = TextUtils.formatKey(page.getDescription());
        onResize();
    }

    public void onResize(){
        this.pageBounds=parent.getPageBounds();
    }
    public void onPageRefresh(){
    }

    /** Draws page
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param partialTicks Parent partialTicks
     */
    public void draw(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX,mouseY,partialTicks);
    }

    /** Processes scroll event
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param wheelDelta Wheel scrolled amount and direction
     * @return Whether the event was processed
     */
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta){
        return false;
    }

    /** Processes mouse click
     * @param x Mouse X relative to page area
     * @param y Mouse Y relative to page area
     * @param button Clicked button
     * @return Whether the event was processed
     */
    public boolean onMouseClick(int x, int y, int button) throws IOException {
        super.mouseClicked(x,y,button);
        return false;
    }

    /** Processes mouse click
     * @param x Mouse X relative to page area
     * @param y Mouse Y relative to page area
     * @param state State
     * @return Whether the event was processed
     */
    public boolean onMouseRelease(int x, int y, int state) {
        super.mouseReleased(x,y,state);
        return false;
    }

    /** Processes mouse click
     * @param x Mouse X relative to page area
     * @param y Mouse Y relative to page area
     * @param clickedMouseButton Clicked button
     * @param timeSinceLastClick Time since last click
     * @return Whether the event was processed
     */
    public boolean onMouseDrag(int x, int y, int clickedMouseButton, long timeSinceLastClick) {
        return false;
    }

    public String getFormattedDescription() {
        return formattedDescription;
    }
}
