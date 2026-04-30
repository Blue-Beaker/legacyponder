package io.bluebeaker.legacyponder.manual.page;

import io.bluebeaker.legacyponder.manual.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.manual.drawable.DrawableScroll;
import io.bluebeaker.legacyponder.manual.drawable.PrebuiltDrawables;

public class PageCatalog extends PageDrawable{
    public PageCatalog() {
        super((w,h)-> {
            DrawableGroup drawableGroup = PrebuiltDrawables.buildCatalog(w);
            return DrawableScroll.build(drawableGroup,w,h);
        });
    }
}
