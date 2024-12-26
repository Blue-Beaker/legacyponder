package io.bluebeaker.legacyponder.ponder;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenPonder extends GuiScreen {
    private PonderEntry activePonder = null;

    public GuiScreenPonder(){
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1,1,1,20,20,"prev"));
        this.buttonList.add(new GuiButton(2,22,1,20,20,"next"));
        super.initGui();
    }

    public void setPonderID(String id){
        PonderEntry ponderEntry = PonderRegistry.getEntries().get(id);
        this.activePonder=ponderEntry;
    }

}
