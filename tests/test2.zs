#reloadable
#sideonly client

import mods.legacyponder.IPonderEntry;
import mods.legacyponder.PonderRegistry;
import mods.legacyponder.Page;
import mods.legacyponder.DrawableBuilder;
import mods.legacyponder.HoverComponent;
import mods.legacyponder.HighlightArea;
import mods.legacyponder.DrawableBox;
import mods.legacyponder.DrawableLine;

import crafttweaker.text.ITextComponent;



val entry = IPonderEntry.createPonderEntry("Entry 3","Example entry 3 with custom GUI");

entry.addIngredient(<ore:plankWood>);
entry.addIngredient(<minecraft:wool:0>);
entry.addIngredient(<liquid:lava>);
entry.addIngredient(<microblockcbe:microblock:1>.withTag({mat: "minecraft:stone[variant=stone]"}));

entry.addPage(Page.fromDrawable(function(w,h){
    val group = DrawableBuilder.buildGroup();
    group.addChild(DrawableBuilder.buildText("Test",0xFFFFFFFF).setAlign(0.5),w/2,0);
    group.addChild(DrawableBuilder.buildTexture("textures/gui/container/crafting_table.png",0,0,176,166),0,20);
    group.addChild(DrawableBuilder.buildItem(<minecraft:gold_ingot>*10),30,37);
    group.addChild(DrawableBuilder.buildItem(<minecraft:beacon>),48,37);
    group.addChild(DrawableBuilder.buildItem(<ore:plankWood>),66,37);
    return group;
}));
entry.addPage(Page.fromStructure("test1/stru1")
    .addHighlightArea(2,1,1,3,2,2,255,180,180)
    .addHoverComponent(2.5,1,1.5,255,180,180,function(w,h){
             return DrawableBuilder.buildItem(<minecraft:diamond>*2);
             })
    .addHighlightArea(HighlightArea.build(0,0,0,3,2,3).setColor(80,127,127))
    .addHoverComponent(
        HoverComponent.build(1.5,1,1.5,function(w,h){
            val group = DrawableBuilder.buildGroup();
            group.addChild(DrawableBox.build(0,0,30,30,0xFFFF8080).setLinkItem(<minecraft:iron_ingot>));
            group.addChild(DrawableBuilder.buildItem(<minecraft:gold_ingot>*10),2,2);
            group.addChild(DrawableBuilder.buildText(ITextComponent.fromString("Test"),0xFFFFFFFF),0,64);
            group.addChild(DrawableLine.build(20,50,50,30,0xFFFF8080).point(50,50).setLinkItem(<minecraft:iron_ingot>));

            val group2 = DrawableBuilder.buildGroup();
            group2.addChild(DrawableBox.build(0,0,30,30,0xFFFF8080).setLinkItem(<minecraft:iron_ingot>));
            group2.addChild(DrawableBuilder.buildItem(<minecraft:gold_ingot>*10),2,2);
            group.addChild(group2,50,0);

            return group;
            }
        ).setColor(80,127,127))
    );

PonderRegistry.add("test_entry3",entry);