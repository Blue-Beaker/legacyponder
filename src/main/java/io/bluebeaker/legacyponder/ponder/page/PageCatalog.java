package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableScroll;
import io.bluebeaker.legacyponder.ponder.drawable.PrebuiltDrawables;

public class PageCatalog extends PageDrawable{
    public PageCatalog() {
        super((w,h)-> {
            DrawableGroup drawableGroup = PrebuiltDrawables.buildCatalog(w);
            return new DrawableScroll(drawableGroup,w,h);
        });
    }
}
