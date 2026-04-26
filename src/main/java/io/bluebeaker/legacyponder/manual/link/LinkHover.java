package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;

import java.util.Arrays;
import java.util.List;

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
        return Arrays.asList(TextUtils.formatLines(tooltip));
    }
}
