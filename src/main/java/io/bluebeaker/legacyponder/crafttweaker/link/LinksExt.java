package io.bluebeaker.legacyponder.crafttweaker.link;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.manual.link.LinkItem;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModZenRegister
@ZenExpansion("mods.legacyponder.Links")
public abstract class LinksExt {
    @ZenMethodStatic
    public static LinkItem item(IItemStack item){
        return new LinkItem(CraftTweakerMC.getItemStack(item));
    }
}
