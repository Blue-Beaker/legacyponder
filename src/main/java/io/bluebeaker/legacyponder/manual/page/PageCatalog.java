package io.bluebeaker.legacyponder.manual.page;
import io.bluebeaker.legacyponder.ModZenRegister;

import io.bluebeaker.legacyponder.manual.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.manual.drawable.DrawableScroll;
import io.bluebeaker.legacyponder.manual.drawable.PrebuiltDrawables;
import stanhebben.zenscript.annotations.ZenClass;

@ModZenRegister
@ZenClass("mods.legacyponder.PageCatalog")
public class PageCatalog extends PageDrawable{
    public PageCatalog() {
        super((w,h)-> {
            DrawableGroup drawableGroup = PrebuiltDrawables.buildCatalog(w);
            return DrawableScroll.build(drawableGroup,w,h);
        });
    }
}
