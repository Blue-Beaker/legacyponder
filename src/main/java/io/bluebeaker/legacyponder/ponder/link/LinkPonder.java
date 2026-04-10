package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.Entry;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
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
        Entry entry = PonderRegistry.getEntries().get(id);
        if(entry==null) return Collections.singletonList("Unknown ponder entry: "+id);
        List<String> tooltip = new ArrayList<>();
        tooltip.add(I18n.format(entry.title));
        if(entry.summary.isEmpty()) return tooltip;
        tooltip.add("");
        Collections.addAll(tooltip, I18n.format(entry.summary).split("\n"));
        return tooltip;
    }
}
