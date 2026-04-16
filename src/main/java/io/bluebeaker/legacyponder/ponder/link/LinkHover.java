package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.resources.I18n;

import java.util.Arrays;
import java.util.List;

public class LinkHover implements LinkBase{
    public final String tooltip;

    public LinkHover(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void onClick(GuiScreenPonder screen, int button) {
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public List<String> getTooltip(GuiScreenPonder screen) {
        return Arrays.asList(TextUtils.formatLines(tooltip));
    }
}
