package io.bluebeaker.legacyponder.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class NBTUtils {
    public static NBTTagCompound getTileEntityNBT(TileEntity tileEntity){
        NBTTagCompound tileentityNBT = tileEntity.writeToNBT(new NBTTagCompound());
        tileentityNBT.setString("id", TileEntity.getKey(tileEntity.getClass()).toString());
        return tileentityNBT;
    }
}
