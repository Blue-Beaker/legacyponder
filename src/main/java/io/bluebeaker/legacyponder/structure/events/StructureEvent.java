package io.bluebeaker.legacyponder.structure.events;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class StructureEvent<W extends World> extends Event {
    public final W world;
    public StructureEvent(W world) {
        this.world = world;
    }
}
