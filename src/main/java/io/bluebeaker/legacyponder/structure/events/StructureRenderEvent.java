package io.bluebeaker.legacyponder.structure.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class StructureRenderEvent extends Event {
    public final World world;
    public final BlockPos pos;
    StructureRenderEvent(World world, BlockPos pos){
        this.world = world;
        this.pos = pos;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public static class RenderBuffer extends StructureRenderEvent{
        public final IBlockState state;
        public final BufferBuilder buffer;
        public RenderBuffer(World world, IBlockState state, BlockPos pos, BufferBuilder buffer) {
            super(world, pos);
            this.state = state;
            this.buffer = buffer;
        }
    }

    public static class RenderTile extends StructureRenderEvent{
        public final TileEntity tileEntity;
        public RenderTile(World world, TileEntity tileEntity, BlockPos pos) {
            super(world,pos);
            this.tileEntity = tileEntity;
        }
    }
}
