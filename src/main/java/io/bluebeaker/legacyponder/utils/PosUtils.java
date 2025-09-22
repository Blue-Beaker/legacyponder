package io.bluebeaker.legacyponder.utils;

import net.minecraft.util.math.BlockPos;

public class PosUtils {
    public static String blockPosToString(BlockPos pos){
        return pos.getX()+","+pos.getY()+","+ pos.getZ();
    }
    public static BlockPos blockPosFromString(String str) throws BlockPosFormatException {
        String[] split = str.split(",");
        if(split.length<3){
            throw new BlockPosFormatException();
        }
        return new BlockPos(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
    }
    public static class BlockPosFormatException extends Exception{

    }
}
