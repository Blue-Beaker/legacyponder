package io.bluebeaker.legacyponder.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collection;
import java.util.List;

public class ItemUtils {
    public static List<ItemStack> expandWildcard(Collection<ItemStack> stacks){
        NonNullList<ItemStack> out = NonNullList.create();
        for (ItemStack itemStack : stacks) {
            if(itemStack.getMetadata()==32767){
                CreativeTabs creativeTab = itemStack.getItem().getCreativeTab();
                if(creativeTab==null) creativeTab=CreativeTabs.SEARCH;
                itemStack.getItem().getSubItems(creativeTab, out);
            }else {
                out.add(itemStack);
            }
        }
        return out;
    }
}
