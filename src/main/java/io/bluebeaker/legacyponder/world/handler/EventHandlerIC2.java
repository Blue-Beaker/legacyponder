package io.bluebeaker.legacyponder.world.handler;

import ic2.api.network.INetworkUpdateListener;
import io.bluebeaker.legacyponder.world.LoadTileEntityEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerIC2 {
    @SubscribeEvent
    public static void onTileEntityLoad(LoadTileEntityEvent event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof INetworkUpdateListener){
            ((INetworkUpdateListener) tile).onNetworkUpdate("");
        }
    }
}
