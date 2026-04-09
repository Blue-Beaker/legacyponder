package io.bluebeaker.legacyponder.ponder.link;

import net.minecraft.item.ItemStack;

public class LinkItem implements LinkItemBase {

    private final ItemStack item;

    public LinkItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }
}
