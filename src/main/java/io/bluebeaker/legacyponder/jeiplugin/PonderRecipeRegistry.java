package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.Entry;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

public class PonderRecipeRegistry {

    public static List<PonderRecipeWrapper> getRecipes(final IJeiHelpers jeiHelpers){
        List<PonderRecipeWrapper> recipes = new ArrayList<>();
        for(String id: PonderRegistry.getEntries().keySet()){
            Entry entry = PonderRegistry.getEntries().get(id);
            addWrapper(jeiHelpers, id, entry.summary, entry, recipes);
        }
        return recipes;
    }

    private static void addWrapper(IJeiHelpers jeiHelpers, String key, String summary, Entry internal, List<PonderRecipeWrapper> recipes) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        List<String> lines = fontRenderer.listFormattedStringToWidth(I18n.format(summary), PonderRecipeCategory.recipeWidth-8);
        // Compute max lines
        int maxHeight = PonderRecipeCategory.recipeHeight - 2 - 18 - ((internal.getFluids().size() + internal.getItems().size() - 1) / 9 * 18);
        if(!internal.getPages().isEmpty()) maxHeight=maxHeight-22;
        // Keep at least 1 line of text to prevent infinite loop
        int maxLines = Math.max(1,maxHeight / fontRenderer.FONT_HEIGHT);

        for (int from = 0; from < lines.size(); from=from+maxLines) {
            int to = Math.min(from + maxLines, lines.size());
            recipes.add(new PonderRecipeWrapper(jeiHelpers.getGuiHelper(), key, internal.getItems(), internal.getFluids(),lines.subList(from, to)));
        }
    }
}
