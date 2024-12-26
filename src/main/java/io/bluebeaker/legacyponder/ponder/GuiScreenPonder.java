package io.bluebeaker.legacyponder.ponder;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenPonder extends GuiScreen {
    public GuiScreenPonder(){
        this.buttonList.add(new GuiButton(1,1,1,20,20,"prev"));
        this.buttonList.add(new GuiButton(2,22,1,20,20,"next"));
    }
}
