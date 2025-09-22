package io.bluebeaker.legacyponder.utils;

import ic2.core.block.TileEntityBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class WorkaroundBlockstateIC2 {

    @Nullable
    public static IBlockState getStateFromIC2TileEntity(TileEntity tileEntity){
        if(tileEntity instanceof TileEntityBlock){
            return ((TileEntityBlock)tileEntity).getBlockState();
        }
        else {
            return null;
        }
    }
}
