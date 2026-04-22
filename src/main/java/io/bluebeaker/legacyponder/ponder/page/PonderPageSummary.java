package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.ponder.drawable.PrebuiltDrawables;

public class PonderPageSummary extends PonderPageDrawable{
    public PonderPageSummary(String id) {
        super((w,h)-> {
            int newWidth = Math.min(256,w);
            DrawableGroup summary = PrebuiltDrawables.buildSummary(id, newWidth, h);
            return summary.setPosition((w-newWidth)/2,(h-summary.getYMax())/2);
        });
    }
}
