package io.bluebeaker.legacyponder.structure.handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface ITileCaptureHandler<T extends TileEntity> {
    /** Saves extra data for a TileEntity.
     * @param tile TileEntity to be captured
     * @param data NBTTagCompound to save data into
     * @return The NBT with data saved
     */
    NBTTagCompound saveExtraData(T tile, NBTTagCompound data);

    /** Loads extra data for a TileEntity.
     * @param tile TileEntity to be loaded
     * @param tag NBTTagCompound to load data from
     */
    void loadExtraData(T tile, NBTTagCompound tag);
}
