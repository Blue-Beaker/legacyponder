package io.bluebeaker.legacyponder.structure.events;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureCaptureEvent extends StructureEvent<World> {
    public final BlockPos minPoint;
    public final BlockPos maxPoint;

    public NBTTagCompound extraData = new NBTTagCompound();

    public StructureCaptureEvent(World world, BlockPos minPoint, BlockPos maxPoint) {
        super(world);
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }
}
