package io.bluebeaker.legacyponder.manual;

import io.bluebeaker.legacyponder.Keybinds;
import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.UIConfig;
import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.demo.DemoEntries;
import io.bluebeaker.legacyponder.manual.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.manual.gui.MouseTracker;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import io.bluebeaker.legacyponder.manual.page.PageBlank;
import io.bluebeaker.legacyponder.manual.page.PageSummary;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiConfig;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class GuiUnconfusion extends GuiScreen {
    @Nonnull
    protected Entry currentEntry = EmptyEntry.INSTANCE;
    protected int currentPageID = 0;
    @Nonnull
    protected PageBase currentPage = PageBlank.INSTANCE;
    protected int pages = 0;
    @Nonnull
    protected GuiInfoPage<?> guiInfoPage = PageBlank.INSTANCE.getGuiPage(this);

    public void setLastScreen(GuiScreen lastScreen) {
        if (!lastScreenSet)
            this.lastScreen = lastScreen;
        lastScreenSet=true;
    }

    protected boolean lastScreenSet = false;
    protected GuiScreen lastScreen;
    protected String lastEntryTitle = "";
    protected String entryID = "";

    protected final HistoryTracker historyTracker = HistoryTracker.get();

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

    public GuiUnconfusion(){
        StructureRenderManager.viewPos.resetAll();
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonExt(ButtonID.CLOSE,this.width-20,0,20,20,"x"));
        this.buttonList.add(new GuiButtonExt(ButtonID.PREV,2,this.height-22,20,20,"<"));
        this.buttonList.add(new GuiButtonExt(ButtonID.NEXT,23,this.height-22,20,20,">"));
        this.buttonList.add(new GuiButtonExt(ButtonID.HELP,this.width-40,0,20,20,"?"));
        this.buttonList.add(new GuiButtonExt(ButtonID.SETTINGS,this.width-60,0,20,20,"#"));

        GuiButtonExt back = new GuiButtonExt(ButtonID.BACK, 2, 2, 16, 16, "<");
        this.buttonList.add(back);
        back.enabled=historyTracker.currentIndex>0;
        GuiButtonExt forward = new GuiButtonExt(ButtonID.FORWARD, 18, 2, 16, 16, ">");
        this.buttonList.add(forward);
        forward.enabled=historyTracker.currentIndex<historyTracker.history.size()-1;

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
        }else {
            if(wheelDelta>0){
                gotoPage(currentPageID-1);
            }else {
                gotoPage(currentPageID+1);
            }
        }
    }

    public void close() {
        Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
    }

    public void setEntryID(String id){
        this.currentEntry = ManualRegistry.getEntries().getOrDefault(id, EmptyEntry.INSTANCE);
        this.entryID = id;
        this.pages=this.currentEntry.getPages().size();
        this.setCurrentPageID(1);
    }
    public String getEntryID(){
        return this.entryID;
    }


    /** Jump to specified manual entry and record a history.
     * @param id manual id, leave empty for not change.
     * @param page Page to jump to. value <= 0 for default.
     */
    public void jumpTo(String id, int page){
        if((id.isEmpty() || id.equals(this.entryID))
                && (page<0 || page==this.currentPageID)){
            // No jump
            return;
        }
        if(!id.isEmpty())
            this.setEntryID(id);
        if(page>0)
            this.setCurrentPageID(Math.min(page,pages));
        this.pushHistory();
        this.initGui();
    }
    public void jumpTo(String id){
        jumpTo(id,-1);
    }

    public void loadHistory(){
        if(historyTracker.history.isEmpty()){
            jumpTo(DemoEntries.HELP_ID);
            return;
        }
        gotoHistory(historyTracker.currentIndex);
    }

    public void pushHistory() {
        historyTracker.record(entryID,currentPageID);
    }

    protected void goBack(){
        gotoHistory(historyTracker.currentIndex-1);
    }
    protected void goForward(){
        gotoHistory(historyTracker.currentIndex+1);
    }
    protected void gotoHistory(int index){
        HistoryTracker.HistoryEntry historyEntry = historyTracker.gotoIndex(index);
        if(historyEntry==null) return;
        this.setEntryID(historyEntry.id);
        this.setCurrentPageID(historyEntry.page);
        this.initGui();
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
            this.drawString(this.fontRenderer,String.format("%s/%s",this.currentPageID,this.pages),44,this.height-15,0xFFFFFFFF);

            // Draw page description
            RenderUtils.drawSplitString(this.fontRenderer,this.guiInfoPage.getFormattedDescription(),100,this.pageBounds.y+this.pageBounds.h+2, this.width-100,0xFFFFFFFF,true);

            // Draw current page
            this.guiInfoPage.draw(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, partialTicks);
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error drawing manual page {}: {}",this.currentPage,e);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawButtonTooltip(mouseX, mouseY);
    }

    private void drawButtonTooltip(int mouseX, int mouseY) {
        for (GuiButton button : this.buttonList) {
            if(button.isMouseOver()){
                GlStateManager.disableDepth();
                if (button.id == ButtonID.SETTINGS) {
                    this.drawHoveringText(I18n.format("button.legacyponder.settings"), mouseX, mouseY);
                } else if (button.id == ButtonID.HELP) {
                    this.drawHoveringText(I18n.format("button.legacyponder.help"), mouseX, mouseY);
                } else if (button.id == ButtonID.BACK || button.id == ButtonID.FORWARD) {
                    List<String> historyTip = historyTracker.getTooltipStrings();
                    this.drawHoveringText(historyTip, mouseX, mouseY);
                }
                GlStateManager.disableLighting();
                return;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        try{
            if (keyCode == Keyboard.KEY_ESCAPE) {
                if (UIConfig.back_then_close && this.historyTracker.currentIndex >0) {
                    goBack();
                    return;
                }
                close();
            }else if (handleKeybinds(keyCode)){
                return;
            } else {
                guiInfoPage.onKeyTyped(typedChar, keyCode);
            }
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error handling key {} on manual page {}: {}",keyCode,this.currentPage,e);
        }
    }

    protected boolean handleKeybinds(int keyCode){
        if (Keybinds.prevPage.isActiveAndMatches(keyCode)) {
            gotoPage(currentPageID - 1);
            return true;
        } else if (Keybinds.nextPage.isActiveAndMatches(keyCode)) {
            gotoPage(currentPageID + 1);
            return true;
        } else if (Keybinds.histBack.isActiveAndMatches(keyCode)) {
            gotoHistory(historyTracker.currentIndex-1);
            return true;
        } else if (Keybinds.histForward.isActiveAndMatches(keyCode)) {
            gotoHistory(historyTracker.currentIndex+1);
            return true;
        }
        else if (Keybinds.histBack.isActiveAndMatches(keyCode-100)) {
            gotoHistory(historyTracker.currentIndex-1);
            return true;
        } else if (Keybinds.histForward.isActiveAndMatches(keyCode-100)) {
            gotoHistory(historyTracker.currentIndex+1);
            return true;
        }
        return false;
    }

    private void gotoPage(int pageID) {
        setCurrentPageID(pageID);
        historyTracker.updateCurrentHistory(this.entryID,this.currentPageID);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (handleKeybinds(mouseButton-100)){
            return;
        }
        try {
            mouseDownInPage = isMouseInPage(mouseX, mouseY);
            if (mouseDownInPage && guiInfoPage.onMouseClick(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, mouseButton)) {
                return;
            }
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error handling mouse click on manual page {}: {}",this.currentPage,e);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        try {
            if(mouseDownInPage){
                guiInfoPage.onMouseRelease(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, state);
                mouseDownInPage=false;
                return;
            }
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error handling mouse releasing on manual page {}: {}",this.currentPage,e);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        try{
            if(mouseDownInPage && guiInfoPage.onMouseDrag(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, clickedMouseButton, timeSinceLastClick)){
                return;
            }
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error handling mouse dragging on manual page {}: {}",this.currentPage,e);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == ButtonID.CLOSE) {
            close();
        } else if (button.id == ButtonID.PREV) {
            this.gotoPage(this.currentPageID - 1);
        } else if (button.id == ButtonID.NEXT) {
            this.gotoPage(this.currentPageID + 1);
        } else if (button.id == ButtonID.BACK) { //Back
            this.goBack();
        } else if (button.id == ButtonID.FORWARD) { //Back
            this.goForward();
        } else if (button.id == ButtonID.HELP) { //Help
            this.jumpTo(DemoEntries.HELP_ID, -1);
        } else if (button.id == ButtonID.SETTINGS) { //Config
            Minecraft.getMinecraft()
                    .displayGuiScreen(
                            new GuiConfig(this, LegacyPonder.MODID, false, false, I18n.format("config.legacyponder.ui"), UIConfig.class));
        } else if (button.id == ButtonID.LINK_CONFIRMED) {
            this.isLinkActive = false;
        }
        super.actionPerformed(button);
    }

    public void setCurrentPageID(int page){
        if(this.pages==0) page=0;
        this.currentPageID =page;
        while (this.currentPageID <0){
            this.currentPageID = this.currentPageID + (this.pages + 1);
        }
        while (this.currentPageID > this.pages){
            this.currentPageID = this.currentPageID - (this.pages + 1);
        }
        updateCurrentPage();
    }
    public void updateCurrentPage(){
        PageBase page = getPage(this.currentPageID);
        this.currentPage=page;
        this.guiInfoPage = page.getGuiPage(this);
        guiInfoPage.onResize();
        guiInfoPage.onPageRefresh();
    }

    public PageBase getPage(int pageID){
        if(pageID==0){
            return new PageSummary(entryID);
        }
        if(this.pages == 0 || pageID > this.pages) return PageBlank.INSTANCE;
        return this.currentEntry.getPages().get(pageID-1);
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

    public static class ButtonID{
        public static final int CLOSE = 0;
        public static final int PREV = 1;
        public static final int NEXT = 2;
        public static final int BACK = 3;
        public static final int HELP = 4;
        public static final int SETTINGS = 5;
        public static final int LINK_CONFIRMED = 31102009;
        public static final int FORWARD = 6;
    }
}
