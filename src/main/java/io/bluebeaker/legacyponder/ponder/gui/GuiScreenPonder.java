package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public class GuiScreenPonder extends GuiScreen {
    protected PonderEntry currentPonder = null;
    protected int currentPage = 0;
    protected int pages = 0;
    protected GuiScreen lastScreen;

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

    public void close() {
        Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
    }

    public void setPonderID(String id){
        PonderEntry ponderEntry = PonderRegistry.getEntries().get(id);
        this.currentPonder =ponderEntry;
        this.pages=this.currentPonder.getPages().size();
        this.setCurrentPage(0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if(this.currentPonder !=null){
            // Draw title
            this.drawCenteredString(this.fontRenderer,this.currentPonder.id,this.width/2,10,0xFFFFFFFF);
            // Draw current page
            if(this.pages>0){
                this.currentPonder.getPages().get(this.currentPage).draw(this,mouseX,mouseY,partialTicks);
            }
            // Draw page number
            this.drawString(this.fontRenderer,String.format("%s/%s",this.currentPage+1,this.pages),44,this.height-11,0xFFFFFFFF);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1)
        {
            close();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id==0){
            close();
        } else if (button.id==1) {
            this.setCurrentPage(this.currentPage-1);
        } else if (button.id==2) {
            this.setCurrentPage(this.currentPage+1);
        }
        super.actionPerformed(button);
    }

    protected void setCurrentPage(int page){
        if(this.pages==0) return;
        this.currentPage=page;
        while (this.currentPage<0){
            this.currentPage+=this.pages;
        }
        while (this.currentPage>=this.pages){
            this.currentPage-=this.pages;
        }
    }
}
