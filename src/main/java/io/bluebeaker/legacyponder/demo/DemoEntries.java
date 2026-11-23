package io.bluebeaker.legacyponder.demo;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.Tags;
import io.bluebeaker.legacyponder.api.ManualRegistry;
import io.bluebeaker.legacyponder.crafttweaker.DrawableBuilder;
import io.bluebeaker.legacyponder.crafttweaker.IEntry;
import io.bluebeaker.legacyponder.crafttweaker.IManualRegistry;
import io.bluebeaker.legacyponder.crafttweaker.drawable.DrawableBaseExt;
import io.bluebeaker.legacyponder.jeiplugin.UnconfusionRecipeCategory;
import io.bluebeaker.legacyponder.manual.drawable.container.DrawableGrid;
import io.bluebeaker.legacyponder.manual.drawable.container.DrawableGroup;
import io.bluebeaker.legacyponder.manual.drawable.container.DrawableScroll;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraft.init.Items;

public class DemoEntries {

    public static final String INTERNAL_DEMO_ID = "internal:demo";

    public static void addDemoIfNeeded(){
        if(CommonConfig.demo){
            addDemoEntry();
        } else {
            ManualRegistry.getEntries().remove(INTERNAL_DEMO_ID);
        }
    }

    public static void addDemoEntry(){
        if(ManualRegistry.getEntries().containsKey(INTERNAL_DEMO_ID)) return;

        IEntry entry = IEntry.createEntry("unconfusion.entry.demo_1.title","unconfusion.entry.demo_1.desc");
        entry.addIngredient(CraftTweakerMC.getItemStack(Items.IRON_INGOT,1,0));
        entry.addIngredient(CraftTweakerMC.getItemStack(Items.GOLD_INGOT,1,0));
        entry.addIngredient(CraftTweakerMC.getItemStack(Items.DIAMOND,1,0));
        entry.addIngredient(CraftTweakerMC.getItemStack(Items.EMERALD,1,0));


        entry.addPage(PageBase.fromDrawable((width, height) -> DrawableBuilder.formattedText("unconfusion.entry.demo_1.page1.text",0xFFFFFFFF).setAlign(0).setSize(width, height)));
        entry.addPage(PageBase.fromStructure("legacyponder:tree").setDescription("A tree, with some blocks around it."));
        entry.addPage(PageBase.fromDrawable((width, height) -> {
            DrawableGroup group = DrawableBuilder.group();

            group.addChild(DrawableBuilder.text("This is just a text",0xFFFFFFFF));
            group.addChild(DrawableBuilder.text("Hover on this to see a tooltip",0xFFFFFFFF).setLinkHover("A tooltip"),0,10);
            group.addChild(DrawableBaseExt.setLinkItem(DrawableBuilder.text("Link to an item",0xFFFFFFFF),CraftTweakerMC.getItemStack(Items.DIAMOND_PICKAXE,1,500)),0,20);
            group.addChild(DrawableBuilder.text("Link to help page 2",0xFFFFFFFF).setLinkManual(HelpEntry.HELP_ID,2),0,30);
            group.addChild(DrawableBuilder.text("Link to the mod's repository",0xFFFFFFFF).setLinkUrl(Tags.MOD_URL,null),0,40);
            group.addChild(DrawableBuilder.text("Link to the JEI category",0xFFFFFFFF).setLinkCategory(UnconfusionRecipeCategory.UID,null),0,50);
            return group;
        }));
        entry.addPage(PageBase.fromDrawable((w,h)->{
            DrawableGroup group = DrawableBuilder.group();
            int itemColumns = Math.min(w/16,16);
            DrawableGrid grid = DrawableBuilder.grid(itemColumns,16,16);
            DrawableScroll scroll = DrawableBuilder.scroll(grid,itemColumns*16,h-10);
            group.addChild(scroll,(w-itemColumns*16)/2,10);
            group.addChild(DrawableBuilder.text("Scroll grid test",0xFFFFFFFF).setAlign(0.5F),w/2,0);

            IItemStack[] items = CraftTweakerAPI.loadedMods.get("minecraft").getItems();
            for (IItemStack item : items) {
                grid.addChild(DrawableBuilder.item(item));
            }
            return group;
        }));
        entry.addPage(PageBase.catalogPage().setDescription("Example catalog showing all existing entries"));

        IManualRegistry.add(INTERNAL_DEMO_ID, entry);
    }
}
