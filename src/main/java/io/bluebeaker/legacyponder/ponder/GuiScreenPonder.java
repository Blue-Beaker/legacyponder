package io.bluebeaker.legacyponder.ponder;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.MouseTracker;
import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import io.bluebeaker.legacyponder.ponder.page.PonderPageBlank;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiScreenPonder extends GuiScreen {
    @Nonnull
    protected Entry currentEntry = EmptyEntry.INSTANCE;
    protected int currentPageID = 0;
    @Nonnull
    protected PonderPageBase currentPage = PonderPageBlank.INSTANCE;
    protected int pages = 0;
    @Nonnull
    protected GuiInfoPage<?> guiInfoPage = PonderPageBlank.INSTANCE.getGuiPage(this);

    public void setLastScreen(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
    }

    protected GuiScreen lastScreen;
    protected String lastEntryTitle = "";
    protected String ponderID = "";

    protected final List<HistoryEntry> history = new ArrayList<>();

    protected boolean isLinkActive = false;

    public boolean isMouseDownInPage() {
        return mouseDownInPage;
    }
    public void releaseMouse(){
        mouseDownInPage=false;
    }

    protected boolean mouseDownInPage = false;

    public Vec2i getLastMousePos() {
        return lastMousePos;
    }

    public Vec2i getMouseDelta() {
        return mouseDelta;
    }

    protected Vec2i lastMousePos = new Vec2i(0,0);
    protected Vec2i mouseDelta = new Vec2i(0,0);

    public BoundingBox2D getPageBounds() {
        return pageBounds;
    }

    protected BoundingBox2D pageBounds = BoundingBox2D.EMPTY;

    public GuiScreenPonder(){
        StructureRenderManager.viewPos.resetAll();
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonExt(0,this.width-20,0,20,20,"x"));
        this.buttonList.add(new GuiButtonExt(1,2,this.height-22,20,20,"<"));
        this.buttonList.add(new GuiButtonExt(2,23,this.height-22,20,20,">"));

        if(!this.history.isEmpty()){
            HistoryEntry lastHistory = getLastHistory();
            this.lastEntryTitle=(I18n.format(PonderRegistry.getEntries().get(lastHistory.id).title)+" : "+(lastHistory.page+1));
            this.buttonList.add(new GuiButtonExt(3,2,2,Math.min(100,20+fontRenderer.getStringWidth(lastEntryTitle)),16,"< "+lastEntryTitle));
        }

        this.pageBounds=new BoundingBox2D(2,24,this.width-4,this.height-48);
        super.initGui();

        guiInfoPage.onResize();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        MouseTracker.INSTANCE.tick();
        int wheel = MouseTracker.INSTANCE.getWheel();
        if(wheel!=0){
            handleWheel(wheel);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        guiInfoPage.updateScreen();
    }

    public void handleWheel(int wheelDelta){
        int mouseX=lastMousePos.x;
        int mouseY=lastMousePos.y;
        if (isMouseInPage(mouseX, mouseY)){
            guiInfoPage.mouseScroll(mouseX-this.pageBounds.x, mouseY-this.pageBounds.y, wheelDelta);
        }
    }

    public void close() {
        Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
    }

    public void setPonderID(String id){
        this.currentEntry = PonderRegistry.getEntries().getOrDefault(id, EmptyEntry.INSTANCE);
        this.ponderID = id;
        this.pages=this.currentEntry.getPages().size();
        this.setCurrentPageID(0);
    }
    public String getPonderID(){
        return this.ponderID;
    }


    /** Jump to specified ponder entry and record a history.
     * @param id ponder id, leave empty for not change.
     * @param page Page to jump to. value <= 0 for default.
     */
    public void jumpTo(String id, int page){
        if((id.isEmpty() || id.equals(this.ponderID))
                && (page<=0 || page==this.currentPageID+1)){
            // No jump
            return;
        }
        this.pushHistory();
        if(!id.isEmpty())
            this.setPonderID(id);
        if(page>0)
            this.setCurrentPageID(page-1);
        this.initGui();
    }

    public void pushHistory() {
        this.history.add(new HistoryEntry(this.ponderID,this.currentPageID));
    }

    protected void popHistory(){
        int size = this.history.size();
        if(size==0) return;
        HistoryEntry historyEntry = this.history.get(size-1);
        this.setPonderID(historyEntry.id);
        this.setCurrentPageID(historyEntry.page);
        this.history.remove(size-1);
        this.initGui();
    }

    protected @Nullable HistoryEntry getLastHistory(){
        int size = this.history.size();
        if(size==0) return null;
        return this.history.get(size-1);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            Vec2i mousePos = new Vec2i(mouseX, mouseY);
            mouseDelta= mousePos.sub(lastMousePos);
            lastMousePos=mousePos;
            this.drawDefaultBackground();
            // Draw title
            this.drawCenteredString(this.fontRenderer, I18n.format(this.currentEntry.title),this.width/2,5,0xFFFFFFFF);

            // Draw back button if there is history
//            if(!this.history.isEmpty()) {
//                this.drawString(this.fontRenderer,lastEntryTitle,24,5,0xFFFFFFFF);
//            }

            // Draw page number
            this.drawString(this.fontRenderer,String.format("%s/%s",this.currentPageID +1,this.pages),44,this.height-15,0xFFFFFFFF);

            // Draw page description
            RenderUtils.drawSplitString(this.fontRenderer,this.guiInfoPage.getFormattedDescription(),100,this.pageBounds.y+this.pageBounds.h+2, this.width-100,0xFFFFFFFF,true);

            // Draw current page
            this.guiInfoPage.draw(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, partialTicks);
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error drawing ponder page {}: {}",this.currentPage,e);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1)
        {
            if(!this.history.isEmpty()){
                popHistory();
                return;
            }
            close();
        }else {
            guiInfoPage.onKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        mouseDownInPage = isMouseInPage(mouseX, mouseY);
        if (mouseDownInPage && guiInfoPage.onMouseClick(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, mouseButton)) {
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(mouseDownInPage){
            guiInfoPage.onMouseRelease(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, state);
            mouseDownInPage=false;
            return;
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(mouseDownInPage && guiInfoPage.onMouseDrag(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, clickedMouseButton, timeSinceLastClick)){
            return;
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id==0){
            close();
        } else if (button.id==1) {
            this.setCurrentPageID(this.currentPageID -1);
        } else if (button.id==2) {
            this.setCurrentPageID(this.currentPageID +1);
        } else if (button.id==3) {
            this.popHistory();
        } else if (button.id==31102009){
            this.isLinkActive=false;
        }
        super.actionPerformed(button);
    }

    public void setCurrentPageID(int page){
        if(this.pages==0) return;
        this.currentPageID =page;
        while (this.currentPageID <0){
            this.currentPageID +=this.pages;
        }
        while (this.currentPageID >=this.pages){
            this.currentPageID -=this.pages;
        }
        updateCurrentPage();
    }
    public void updateCurrentPage(){
        PonderPageBase page = getPage(this.currentPageID);
        this.currentPage=page;
        this.guiInfoPage = page.getGuiPage(this);
        guiInfoPage.onResize();
        guiInfoPage.onPageRefresh();
    }

    public PonderPageBase getPage(int pageID){
        if(this.pages == 0 || pageID >= this.pages) return PonderPageBlank.INSTANCE;
        return this.currentEntry.getPages().get(pageID);
    }

    public boolean isMouseInPage(int x, int y){
        return x>=this.pageBounds.x && x<=this.pageBounds.getX2()
                && y>=this.pageBounds.y && y<=this.pageBounds.getY2();
    }

    public void openUrl(String url){
        isLinkActive=true;
        TextComponentString component = new TextComponentString("");
        component.getStyle().setClickEvent(new net.minecraft.util.text.event.ClickEvent(net.minecraft.util.text.event.ClickEvent.Action.OPEN_URL, url));
        this.handleComponentClick(component);
    }

    public boolean isLinkActive() {
        return isLinkActive;
    }

    public static class HistoryEntry{
        final String id;
        final int page;
        public HistoryEntry(String id, int page) {
            this.id = id;
            this.page = page;
        }
    }
}
