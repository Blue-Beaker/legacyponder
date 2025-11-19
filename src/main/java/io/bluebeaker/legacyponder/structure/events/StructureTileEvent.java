package io.bluebeaker.legacyponder.structure.events;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;

public class StructureTileEvent extends Event {
    public final World world;
    public final TileEntity tileEntity;
    public final BlockPos pos;
    public StructureTileEvent(World world, TileEntity tileEntity, BlockPos pos){
        this.world = world;
        this.tileEntity = tileEntity;
        this.pos = pos;
    }

    public static class Save extends StructureTileEvent{
        public final NBTTagCompound extraData = new NBTTagCompound();
        public Save(World world, TileEntity tileEntity, BlockPos pos) {
            super(world, tileEntity, pos);
        }
    }

    public static class Load extends StructureTileEvent {
        public final @Nullable NBTTagCompound extraData;
        public Load(World world, TileEntity tileEntity, BlockPos pos, @Nullable NBTTagCompound extraData){
            super(world, tileEntity, pos);
            this.extraData =extraData;
        }
    }

    public static class AfterLoad extends StructureTileEvent {
        public AfterLoad(World world, TileEntity tileEntity, BlockPos pos){
            super(world, tileEntity, pos);
        }
    }
}
