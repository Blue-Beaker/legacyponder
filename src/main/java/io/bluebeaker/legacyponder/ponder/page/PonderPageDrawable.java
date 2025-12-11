package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageDrawable;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.PonderPageDrawable")
@ZenRegister
public class PonderPageDrawable extends PonderPageBase{
    private final IDrawableSupplier drawableSupplier;
    public PonderPageDrawable(IDrawableSupplier drawableSupplier){
        this.drawableSupplier = drawableSupplier;
    }

    /** Build a drawable from this page.
     * @param width width
     * @param height height
     * @return The drawable built for the specified page size
     */
    @ZenMethod
    public DrawableBase getDrawable(int width, int height){
        return drawableSupplier.process(width, height);
    }

    @Override
    public GuiInfoPage<PonderPageDrawable> getGuiPage(GuiScreenPonder parent) {
        return new GuiPageDrawable(parent,this);
    }
}
