package io.bluebeaker.legacyponder.jeiplugin;

import mezz.jei.api.IRecipesGui;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.config.KeyBindings;
import mezz.jei.gui.Focus;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class JEIUtils {

    public static void showJEICategory(String category){
        showJEICategories(Collections.singletonList(category));
    }

    public static void showJEICategories(List<String> jeiCategories){
        if (UnconfusionJEIPlugin.runtime != null) {
            UnconfusionJEIPlugin.runtime.getRecipesGui().showCategories(jeiCategories);
        }
    }

    @Nullable
    public static IRecipeCategory<?> getCategory(String category){
        if (UnconfusionJEIPlugin.runtime != null) {
            return UnconfusionJEIPlugin.runtime.getRecipeRegistry().getRecipeCategory(category);
        }
        return null;
    }

    public static <V> void handleJEIAction(V ingredient, JEIAction action) {
        if(UnconfusionJEIPlugin.runtime==null || ingredient==null) return;
        IRecipesGui recipesGui = UnconfusionJEIPlugin.runtime.getRecipesGui();
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
