package io.bluebeaker.legacyponder.demo;

import crafttweaker.mc1120.brackets.BracketHandlerOre;
import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.crafttweaker.IPonderEntry;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import io.bluebeaker.legacyponder.ponder.page.PageBase;

public class DemoEntries {

    public static final String INTERNAL_DEMO_1 = "internal:demo_1";

    public static void addDemoIfNeeded(){
        if(CommonConfig.demo && !demoAdded){
            demoAdded=addDemoEntry();
        } else if (!CommonConfig.demo && demoAdded) {
            PonderRegistry.getEntries().remove(INTERNAL_DEMO_1);
            demoAdded=false;
        }
    }
    private static boolean demoAdded = false;
    public static boolean addDemoEntry(){
        if(PonderRegistry.getEntries().containsKey(INTERNAL_DEMO_1)) return false;

        IPonderEntry entry = IPonderEntry.createPonderEntry("ponderentry.demo_1.title","ponderentry.demo_1.desc");

        entry.addIngredient(BracketHandlerOre.getOre("ingotGold"));
        entry.addPage(PageBase.fromDrawable(new IDrawableSupplier() {
            @Override
            public DrawableBase process(int width, int height) {
                return DrawableBuilder.formattedText("ponderentry.demo_1.page1.text",0xFFFFFFFF).setAlign(0).setSize(width, height);
            }
        }));
        entry.addPage(PageBase.fromStructure("legacyponder:tree").setDescription("A tree, with some blocks around it."));
        entry.addPage(PageBase.catalogPage());

        PonderRegistry.add(INTERNAL_DEMO_1, entry);
        return true;
    }
}
