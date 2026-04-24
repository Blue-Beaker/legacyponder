package io.bluebeaker.legacyponder.ponder.link;

import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class LinkFluid implements LinkIngredient<FluidStack> {

    private final FluidStack fluid;

    public LinkFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    public FluidStack getIngredient() {
        return fluid;
    }

    @Override
    public List<String> getTooltip(GuiUnconfusion screen) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(fluid.getLocalizedName());
        tooltip.add(" * "+fluid.amount+" mB");
        return tooltip;
    }
}
