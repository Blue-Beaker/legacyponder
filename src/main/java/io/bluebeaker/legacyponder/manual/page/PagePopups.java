package io.bluebeaker.legacyponder.manual.page;

import io.bluebeaker.legacyponder.manual.hover.HoverComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class PagePopups extends PageBase {
    protected final List<HoverComponent> hoverComponents = new ArrayList<>();
    public List<HoverComponent> getHoverComponents() {
        return hoverComponents;
    }
}
