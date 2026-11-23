package io.bluebeaker.legacyponder.demo;

import io.bluebeaker.legacyponder.api.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.drawable.DrawableHoverPos;
import io.bluebeaker.legacyponder.manual.drawable.DrawableItem;
import io.bluebeaker.legacyponder.manual.drawable.DrawableText;
import io.bluebeaker.legacyponder.manual.drawable.DrawableTexture;
import io.bluebeaker.legacyponder.manual.drawable.container.DrawableGroup;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentKeybind;
import net.minecraftforge.oredict.OreDictionary;

public class HelpEntry {
    public static final String HELP_ID = "internal:help";

    public static void addHelpEntry(){
        Entry entry = new Entry("unconfusion.entry.help.title","unconfusion.entry.help.desc");
        entry.addPage(PageBase.fromDrawable((w, h) -> {
            DrawableGroup group = new DrawableGroup();

            DrawableText desc = DrawableText.buildFormatted("unconfusion.entry.help.page1.text1", 0xFFFFFFFF).setAlign(0.5F).setMaxWidth(w);
            group.addChild(desc,w/2, h*3/10-5);

            group.addChild(DrawableText.buildFormatted("unconfusion.entry.help.page1.bottomleft",0xFFFFFFFF,new TextComponentKeybind("key.legacyponder.prev_page").getFormattedText(), new TextComponentKeybind("key.legacyponder.next_page").getFormattedText()),2,h-12);
            group.addChild(DrawableText.buildFormatted("unconfusion.entry.help.page1.topleft",0xFFFFFFFF),2,2);
            group.addChild(DrawableText.buildFormatted("unconfusion.entry.help.page1.topright",0xFFFFFFFF).setAlign(1),w,2);
            return group;
        }).setDescription("unconfusion.entry.help.page1.desc"));

        entry.addPage(PageBase.fromStructure("legacyponder:tree").setOverlay((w,h)->{
            DrawableGroup group = new DrawableGroup();

            group.addChild(DrawableText.buildFormatted("unconfusion.entry.help.page2.middle",0xFFFFFFFF).setAlign(0.5F),w/2, 20);
            group.addChild(DrawableText.buildFormatted("unconfusion.entry.help.page2.bottomleft",0xFFFFFFFF),2,h-28);

            return group;
        }).setDescription("unconfusion.entry.help.page2.desc"));

        entry.addPage(PageBase.fromStructure("legacyponder:tree")
                .addHighlightArea(1,3,2,2,4,3,0xFFAAAA)
                .addHoverComponent(1.5F,4F,2.5F,0xFFAAAA,(w,h)->{
            DrawableGroup group = new DrawableGroup();
            DrawableText text1 = DrawableText.buildFormatted("unconfusion.entry.help.page3.hover1", 0xFFFFFFFF);
            group.addChild(text1.setMaxWidth(150),0, 0);
            return group;
        }).setDescription("unconfusion.entry.help.page3.desc"));

        entry.addPage(PageBase.fromStructure("legacyponder:tree")
                .addHighlightArea(1,3,1,2,4,2,0xAAFFAA)
                .addHoverComponent(1.5F,4F,1.5F,0xAAFFAA,(w,h)->{
                    DrawableGroup group = new DrawableGroup();
                    DrawableText text1 = DrawableText.buildFormatted("unconfusion.entry.help.page4.hover1", 0xFFFFFFFF, new TextComponentKeybind("key.jei.showRecipe").getFormattedText(), new TextComponentKeybind("key.jei.showUses").getFormattedText());
                    group.addChild(text1.setMaxWidth(150),0, 0);
                    group.addChild(new DrawableItem(new ItemStack(Items.DIAMOND)),0,text1.getYMax());
                    return group;
                }).setDescription("unconfusion.entry.help.page4.desc"));

        entry.addPage(PageBase.fromDrawable((w,h)->{
                    DrawableGroup group = new DrawableGroup();
                    group.addChild(DrawableText.buildFormatted("unconfusion.entry.help.page5.title",0xFFFFFFFF),0,0);
                    group.addChild(DrawableTexture.build("textures/gui/container/furnace.png",0,0,176,166),0,15);
                    group.addChild(new DrawableItem(OreDictionary.getOres("logWood")),56,15+17);
                    group.addChild(new DrawableItem(new ItemStack(Items.COAL,1,1)),116,15+35);
                    group.addChild(new DrawableHoverPos(0),116+12,15+12+35);

                    DrawableText text = DrawableText.build(ManualRegistry.getEntryTitle(HELP_ID) + ":2", 0xFFFFFFFF);
                    text.setLinkManual(HELP_ID,2);
                    group.addChild(text,12,88+15);
                    group.addChild(new DrawableHoverPos(1),text.getXMax(),text.getYMax());

                    group.setPosition((w-group.getWidth())/3,0);
                    return group;
                })
                .addHoverComponent(0,0xFFFFAA,(w,h)-> DrawableText.buildFormatted("unconfusion.entry.help.page5.hover1", 0xFFFFFFFF).setMaxWidth(100))
                .addHoverComponent(1,0xAAAAFF,(w,h)-> DrawableText.buildFormatted("unconfusion.entry.help.page5.hover2", 0xFFFFFFFF).setMaxWidth(100))
                .setDescription("unconfusion.entry.help.page5.desc"));

        ManualRegistry.PONDER_REGISTRY.put(HELP_ID, entry);
    }
}
