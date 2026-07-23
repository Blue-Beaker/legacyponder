package io.bluebeaker.legacyponder.manual;

import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class Entry {
    public final String title;
    public final String summary;
    private final List<PageBase> pages;

    public final IngredientLink mainLink;

    public boolean hideInJEI = false;

    public Entry(String title, String summary){
        this.title=title;
        this.summary = summary;
        this.pages=new ArrayList<>();
        this.mainLink =new IngredientLink();
    }

    public void addPage(PageBase page){
        this.pages.add(page);
    }
    public List<PageBase> getPages(){
        return pages;
    }

    //----------Main Ingredients----------
    public void addItems(List<ItemStack> items1){
        this.mainLink.addItems(items1);
    }
    public void addItem(ItemStack item){
        this.mainLink.addItem(item);
    }
    public List<List<ItemStack>> getItems(){
        return this.mainLink.getItems();
    }

    public void addFluid(FluidStack fluid){
        this.mainLink.addFluid(fluid);
    }
    public List<FluidStack> getFluids(){
        return this.mainLink.getFluids();
    }

    public IngredientLink getMainLink() {
        return mainLink;
    }

    public boolean isHideInJEI(){return hideInJEI;}
}
