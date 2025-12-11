package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.EmptyEntry;
import io.bluebeaker.legacyponder.ponder.Entry;
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
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nonnull;
import java.io.IOException;

public class GuiScreenPonder extends GuiScreen {
    @Nonnull
    protected Entry currentEntry = EmptyEntry.INSTANCE;
    protected int currentPageID = 0;
    @Nonnull
    protected PonderPageBase currentPage = PonderPageBlank.INSTANCE;
    protected int pages = 0;
    @Nonnull
    protected GuiInfoPage<?> guiInfoPage = PonderPageBlank.INSTANCE.getGuiPage(this);
    protected GuiScreen lastScreen;
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
        lastScreen=Minecraft.getMinecraft().currentScreen;
        StructureRenderManager.viewPos.resetAll();
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonExt(0,this.width-20,0,20,20,"x"));
        this.buttonList.add(new GuiButtonExt(1,2,this.height-22,20,20,"<"));
        this.buttonList.add(new GuiButtonExt(2,23,this.height-22,20,20,">"));
        this.pageBounds=new BoundingBox2D(5,20,this.width-10,this.height-50);
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
        mouseDownInPage = isMouseInPage(mouseX, mouseY);
        if (mouseDownInPage){
            guiInfoPage.mouseScroll(mouseX-this.pageBounds.x, mouseY-this.pageBounds.y, wheelDelta);
        }
    }

    public void close() {
        Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
    }

    public void setPonderID(String id){
        this.currentEntry = PonderRegistry.getEntries().getOrDefault(id, EmptyEntry.INSTANCE);
        this.pages=this.currentEntry.getPages().size();
        this.setCurrentPageID(0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            Vec2i mousePos = new Vec2i(mouseX, mouseY);
            mouseDelta= mousePos.sub(lastMousePos);
            lastMousePos=mousePos;
            this.drawDefaultBackground();
            // Draw title
            this.drawCenteredString(this.fontRenderer, I18n.format(this.currentEntry.title),this.width/2,10,0xFFFFFFFF);
            // Draw current page
            this.guiInfoPage.draw(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, partialTicks);
            // Draw page number
            this.drawString(this.fontRenderer,String.format("%s/%s",this.currentPageID +1,this.pages),44,this.height-20,0xFFFFFFFF);

            // Draw page description
            RenderUtils.drawSplitString(this.fontRenderer,this.guiInfoPage.getFormattedDescription(),100,this.height-20, this.width-100,0xFFFFFFFF,true);
        } catch (Exception e) {
            LegacyPonder.getLogger().error("Error drawing ponder page {}: {}",this.currentPage,e);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1)
        {
            close();
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
        if(mouseDownInPage && guiInfoPage.onMouseRelease(mouseX - this.pageBounds.x, mouseY - this.pageBounds.y, state)){
            mouseDownInPage=false;
            return;
        }
        mouseDownInPage=false;
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
        }
        super.actionPerformed(button);
    }

    protected void setCurrentPageID(int page){
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
        guiInfoPage.onPageRefresh();
    }

    public PonderPageBase getPage(int pageID){
        if(this.pages == 0 || pageID >= this.pages) return PonderPageBlank.INSTANCE;
        return this.currentEntry.getPages().get(pageID);
    }

    public boolean isMouseInPage(int x, int y){
        return x>=this.pageBounds.x && x<=this.pageBounds.x+this.pageBounds.w
                && y>=this.pageBounds.y && y<=this.pageBounds.y+this.pageBounds.h;
    }
}
