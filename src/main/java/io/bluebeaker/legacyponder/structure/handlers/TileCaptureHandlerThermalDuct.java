package io.bluebeaker.legacyponder.structure.handlers;

import cofh.thermaldynamics.duct.ConnectionType;
import cofh.thermaldynamics.duct.tiles.TileGrid;
import net.minecraft.nbt.NBTTagCompound;

public class TileCaptureHandlerThermalDuct implements ITileCaptureHandler<TileGrid> {

    @Override
    public NBTTagCompound saveExtraData(TileGrid tile, NBTTagCompound data) {
        int[] sides = new int[6];
        for (int i = 0; i < 6; i++) {
            sides[i]=tile.getConnectionType(i).ordinal();
        }
        data.setIntArray("sides",sides);
        return data;
    }

    @Override
    public void loadExtraData(TileGrid tile, NBTTagCompound tag) {
        if(tag.hasKey("sides")){
            int[] sides = tag.getIntArray("sides");
            for (int i = 0; i < 6; i++) {
                tile.setConnectionType(i, ConnectionType.values()[sides[i]]);
            }
        }
    }
}
