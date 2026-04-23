package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.drawable.PrebuiltDrawables;

public class PageCatalog extends PageDrawable{
    public PageCatalog() {
        super((w,h)-> PrebuiltDrawables.buildCatalog(w));
    }
}
