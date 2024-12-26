package io.bluebeaker.legacyponder.ponder;

import io.bluebeaker.legacyponder.ponder.info.PonderInfo;

public class PonderPageBase {
    public PonderInfo info;
    protected PonderPageBase(PonderInfo info) {
        this.info=info;
    }
    protected PonderPageBase(){
        this(null);
    }
}
