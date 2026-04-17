#reloadable
#sideonly client

import mods.legacyponder.IPonderEntry;
import mods.legacyponder.PonderRegistry;
import mods.legacyponder.PonderPage;
import mods.legacyponder.DrawableBuilder;

val entry = IPonderEntry.createPonderEntry("Entry 1","Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1 Test page entry 1");
entry.addItem(<minecraft:diamond>);
entry.addFluid(<liquid:water>);
entry.addIngredient(<ore:plankWood>);
entry.addIngredient(<minecraft:wool:0>|<minecraft:wool:1>|<minecraft:wool:2>|<minecraft:wool:3>|<minecraft:wool:4>|<minecraft:wool:5>|<minecraft:wool:6>|<minecraft:wool:7>|<minecraft:wool:8>|<minecraft:wool:9>|<minecraft:wool:10>);
entry.addPage(PonderPage.catalogPage());
entry.addPage(PonderPage.dummyPage().setDescription("§bDummy page§r for the entry!"));
entry.addPage(PonderPage.fromStructure("5").setDescription("§1test1 §2test2 §3test3 §4test4 §5test5 §1test1 §2test2 §3test3 §4test4 §5test5 §1test1 §2test2 §3test3 §4test4 §5test5 §1test1 §2test2 §3test3 §4test4 §5test5 §1test1 §2test2 §3test3 §4test4 §5test5"));
entry.addPage(PonderPage.fromStructure("structure1").setDescription("§bStructure1"));
entry.addPage(PonderPage.fromDrawable(function(w,h){
    val group = DrawableBuilder.buildGroup();
    group.addChild(DrawableBuilder.buildText("Test",0xFFFFFFFF).setMaxWidth(0).setLinkPonder("test_entry3"),176/2,0);
    group.addChild(DrawableBuilder.buildTexture("textures/gui/container/crafting_table.png",0,0,176,166),0,20);
    group.addChild(DrawableBuilder.buildItem(<minecraft:diamond_pickaxe:800>),30,37);
    group.addChild(DrawableBuilder.buildItem(<minecraft:diamond_pickaxe:200>),48,37);

    group.addChild(DrawableBuilder.buildText("Click to show recipe of §bdiamond",0xFFFFFFFF).setMaxWidth(0).setLinkItem(<minecraft:diamond>),176/2,20);
    group.addChild(DrawableBuilder.buildText("Click to show url",0xFFFFFFFF).setMaxWidth(0).setLinkUrl("https://minecraft.net","minecraft.net"),176/2,30);

    group.setPosition((w/2)-(176/2),0);
    return group;
}));
entry.addPage(PonderPage.fromDrawable(function(w,h){
    val group = DrawableBuilder.buildGroup();
    group.addChild(DrawableBuilder.buildText("Links",0xFFFFFFFF).setMaxWidth(0),176/2,0);
    group.addChild(DrawableBuilder.buildText("Click to show recipe of §bdiamond",0xFFFFFFFF).setMaxWidth(0).setLinkItem(<minecraft:diamond>),176/2,10);
    group.addChild(DrawableBuilder.buildText("Click to show url",0xFFFFFFFF).setMaxWidth(0).setLinkUrl("https://minecraft.net","minecraft.net"),176/2,20);
    group.addChild(DrawableBuilder.buildText("Page 4",0xFFFFFFFF).setMaxWidth(0).setLinkPonder("",4),176/2,30);
    group.addChild(DrawableBuilder.buildText("Entry 3 : Page 4",0xFFFFFFFF).setMaxWidth(0).setLinkPonder("test_entry3",2),176/2,40);
    group.addChild(DrawableBuilder.buildText("Hover only",0xFFFFFFFF).setMaxWidth(0).setLinkHover("Just a hover\n111"),176/2,50);

    group.setPosition((w/2)-(176/2),0);
    return group;
}));

entry.addPage(PonderPage.fromStructure("dir1/structure2").setDescription("Structure 2"));

PonderRegistry.add("test_entry",entry);

val entry2 = IPonderEntry.createPonderEntry("Entry 2","Example entry 2 without page");

entry2.addIngredient(<ore:plankWood>|<minecraft:wool:0>|<minecraft:wool:1>|<minecraft:wool:2>|<minecraft:wool:3>|<minecraft:wool:4>|<minecraft:wool:5>|<minecraft:wool:6>|<minecraft:wool:7>|<minecraft:wool:8>|<minecraft:wool:9>|<minecraft:wool:10>);
PonderRegistry.add("test_entry2",entry2);