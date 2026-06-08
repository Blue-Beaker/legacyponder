package io.bluebeaker.legacyponder.manual.link;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.LinkItem")
@ZenRegister
public class LinkItem implements LinkItemBase {

    private final ItemStack item;

    public LinkItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack getIngredient() {
        return item;
    }
}
