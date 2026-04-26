package io.bluebeaker.legacyponder.demo;

import crafttweaker.mc1120.brackets.BracketHandlerOre;
import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.crafttweaker.IEntry;
import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.manual.drawable.DrawableItem;
import io.bluebeaker.legacyponder.manual.drawable.DrawableText;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
            int textY = h*3/10-5;

            DrawableText desc = DrawableBuilder.formattedText("unconfusion.entry.help.page1.text1", 0xFFFFFFFF).setAlign(0.5F).setMaxWidth(w);
            group.addChild(desc,w/2, textY);
            textY += desc.getHeight();

            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.bottomleft",0xFFFFFFFF,new TextComponentKeybind("key.legacyponder.prev_page").getFormattedText(), new TextComponentKeybind("key.legacyponder.next_page").getFormattedText()),2,h-12);
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.topleft",0xFFFFFFFF),2,2);
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page1.topright",0xFFFFFFFF).setAlign(1),w,2);
            return group;
        }).setDescription("unconfusion.entry.help.page1.desc"));

        entry.addPage(PageBase.fromStructure("legacyponder:tree").setOverlay((w,h)->{
            DrawableGroup group = DrawableBuilder.buildGroup();

            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page2.middle",0xFFFFFFFF).setAlign(0.5F),w/2, 20);
            group.addChild(DrawableBuilder.formattedText("unconfusion.entry.help.page2.bottomleft",0xFFFFFFFF),2,h-28);

            return group;
        }).setDescription("unconfusion.entry.help.page2.desc"));

        entry.addPage(PageBase.fromStructure("legacyponder:tree")
                .addHighlightArea(1,3,2,2,4,3,0xFFAAAA)
                .addHoverComponent(1.5F,4F,2.5F,0xFFAAAA,(w,h)->{
            DrawableGroup group = DrawableBuilder.buildGroup();
            DrawableText text1 = DrawableBuilder.formattedText("unconfusion.entry.help.page3.hover1", 0xFFFFFFFF);
            group.addChild(text1.setMaxWidth(150),0, 0);
            return group;
        }).setDescription("unconfusion.entry.help.page3.desc"));

        entry.addPage(PageBase.fromStructure("legacyponder:tree")
                .addHighlightArea(1,3,1,2,4,2,0xAAFFAA)
                .addHoverComponent(1.5F,4F,1.5F,0xAAFFAA,(w,h)->{
                    DrawableGroup group = DrawableBuilder.buildGroup();
                    DrawableText text1 = DrawableBuilder.formattedText("unconfusion.entry.help.page4.hover1", 0xFFFFFFFF, new TextComponentKeybind("key.jei.showRecipe").getFormattedText(), new TextComponentKeybind("key.jei.showUses").getFormattedText());
                    group.addChild(text1.setMaxWidth(150),0, 0);
                    group.addChild(new DrawableItem(new ItemStack(Items.DIAMOND)),0,text1.getYMax());
                    return group;
                }).setDescription("unconfusion.entry.help.page4.desc"));


        entry.addPage(PageBase.catalogPage());

        ManualRegistry.add(HELP_ID, entry);
    }
}
