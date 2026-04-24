package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.ponder.link.LinkBase;
import io.bluebeaker.legacyponder.ponder.link.LinkFluid;
import io.bluebeaker.legacyponder.utils.FluidRender;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ZenClass("mods.legacyponder.DrawableFluid")
public class DrawableFluid extends DrawableBase{
    private final FluidStack fluid;

    public DrawableFluid(FluidStack fluid){
        this.fluid=fluid;
        resetLink();
    }

    public static DrawableFluid build(ILiquidStack fluid) {
        return new DrawableFluid(CraftTweakerMC.getLiquidStack(fluid));
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();

        FluidRender.renderFluid(this.fluid,x,y);

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();

        if(isLinkClickable() && isLastHovered()){
            GuiUtils.drawGradientRect(300, x, y, x + 16, y + 16, 0x80ffffff, 0x80ffffff);
        }

    }

    public FluidStack getFluid() {
        return fluid;
    }

    @Nullable
    @Override
    public LinkBase getDefaultLink() {
        return new LinkFluid(this.fluid);
    }

    @ZenMethod
    @Override
    public DrawableBase setSize(int w, int h) {
        return this;
    }

    @ZenMethod
    @Override
    public int getWidth() {
        return 16;
    }
    @ZenMethod
    @Override
    public int getHeight() {
        return 16;
    }
}
