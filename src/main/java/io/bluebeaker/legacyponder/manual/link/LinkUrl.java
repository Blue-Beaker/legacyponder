package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LinkUrl implements LinkBase{
    @Nullable
    public final String tooltip;
    public final String url;

    public LinkUrl(String url,@Nullable String tooltip) {
        this.url = url;
        this.tooltip = tooltip;
    }

    @Override
    public void onClick(GuiUnconfusion screen, int button) {
        if(button==0){
            screen.openUrl(url);
            screen.releaseMouse();
        }
    }

    @Override
    public List<String> getTooltip(GuiUnconfusion screen) {
        if(tooltip==null){
            return Collections.singletonList(url);
        }else {
            return Arrays.asList(TextUtils.formatLines(tooltip));
        }
    }
}
