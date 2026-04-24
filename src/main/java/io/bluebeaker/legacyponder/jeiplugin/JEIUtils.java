package io.bluebeaker.legacyponder.jeiplugin;

import mezz.jei.api.IRecipesGui;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.config.KeyBindings;
import mezz.jei.gui.Focus;

public class JEIUtils {

    public static <V> void handleJEIAction(V ingredient, JEIAction action) {
        if(PonderJEIPlugin.runtime==null || ingredient==null) return;
        IRecipesGui recipesGui = PonderJEIPlugin.runtime.getRecipesGui();
        if(action== JEIAction.RECIPE) {
            recipesGui.show(new Focus<>(IFocus.Mode.OUTPUT, ingredient));
        } else if(action== JEIAction.USAGE) {
            recipesGui.show(new Focus<>(IFocus.Mode.INPUT, ingredient));
        }
    }

    public enum JEIAction {
        RECIPE,
        USAGE,
        NONE
    }

    public static JEIAction getJEIAction(int eventKey){
        if(KeyBindings.showRecipe.isActiveAndMatches(eventKey)) {
            return JEIAction.RECIPE;
        }
        if(KeyBindings.showUses.isActiveAndMatches(eventKey)) {
            return JEIAction.USAGE;
        }
        return JEIAction.NONE;
    }
}
