package io.bluebeaker.legacyponder.crafttweaker.drawable;

import crafttweaker.api.item.IItemStack;
import io.bluebeaker.legacyponder.crafttweaker.link.LinksExt;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ModZenRegister
@ZenExpansion("mods.legacyponder.DrawableBase")
public class DrawableBaseExt {
    @ZenMethod
    public static DrawableBase setLinkItem(DrawableBase inst, IItemStack item) {return inst.setLink(LinksExt.item(item));}
}
