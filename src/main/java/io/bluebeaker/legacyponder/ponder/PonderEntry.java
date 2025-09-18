package io.bluebeaker.legacyponder.ponder;

import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PonderEntry {
    public final String id;
    private final List<PonderPageBase> pages;

    private final List<ItemStack> items;
    private final List<FluidStack> fluids;

    public PonderEntry(String id){
        this.id=id;
        this.pages=new ArrayList<>();
        this.items=new ArrayList<>();
        this.fluids=new ArrayList<>();
    }

    public void addPage(PonderPageBase page){
        this.pages.add(page);
    }
    public List<PonderPageBase> getPages(){
        return pages;
    }

    public void addItem(ItemStack item){
        this.items.add(item);
    }
    public List<ItemStack> getItems(){
        return this.items;
    }

    public void addFluid(FluidStack fluid){
        this.fluids.add(fluid);
    }
    public List<FluidStack> getFluids(){
        return this.fluids;
    }
}
