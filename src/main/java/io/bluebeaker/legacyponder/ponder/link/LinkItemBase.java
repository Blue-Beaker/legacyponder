package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface LinkItemBase extends LinkBase {
    @Override
    default void onClick(GuiScreenPonder screen, int button) {
        if(button==0 || button==1){
            JEIUtils.JEIAction action = button==0 ? JEIUtils.JEIAction.RECIPE : JEIUtils.JEIAction.USAGE;
            JEIUtils.handleJEIAction(getItem(), action);
        }
    }

    @Override
    default boolean onKeyDown(GuiScreenPonder screen, int keyCode) {
        JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
        if(action!= JEIUtils.JEIAction.NONE){
            JEIUtils.handleJEIAction(getItem(), action);
            return true;
        }
        return false;
    }

    @Override
    default List<String> getTooltip(GuiScreenPonder screen) {
        return screen.getItemToolTip(getItem());
    }

    ItemStack getItem();
}
