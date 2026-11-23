package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.utils.ModZenRegister;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;

@ModZenRegister
@ZenClass("mods.legacyponder.Links")
public class Links {
    @ZenMethod
    public static LinkHover hover(String tooltip){
        return new LinkHover(tooltip);
    }
    @ZenMethod
    public static LinkPonder manual(String id, int page){
        return new LinkPonder(id, page);
    }
    @ZenMethod
    public static LinkPonder manual(String id){
        return new LinkPonder(id);
    }
    @ZenMethod
    public static LinkUrl url(String url, @Nullable String tooltip){
        return new LinkUrl(url,tooltip);
    }

    @ZenMethod
    public static LinkJeiCategory category(String category, @Nullable String tooltip){
        return new LinkJeiCategory(category,tooltip);
    }
    @ZenMethod
    public static LinkJeiCategory category(String[] category, @Nullable String tooltip){
        return new LinkJeiCategory(category,tooltip);
    }

    public static LinkItem item(ItemStack item){
        return new LinkItem(item);
    }
}
