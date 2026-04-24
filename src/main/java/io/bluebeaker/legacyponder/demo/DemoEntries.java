package io.bluebeaker.legacyponder.demo;

import crafttweaker.mc1120.brackets.BracketHandlerOre;
import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.crafttweaker.IPonderEntry;
import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.ponder.page.PageBase;

public class DemoEntries {

    public static final String HELP_ID = "internal:help";
    public static final String INTERNAL_DEMO_ID = "internal:demo";

    public static void addDemoIfNeeded(){
        if(CommonConfig.demo){
            demoAdded=addDemoEntry();
        } else if (demoAdded) {
            PonderRegistry.getEntries().remove(INTERNAL_DEMO_ID);
            demoAdded=false;
        }
        addHelpEntry();
    }
    private static boolean demoAdded = false;
    public static boolean addDemoEntry(){
        if(PonderRegistry.getEntries().containsKey(INTERNAL_DEMO_ID)) return false;

        IPonderEntry entry = IPonderEntry.createPonderEntry("unconfusion.entry.demo_1.title","unconfusion.entry.demo_1.desc");

        entry.addIngredient(BracketHandlerOre.getOre("ingotGold"));
        entry.addPage(PageBase.fromDrawable(new IDrawableSupplier() {
            @Override
            public DrawableBase process(int width, int height) {
                return DrawableBuilder.formattedText("unconfusion.entry.demo_1.page1.text",0xFFFFFFFF).setAlign(0).setSize(width, height);
            }
        }));
        entry.addPage(PageBase.fromStructure("legacyponder:tree").setDescription("A tree, with some blocks around it."));
        entry.addPage(PageBase.catalogPage());

        PonderRegistry.add(INTERNAL_DEMO_ID, entry);
        return true;
    }
    public static void addHelpEntry(){
        IPonderEntry entry = IPonderEntry.createPonderEntry("unconfusion.entry.help.title","unconfusion.entry.help.desc");
        entry.addPage(PageBase.fromDrawable((w, h) -> {
            DrawableGroup group = DrawableBuilder.buildGroup();
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.desc",0xFFFFFFFF).setAlign(0.5F).setMaxWidth(w),w/2, (int) (h*0.3-5));
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.bottomleft",0xFFFFFFFF),2,h-12);
            return group;
        }));
        entry.addPage(PageBase.fromStructure("legacyponder:tree").setDescription("A tree, with some blocks around it."));
        entry.addPage(PageBase.catalogPage());

        PonderRegistry.add(HELP_ID, entry);
    }
}
