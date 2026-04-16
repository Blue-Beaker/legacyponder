package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;

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
    public void onClick(GuiScreenPonder screen, int button) {
        if(button==0){
            TextComponentString component = new TextComponentString("");
            component.getStyle().setClickEvent(new net.minecraft.util.text.event.ClickEvent(net.minecraft.util.text.event.ClickEvent.Action.OPEN_URL, url));
            screen.handleComponentClick(component);
        }
    }

    @Override
    public List<String> getTooltip(GuiScreenPonder screen) {
        if(tooltip==null){
            return Collections.singletonList(url);
        }else {
            return Arrays.asList(TextUtils.formatLines(tooltip));
        }
    }
}
