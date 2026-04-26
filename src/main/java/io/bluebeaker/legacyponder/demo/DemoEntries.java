package io.bluebeaker.legacyponder.demo;

import crafttweaker.mc1120.brackets.BracketHandlerOre;
import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.crafttweaker.IEntry;
import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.manual.drawable.DrawableText;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraft.util.text.TextComponentKeybind;

public class DemoEntries {

    public static final String HELP_ID = "internal:help";
    public static final String INTERNAL_DEMO_ID = "internal:demo";

    public static void addDemoIfNeeded(){
        if(CommonConfig.demo){
            demoAdded=addDemoEntry();
        } else if (demoAdded) {
            ManualRegistry.getEntries().remove(INTERNAL_DEMO_ID);
            demoAdded=false;
        }
        addHelpEntry();
    }
    private static boolean demoAdded = false;
    public static boolean addDemoEntry(){
        if(ManualRegistry.getEntries().containsKey(INTERNAL_DEMO_ID)) return false;

        IEntry entry = IEntry.createEntry("unconfusion.entry.demo_1.title","unconfusion.entry.demo_1.desc");

        entry.addIngredient(BracketHandlerOre.getOre("ingotGold"));
        entry.addPage(PageBase.fromDrawable(new IDrawableSupplier() {
            @Override
            public DrawableBase process(int width, int height) {
                return DrawableBuilder.formattedText("unconfusion.entry.demo_1.page1.text",0xFFFFFFFF).setAlign(0).setSize(width, height);
            }
        }));
        entry.addPage(PageBase.fromStructure("legacyponder:tree").setDescription("A tree, with some blocks around it."));
        entry.addPage(PageBase.catalogPage());

        ManualRegistry.add(INTERNAL_DEMO_ID, entry);
        return true;
    }
    public static void addHelpEntry(){
        IEntry entry = IEntry.createEntry("unconfusion.entry.help.title","unconfusion.entry.help.desc");
        entry.addPage(PageBase.fromDrawable((w, h) -> {
            DrawableGroup group = DrawableBuilder.buildGroup();
            int textY = (int)(h*0.3-5);

            DrawableText desc = DrawableBuilder.formattedText("unconfusion.entry.help.page1.text1", 0xFFFFFFFF).setAlign(0.5F).setMaxWidth(w);
            group.addChild(desc,w/2, textY);
            textY += desc.getHeight();

            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.bottomleft",0xFFFFFFFF,new TextComponentKeybind("key.legacyponder.prev_page").getFormattedText(), new TextComponentKeybind("key.legacyponder.next_page").getFormattedText()),2,h-12);
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.topleft",0xFFFFFFFF),2,2);
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.topright",0xFFFFFFFF).setAlign(1),w,2);
            return group;
        }).setDescription("unconfusion.entry.help.page1.desc"));
        entry.addPage(PageBase.fromStructure("legacyponder:tree").setDescription("A tree, with some blocks around it."));
        entry.addPage(PageBase.catalogPage());

        ManualRegistry.add(HELP_ID, entry);
    }
}
