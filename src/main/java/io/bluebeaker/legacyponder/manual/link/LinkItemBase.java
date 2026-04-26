package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface LinkItemBase extends LinkIngredient<ItemStack> {
    @Override
    default List<String> getTooltip(GuiUnconfusion screen) {
        return screen.getItemToolTip(getIngredient());
    }
}
