package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.legacyponder.IDrawableSupplier")
public interface IDrawableSupplier {
    /**
     * Build a drawable for the given size
     * @param width given width, or 0 if not constrained
     * @param height given height, or 0 if not constrained
     * @return the drawable
     */
    DrawableBase process(int width, int height);
}
