package io.bluebeaker.legacyponder.crafttweaker;
import io.bluebeaker.legacyponder.ModZenRegister;

import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import stanhebben.zenscript.annotations.ZenClass;

@ModZenRegister
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
