package io.bluebeaker.legacyponder.world.handler;

import codechicken.multipart.TileMultipart;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerFMP {
    @SubscribeEvent
    public void onTileEntityLoad(StructureTileEvent.AfterLoad event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof TileMultipart){
        }
    }
    @SubscribeEvent
    public void onTileEntityRender(StructureTileEvent.Render event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof TileMultipart){
//            LegacyPonder.getLogger().info("tile:{}",tile);
        }
    }
}
