package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public class GuiScreenPonder extends GuiScreen {
    private PonderEntry currentPonder = null;
    private int currentPage = 0;
    public GuiScreenPonder(){
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonExt(0,this.width-20,0,20,20,"x"));

        this.buttonList.add(new GuiButtonExt(1,2,this.height-22,20,20,"<"));

        this.buttonList.add(new GuiButtonExt(2,23,this.height-22,20,20,">"));

        super.initGui();
    }

    public void setPonderID(String id){
        PonderEntry ponderEntry = PonderRegistry.getEntries().get(id);
        this.currentPonder =ponderEntry;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if(this.currentPonder !=null){
            this.drawString(this.fontRenderer,this.currentPonder.id,100,100,0xFFFFFFFF);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id==0){
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }
}
