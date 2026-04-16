package io.bluebeaker.legacyponder.compat;

import buildcraft.lib.tile.TileBC_Neptune;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerBC {
    @SubscribeEvent
    public void onSaveBCTile(StructureTileEvent.Save event){
        if(event.tileEntity instanceof TileBC_Neptune){
            // Crop tag
            event.tileData.getCompoundTag("owner").removeTag("Properties");
        }
    }
}
