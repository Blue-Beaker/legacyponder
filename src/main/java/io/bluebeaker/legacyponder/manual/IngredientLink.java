package io.bluebeaker.legacyponder.manual;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IngredientLink {
    private final List<List<ItemStack>> items = new ArrayList<>();
    private final List<FluidStack> fluids = new ArrayList<>();

    public void addItems(List<ItemStack> items1){
        this.items.add(items1);
    }
    public void addItem(ItemStack item){
        this.items.add(Collections.singletonList(item));
    }
    public List<List<ItemStack>> getItems(){
        return this.items;
    }

    public void addFluid(FluidStack fluid){
        this.fluids.add(fluid);
    }
    public List<FluidStack> getFluids(){
        return this.fluids;
    }
}
