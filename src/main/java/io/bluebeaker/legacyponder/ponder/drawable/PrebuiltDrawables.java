package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@ZenClass("mods.legacyponder.PrebuiltDrawables")
@ZenRegister
public class PrebuiltDrawables {

    /** Build a 'catalog' that shows all registered ponder entries as links
     * @param width Width of the catalog
     * @return The built catalog
     */
    @ZenMethod
    public static DrawableGroup buildCatalog(int width){
        return buildCatalog(width,"");
    }

    /** Build a 'catalog' that shows all registered ponder entries as links, filtered by an optional regex matcher
     * @param width Width of the catalog
     * @param filter A regex string to filter what entries to be shown, don't filter when empty
     * @return The built catalog
     */
    @ZenMethod
    public static DrawableGroup buildCatalog(int width, String filter) {

        DrawableGroup drawable = new DrawableGroup();
        DrawableGrid icons = new DrawableGrid(1, 16, 16);
        DrawableGrid titles = new DrawableGrid(1, width-16, 16);
        drawable.addChild(icons, 0, 0);
        drawable.addChild(titles, 18, 3);
        Map<String, Entry> entries = PonderRegistry.getEntries();
        ArrayList<String> ids = new ArrayList<>(entries.keySet());

        if(!filter.isEmpty()){
            Pattern pattern = Pattern.compile(filter);
            ids.removeIf((id)-> !pattern.matcher(id).matches());
        }

        Collections.sort(ids);
        for (String key : ids) {
            Entry entry = entries.get(key);
            List<ItemStack> items = new ArrayList<>();
            entry.getItems().forEach(items::addAll);

            icons.addChild(new DrawableItem(items).setLinkPonder(key));
            titles.addChild(DrawableBuilder.buildText(I18n.format(entry.title), 0xFFFFFFFF).setLinkPonder(key));
        }
        return drawable;
    }
    public static DrawableGroup buildSummary(String entryId, int width, int height){
        DrawableGroup drawable = new DrawableGroup();
        Entry entry = PonderRegistry.getEntries().get(entryId);

        int columns = width / 16;
        DrawableGrid items = new DrawableGrid(columns,16,16);
        for (List<ItemStack> item : entry.getItems()) {
            items.addChild(new DrawableItem(item));
        }
        drawable.addChild(items,items.getXMax()/2 - (Math.min(columns,items.children.size())*16)/2,0);
        drawable.addChild(DrawableBuilder.formattedText(entry.summary, 0xFFFFFFFF).setMaxWidth(width).setAlign(0.5F),width/2,items.getHeight());

        return drawable;
    }
}
