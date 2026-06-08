package io.bluebeaker.legacyponder.manual.link;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.List;

@ZenClass("mods.legacyponder.LinkItemBase")
@ZenRegister
public interface LinkItemBase extends LinkIngredient<ItemStack> {
    @Override
    default List<String> getTooltip(GuiUnconfusion screen) {
        return screen.getItemToolTip(getIngredient());
    }
}
