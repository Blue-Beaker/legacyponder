package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PonderRecipeRegistry {

    public static List<PonderRecipeWrapper> getRecipes(final IJeiHelpers jeiHelpers){
        List<PonderRecipeWrapper> recipes = new ArrayList<>();
        for(Map.Entry<String, PonderEntry> entry: PonderRegistry.getEntries().entrySet()){
            PonderEntry internal = entry.getValue();
            String summary = I18n.format(internal.summary);

            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            List<String> lines = fontRenderer.listFormattedStringToWidth(summary, PonderRecipeCategory.recipeWidth-8);

            int maxHeight = PonderRecipeCategory.recipeHeight - 2 - 18 - ((internal.getFluids().size() + internal.getItems().size() - 1) / 9 * 18);
            if(!internal.getPages().isEmpty()) maxHeight=maxHeight-22;
            int maxLines = maxHeight / fontRenderer.FONT_HEIGHT;

            for (int from = 0; from < lines.size(); from=from+maxLines) {
                int to = Math.min(from + maxLines, lines.size());
                recipes.add(new PonderRecipeWrapper(jeiHelpers.getGuiHelper(),entry.getKey(),internal.getItems(),internal.getFluids(),lines.subList(from, to)));
            }

        }
        return recipes;
    }
}
