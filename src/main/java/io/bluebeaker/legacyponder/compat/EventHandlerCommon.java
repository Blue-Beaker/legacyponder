package io.bluebeaker.legacyponder.compat;

import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class EventHandlerCommon {

    public static Set<String> SPECIAL_TILES = new HashSet<>();

    private static EventHandlerCommon instance;

    private EventHandlerCommon(){}

    public static void updateConfig(){
        SPECIAL_TILES.clear();
        for (String line : CommonConfig.specialTileClasses) {
            String trim = line.split("#", 2)[0].trim();
            if(trim.isEmpty()) continue;
            SPECIAL_TILES.add(trim);
        }

    }

    public static EventHandlerCommon getInstance(){
        if(instance==null){
            instance=new EventHandlerCommon();
        }
        return instance;
    }

    @SubscribeEvent
    public void onSave(StructureTileEvent.Save event){
        TileEntity tile = event.tileEntity;
        if(SPECIAL_TILES.contains(tile.getClass().getName())) {
            event.extraData.setTag("updateTag",tile.getUpdateTag());
        }
    }

    @SubscribeEvent
    public void onLoad(StructureTileEvent.Load event){
        TileEntity tile = event.tileEntity;
        if(!(event.world instanceof DummyWorld)) return;
        if (event.extraData != null) {
            NBTTagCompound updateTag = event.extraData.getCompoundTag("updateTag");
            tile.handleUpdateTag(updateTag);
            tile.setWorld(((DummyWorld) event.world).getClientWorld());
        }
    }
}
