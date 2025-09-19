package io.bluebeaker.legacyponder.world;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DummyChunkProvider implements IChunkProvider {
    private final DummyWorld world;
    public final Map<ChunkPos, Chunk> chunks = new HashMap<>();

    public DummyChunkProvider(DummyWorld world){
        this.world=world;
    }

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z) {
        ChunkPos chunkPos = new ChunkPos(x, z);
        return chunks.get(chunkPos);
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        ChunkPos chunkPos = new ChunkPos(x, z);
        if (!chunks.containsKey(chunkPos)) {
            chunks.put(chunkPos, new DummyChunk(world, x, z));
        }
        return chunks.get(chunkPos);
    }

    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public String makeString() {
        return "String";
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z) {
        return chunks.containsKey(new ChunkPos(x, z));
    }

    public void clear() {
        chunks.clear();
    }
}
