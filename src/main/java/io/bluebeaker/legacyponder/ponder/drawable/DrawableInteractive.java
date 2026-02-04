package io.bluebeaker.legacyponder.ponder.drawable;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.DrawableInteractive")
public abstract class DrawableInteractive extends DrawableBase{

    private boolean interactable = true;

    @ZenMethod
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }

    @ZenMethod
    public boolean isInteractable() {
        return interactable;
    }
}
