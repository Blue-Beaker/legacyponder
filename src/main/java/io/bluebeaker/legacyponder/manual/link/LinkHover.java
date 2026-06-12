package io.bluebeaker.legacyponder.manual.link;
import io.bluebeaker.legacyponder.ModZenRegister;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ModZenRegister
@ZenClass("mods.legacyponder.LinkHover")
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
