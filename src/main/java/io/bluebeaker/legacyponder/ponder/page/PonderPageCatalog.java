package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.Entry;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableGrid;
import net.minecraft.client.resources.I18n;

import java.util.Map;
import java.util.stream.Collectors;

public class PonderPageCatalog extends PonderPageDrawable{
    public PonderPageCatalog() {
        super(new Supplier());
    }
    public static class Supplier implements IDrawableSupplier {
        @Override
        public DrawableBase process(int width, int height) {
            DrawableGrid drawable = new DrawableGrid(1,width,10);
            Map<String, Entry> entries = PonderRegistry.getEntries();
            for (String key : entries.keySet().stream().sorted().collect(Collectors.toList())) {
                drawable.addChild(DrawableBuilder.buildText(I18n.format(entries.get(key).title),0xFFFFFFFF).setLinkPonder(key));
            }
            return drawable;
        }
    }
}
