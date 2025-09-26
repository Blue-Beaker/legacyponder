package io.bluebeaker.legacyponder.world;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class LoadTileEntityEvent extends Event {
    public final World world;
    public final TileEntity tileEntity;
    public final BlockPos pos;
    public LoadTileEntityEvent(World world, TileEntity tileEntity, BlockPos pos){
        this.world = world;
        this.tileEntity = tileEntity;
        this.pos = pos;
    }
}
