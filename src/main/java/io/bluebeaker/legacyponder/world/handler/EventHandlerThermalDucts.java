package io.bluebeaker.legacyponder.world.handler;

import cofh.thermaldynamics.duct.tiles.TileGrid;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import io.bluebeaker.legacyponder.structure.handlers.TileCaptureHandlerThermalDuct;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerThermalDucts {

    public TileCaptureHandlerThermalDuct handler = new TileCaptureHandlerThermalDuct();

    @SubscribeEvent
    public void onTileEntitySave(StructureTileEvent.Save event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof TileGrid){
            handler.saveExtraData((TileGrid) tile, event.extraData);
        }
    }
    @SubscribeEvent
    public void onTileEntityLoad(StructureTileEvent.Load event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof TileGrid && event.extraData!=null){
            handler.loadExtraData((TileGrid) tile, event.extraData);
        }
    }
}
