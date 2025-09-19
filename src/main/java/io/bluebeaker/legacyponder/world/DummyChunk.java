package io.bluebeaker.legacyponder.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class DummyChunk extends Chunk {

    public DummyChunk(World worldIn, int x, int z) {
        super(worldIn, x, z);
    }

    @Override
    public void generateSkylightMap() {
    }
}
