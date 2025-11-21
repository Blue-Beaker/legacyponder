package io.bluebeaker.legacyponder.world.handler;

import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerIC2 {
    @SubscribeEvent
    public void onTileEntityLoad(StructureTileEvent.AfterLoad event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof INetworkUpdateListener && tile instanceof INetworkDataProvider){
            for (String networkedField : ((INetworkDataProvider) tile).getNetworkedFields()) {
                ((INetworkUpdateListener) tile).onNetworkUpdate(networkedField);
            }
        }
    }
}
