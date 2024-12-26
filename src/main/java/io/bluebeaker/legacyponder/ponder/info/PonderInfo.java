package io.bluebeaker.legacyponder.ponder.info;

import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

public class PonderInfo extends Gui {
    public final List<IPonderInfoComponent> components;
    public PonderInfo() {
        this.components = new ArrayList<>();
    }
    public void add(IPonderInfoComponent component){
        this.components.add(component);
    }
}
