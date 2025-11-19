package io.bluebeaker.legacyponder.structure.handlers;

import cofh.thermaldynamics.block.BlockDuct;
import cofh.thermaldynamics.duct.tiles.TileGrid;
import net.minecraft.nbt.NBTTagCompound;

public class TileCaptureHandlerThermalDuct implements ITileCaptureHandler<TileGrid> {

    @Override
    public NBTTagCompound saveExtraData(TileGrid tile, NBTTagCompound data) {
        int[] sides = new int[6];
        for (int i = 0; i < 6; i++) {
            sides[i]=tile.getVisualConnectionType(i).ordinal();
        }
        data.setIntArray("sides",sides);
        return data;
    }

    @Override
    public void loadExtraData(TileGrid tile, NBTTagCompound tag) {
        if(tag.hasKey("sides")){
            int[] sides = tag.getIntArray("sides");
            if (tile.clientConnections == null) {
                tile.clientConnections=new BlockDuct.ConnectionType[6];
            }
            for (int i = 0; i < 6; i++) {
                tile.clientConnections[i]=BlockDuct.ConnectionType.values()[sides[i]];
            }
        }
    }
}
