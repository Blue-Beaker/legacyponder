package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageDrawable;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPageDrawable")
@ZenRegister
public class PonderPageDrawable extends PonderPageBase{
    private final IDrawableSupplier drawableSupplier;
    public PonderPageDrawable(IDrawableSupplier drawableSupplier){
        this.drawableSupplier = drawableSupplier;
    }
    public DrawableBase getDrawable(int width, int height){
        return drawableSupplier.process(width, height);
    }

    @Override
    public GuiInfoPage<PonderPageDrawable> getGuiPage(GuiScreenPonder parent) {
        return new GuiPageDrawable(parent,this);
    }
}
