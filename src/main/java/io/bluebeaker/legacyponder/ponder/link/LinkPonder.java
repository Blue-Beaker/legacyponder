package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.client.Minecraft;

import java.util.Collections;
import java.util.List;

public class LinkPonder implements LinkBase{

    public final String id;

    public LinkPonder(String id) {
        this.id = id;
    }

    @Override
    public void onClick(GuiScreenPonder screen, int button) {
        if(button==0){
            GuiScreenPonder guiScreenIn = new GuiScreenPonder();
            guiScreenIn.setPonderID(this.id);
            Minecraft.getMinecraft().displayGuiScreen(guiScreenIn);
        }
    }

    @Override
    public List<String> getTooltip(GuiScreenPonder screen) {
        return Collections.singletonList(id);
    }
}
