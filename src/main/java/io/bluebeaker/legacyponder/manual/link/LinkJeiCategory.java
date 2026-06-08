package io.bluebeaker.legacyponder.manual.link;

import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;
import mezz.jei.api.recipe.IRecipeCategory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LinkJeiCategory implements LinkBase {
    @Nullable
    public final String tooltip;
    public final List<String> categories = new ArrayList<>();

    public LinkJeiCategory(String category, @Nullable String tooltip) {
        this.tooltip = tooltip;
        this.categories.add(category);
    }

    public LinkJeiCategory(String[] categories, @Nullable String tooltip) {
        this.tooltip = tooltip;
        Collections.addAll(this.categories, categories);
    }

    @Override
    public void onClick(GuiUnconfusion screen, int button) {
        if(button==0 || button==1){
            JEIUtils.showJEICategories(this.categories);
            screen.releaseMouse();
        }
    }

    @Override
    public List<String> getTooltip(GuiUnconfusion screen) {
        if(tooltip==null || tooltip.isEmpty()){
            List<String> list = new ArrayList<>();
            for(String category : this.categories){
                IRecipeCategory<?> cat1 = JEIUtils.getCategory(category);
                if(cat1!=null){
                    list.add(cat1.getTitle());
                }
            }
            return list;
        }else {
            return Arrays.asList(TextUtils.formatLines(tooltip));
        }
    }
}
