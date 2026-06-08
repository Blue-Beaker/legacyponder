package io.bluebeaker.legacyponder.manual.link;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ZenClass("mods.legacyponder.LinkHover")
@ZenRegister
public class LinkHover implements LinkBase{
    public final String tooltip;

    public LinkHover(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void onClick(GuiUnconfusion screen, int button) {
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public List<String> getTooltip(GuiUnconfusion screen) {
        if(tooltip==null || tooltip.isEmpty()){
            return Collections.emptyList();
        }else {
            return Arrays.asList(TextUtils.formatLines(tooltip));
        }
    }
}
