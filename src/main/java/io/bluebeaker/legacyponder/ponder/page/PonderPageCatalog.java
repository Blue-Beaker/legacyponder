package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.drawable.PrebuiltDrawables;

public class PonderPageCatalog extends PonderPageDrawable{
    public PonderPageCatalog() {
        super((w,h)-> PrebuiltDrawables.buildCatalog(w));
    }
}
