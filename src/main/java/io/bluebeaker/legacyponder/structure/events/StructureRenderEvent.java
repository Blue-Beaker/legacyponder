package io.bluebeaker.legacyponder.structure.events;

import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class StructureRenderEvent extends StructureEvent<DummyWorld> {

    public StructureRenderEvent(DummyWorld world) {
        super(world);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public static class Pre extends StructureRenderEvent {
        public final Vec3d delta;
        public final float partialTicks;
        public NBTTagCompound extraData;
        public Pre(DummyWorld world, Vec3d delta, float partialTicks){
            super(world);
            this.delta = delta;
            this.partialTicks = partialTicks;
        }
    }
    public static class Post extends StructureRenderEvent {
        public final Vec3d delta;
        public final float partialTicks;
        public NBTTagCompound extraData;
        public Post(DummyWorld world, Vec3d delta, float partialTicks){
            super(world);
            this.delta = delta;
            this.partialTicks = partialTicks;
        }
    }


    public static class RenderBlock extends StructureRenderEvent {
        public final BlockPos pos;
        public RenderBlock(DummyWorld world, BlockPos pos){
            super(world);
            this.pos = pos;
        }
    }

    public static class RenderBuffer extends RenderBlock{
        public final IBlockState state;
        public final BufferBuilder buffer;
        public RenderBuffer(DummyWorld world, IBlockState state, BlockPos pos, BufferBuilder buffer) {
            super(world, pos);
            this.state = state;
            this.buffer = buffer;
        }
    }

    public static class RenderTile extends RenderBlock{
        public final TileEntity tileEntity;
        public final float partialTicks;
        public RenderTile(DummyWorld world, TileEntity tileEntity, BlockPos pos, float partialTicks) {
            super(world,pos);
            this.tileEntity = tileEntity;
            this.partialTicks = partialTicks;
        }
    }
}
