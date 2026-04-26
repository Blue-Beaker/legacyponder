package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkPonder implements LinkBase{

    public final String id;
    public final int page;

    public LinkPonder(String id){
        this(id,-1);
    }
    public LinkPonder(String id, int page) {
        this.id = id;
        this.page = page;
    }

    @Override
    public void onClick(GuiUnconfusion screen, int button) {
        if(button==0){
            screen.jumpTo(this.id, this.page);
        }
    }

    @Override
    public List<String> getTooltip(GuiUnconfusion screen) {
        if(id.isEmpty()){
            // If id unspecified, show only page
            return Collections.singletonList("-> "+I18n.format("link.legacyponder.page",this.page));
        }

        Entry entry = ManualRegistry.getEntries().get(id);
        if(entry==null) return Collections.singletonList("Unknown manual entry: "+id);
        List<String> tooltip = new ArrayList<>();

        String title = I18n.format(entry.title);
        tooltip.add(this.page<0?title:title+" -> "+I18n.format("link.legacyponder.page",this.page));

        if(entry.summary.isEmpty()) return tooltip;
        tooltip.add("");
        Collections.addAll(tooltip, TextUtils.formatLines(entry.summary));
        return tooltip;
    }
}
