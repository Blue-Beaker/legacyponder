package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.item.ItemStack;

import java.util.List;

public class LinkItem implements LinkBase{

    public final ItemStack item;

    public LinkItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void onClick(GuiScreenPonder screen, int button) {
        if(button==0 || button==1){
            JEIUtils.JEIAction action = button==0 ? JEIUtils.JEIAction.RECIPE : JEIUtils.JEIAction.USAGE;
            JEIUtils.handleJEIAction(item, action);
        }
    }

    @Override
    public boolean onKeyDown(GuiScreenPonder screen, int keyCode) {
        JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
        if(action!= JEIUtils.JEIAction.NONE){
            JEIUtils.handleJEIAction(item, action);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getTooltip(GuiScreenPonder screen) {
        return screen.getItemToolTip(item);
    }
}
