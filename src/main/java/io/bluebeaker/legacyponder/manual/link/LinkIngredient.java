package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;

public interface LinkIngredient<V> extends LinkBase {
    @Override
    default void onClick(GuiUnconfusion screen, int button) {
        if(button==0 || button==1){
            JEIUtils.JEIAction action = button==0 ? JEIUtils.JEIAction.RECIPE : JEIUtils.JEIAction.USAGE;
            JEIUtils.handleJEIAction(getIngredient(), action);
            screen.releaseMouse();
        }
    }

    @Override
    default boolean onKeyDown(GuiUnconfusion screen, int keyCode) {
        JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
        if(action!= JEIUtils.JEIAction.NONE){
            JEIUtils.handleJEIAction(getIngredient(), action);
            return true;
        }
        return false;
    }

    V getIngredient();
}
