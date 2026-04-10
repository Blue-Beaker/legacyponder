package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class LinkUrl implements LinkBase{
    @Nullable
    public final String name;
    public final String url;

    public LinkUrl(String url,@Nullable String name) {
        this.url = url;
        this.name = name;
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
        return Collections.singletonList(name==null?url:name);
    }
}
