package io.bluebeaker.legacyponder.manual.link;
import io.bluebeaker.legacyponder.ModZenRegister;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.List;

@ModZenRegister
@ZenClass("mods.legacyponder.LinkItemBase")
public interface LinkItemBase extends LinkIngredient<ItemStack> {
    @Override
    default List<String> getTooltip(GuiUnconfusion screen) {
        return screen.getItemToolTip(getIngredient());
    }
}
