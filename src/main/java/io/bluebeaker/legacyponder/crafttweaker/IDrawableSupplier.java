package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.legacyponder.IDrawableSupplier")
public interface IDrawableSupplier {
    DrawableBase process(int width, int height);
}
