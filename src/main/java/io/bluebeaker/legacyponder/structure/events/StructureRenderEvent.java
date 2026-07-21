package io.bluebeaker.legacyponder.structure.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class StructureRenderEvent extends Event {
    public final World world;
    StructureRenderEvent(World world){
        this.world = world;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public static class Pre extends StructureRenderEvent {
        public final Vec3d delta;
        public final float partialTicks;
        public Pre(World world, Vec3d delta, float partialTicks){
            super(world);
            this.delta = delta;
            this.partialTicks = partialTicks;
        }
    }
    public static class Post extends StructureRenderEvent {
        public final Vec3d delta;
        public final float partialTicks;
        public Post(World world, Vec3d delta, float partialTicks){
            super(world);
            this.delta = delta;
            this.partialTicks = partialTicks;
        }
    }


    public static class RenderBlock extends StructureRenderEvent {
        public final BlockPos pos;
        public RenderBlock(World world, BlockPos pos){
            super(world);
            this.pos = pos;
        }
    }

    public static class RenderBuffer extends RenderBlock{
        public final IBlockState state;
        public final BufferBuilder buffer;
        public RenderBuffer(World world, IBlockState state, BlockPos pos, BufferBuilder buffer) {
            super(world, pos);
            this.state = state;
            this.buffer = buffer;
        }
    }

    public static class RenderTile extends RenderBlock{
        public final TileEntity tileEntity;
        public final float partialTicks;
        public RenderTile(World world, TileEntity tileEntity, BlockPos pos, float partialTicks) {
            super(world,pos);
            this.tileEntity = tileEntity;
            this.partialTicks = partialTicks;
        }
    }
}
