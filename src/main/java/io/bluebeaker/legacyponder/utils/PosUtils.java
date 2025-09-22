package io.bluebeaker.legacyponder.utils;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class PosUtils {
    public static String blockPosToString(BlockPos pos){
        return pos.getX()+","+pos.getY()+","+ pos.getZ();
    }
    @Nullable
    public static BlockPos blockPosFromString(String str) {
        String[] split = str.split(",");
        if(split.length<3){
            return null;
        }
        return new BlockPos(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
    }
    public static class BlockPosFormatException extends Exception{

    }
}
