package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

public class UnconfusionRecipeRegistry {

    public static List<UnconfusionRecipeWrapper> getRecipes(final IJeiHelpers jeiHelpers){
        List<UnconfusionRecipeWrapper> recipes = new ArrayList<>();
        for(String id: ManualRegistry.getEntries().keySet()){
            Entry entry = ManualRegistry.getEntries().get(id);
            addWrapper(jeiHelpers, id, entry.title, entry.summary, entry, recipes);
        }
        return recipes;
    }

    private static void addWrapper(IJeiHelpers jeiHelpers, String key, String title, String summary, Entry internal, List<UnconfusionRecipeWrapper> recipes) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

        List<String> lines = RenderUtils.listFormattedStringToWidth(fr,I18n.format(title)+"\n"+I18n.format(summary), UnconfusionRecipeCategory.recipeWidth-8);
        // Compute max lines
        int maxHeight = UnconfusionRecipeCategory.recipeHeight - 2 - 18 - ((internal.getFluids().size() + internal.getItems().size() - 1) / 9 * 18);
        if(!internal.getPages().isEmpty()) maxHeight=maxHeight-22;
        // Keep at least 1 line of text to prevent infinite loop
        int maxLines = Math.max(1,maxHeight / fr.FONT_HEIGHT);

        for (int from = 0; from < lines.size(); from=from+maxLines) {
            int to = Math.min(from + maxLines, lines.size());
            recipes.add(new UnconfusionRecipeWrapper(jeiHelpers.getGuiHelper(), key, internal.getItems(), internal.getFluids(),lines.subList(from, to)));
        }
    }
}
