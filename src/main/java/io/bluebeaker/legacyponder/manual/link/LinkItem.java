package io.bluebeaker.legacyponder.manual.link;
import io.bluebeaker.legacyponder.ModZenRegister;

import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ModZenRegister
@ZenClass("mods.legacyponder.LinkItem")
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
