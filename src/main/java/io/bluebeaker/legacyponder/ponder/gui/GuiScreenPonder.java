package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nullable;
import java.io.IOException;

public class GuiScreenPonder extends GuiScreen {
    protected PonderEntry currentPonder = null;
    protected int currentPageID = 0;
    @Nullable
    protected PonderPageBase currentPage = null;
    protected int pages = 0;
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
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonExt(0,this.width-20,0,20,20,"x"));
        this.buttonList.add(new GuiButtonExt(1,2,this.height-22,20,20,"<"));
        this.buttonList.add(new GuiButtonExt(2,23,this.height-22,20,20,">"));
        this.pageBounds=new BoundingBox2D(5,20,this.width-10,this.height-50);
        super.initGui();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        MouseTracker.INSTANCE.tick();
    }

    public void close() {
        Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
    }

    public void setPonderID(String id){
        PonderEntry ponderEntry = PonderRegistry.getEntries().get(id);
        this.currentPonder =ponderEntry;
        this.pages=this.currentPonder.getPages().size();
        this.setCurrentPageID(0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            Vec2i mousePos = new Vec2i(mouseX, mouseY);
            mouseDelta= mousePos.sub(lastMousePos);
            lastMousePos=mousePos;
            this.drawDefaultBackground();
            if(this.currentPonder !=null){
                // Draw title
                this.drawCenteredString(this.fontRenderer,this.currentPonder.id,this.width/2,10,0xFFFFFFFF);
                // Draw current page
                if(this.pages>0){
                    this.currentPonder.getPages().get(this.currentPageID).draw(this,mouseX-this.pageBounds.x,mouseY-this.pageBounds.y,partialTicks);
                }
                // Draw page number
                this.drawString(this.fontRenderer,String.format("%s/%s",this.currentPageID +1,this.pages),44,this.height-11,0xFFFFFFFF);
            }
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
        if (currentPage != null && mouseDownInPage && currentPage.click(this,mouseX-this.pageBounds.x, mouseY-this.pageBounds.y, mouseButton)) {
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(currentPage!=null && mouseDownInPage && currentPage.mouseReleased(this,mouseX-this.pageBounds.x,mouseY-this.pageBounds.y,state)){
            mouseDownInPage=false;
            return;
        }
        mouseDownInPage=false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(currentPage!=null && mouseDownInPage && currentPage.mouseDrag(this,mouseX-this.pageBounds.x,mouseY-this.pageBounds.y,clickedMouseButton,timeSinceLastClick)){
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
        if (page != null) {
            page.onSelected();
        }
    }

    @Nullable
    public PonderPageBase getPage(int pageID){
        if(this.currentPonder==null || this.pages==0 || pageID>=this.pages) return null;
        return this.currentPonder.getPages().get(pageID);
    }

    public boolean isMouseInPage(int x, int y){
        return x>=this.pageBounds.x && x<=this.pageBounds.x+this.pageBounds.w
                && y>=this.pageBounds.y && y<=this.pageBounds.y+this.pageBounds.h;
    }
}
