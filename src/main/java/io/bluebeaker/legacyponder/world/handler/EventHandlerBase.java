package io.bluebeaker.legacyponder.world.handler;

import io.bluebeaker.legacyponder.world.LoadTileEntityEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandlerBase {
    @SubscribeEvent
    public static void onTileEntityLoad(LoadTileEntityEvent event){
        TileEntity tile = event.tileEntity;
    }
}
