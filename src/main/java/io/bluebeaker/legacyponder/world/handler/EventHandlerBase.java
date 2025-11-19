package io.bluebeaker.legacyponder.world.handler;

import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerBase {
    @SubscribeEvent
    public void onTileEntityLoad(StructureTileEvent.AfterLoad event){
        TileEntity tile = event.tileEntity;
    }
}
