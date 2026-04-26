package io.bluebeaker.legacyponder.manual.page;

import io.bluebeaker.legacyponder.manual.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.manual.drawable.PrebuiltDrawables;

public class PageSummary extends PageDrawable{
    public PageSummary(String id) {
        super((w,h)-> {
            int newWidth = Math.min(256,w);
            DrawableGroup summary = PrebuiltDrawables.buildSummary(id, newWidth, h);
            return summary.setPosition((w-newWidth)/2,(h-summary.getYMax())/2);
        });
    }
}
