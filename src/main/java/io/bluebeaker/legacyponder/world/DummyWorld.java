package io.bluebeaker.legacyponder.world;

import io.bluebeaker.legacyponder.structure.PonderStructure;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

public class DummyWorld extends World {
    public boolean isTickable = false;
    public BlockPos templateSize = new BlockPos(0,0,0);
    public DummyWorld(){
        this(
                new SaveHandlerMP(),
                new WorldInfo(
                        new WorldSettings(
                                0,
                                GameType.CREATIVE,
                                true,
                                false,
                                WorldType.DEFAULT
                        ),
                        "fake"
                ),
                new WorldProvider() {
                    @Override
                    public DimensionType getDimensionType() {
                        return DimensionType.OVERWORLD;
                    }
                },
                new Profiler(),
                true
        );
        chunkProvider = new DummyChunkProvider(this);
        this.provider.setWorld(this);
    }

    protected DummyWorld(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return chunkProvider;
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return chunkProvider.isChunkGeneratedAt(x,z);
    }

    public void loadTemplate(Template template){
        loadTemplate(template, StructureRenderManager.STRUCTURE_OFFSET,new PlacementSettings());
    }
    public void loadTemplate(Template template, BlockPos pos, PlacementSettings settings){
        clearWorld();
        templateSize=template.getSize();
        template.addBlocksToWorld(this,pos,settings);
    }

    private void clearWorld() {
        ((DummyChunkProvider)chunkProvider).clear();
        this.loadedEntityList.clear();
        this.unloadedEntityList.clear();
    }

    public void loadStructure(PonderStructure structure){
        loadStructure(structure, StructureRenderManager.STRUCTURE_OFFSET);
    }
    public void loadStructure(PonderStructure structure, BlockPos pos){
        clearWorld();
        templateSize=structure.getSize();
        structure.putToWorld(this,pos);
    }
}
