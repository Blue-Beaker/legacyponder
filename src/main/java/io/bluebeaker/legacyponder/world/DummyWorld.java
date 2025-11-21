package io.bluebeaker.legacyponder.world;

import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;

public class DummyWorld extends World {
    public BlockPos templateSize = new BlockPos(0,0,0);

    protected PonderStructure currentStructure = null;
    protected BlockPos structureOffset = new BlockPos(0,0,0);

    public DummyWorld(boolean isClient){
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
                isClient
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
        postLoad();
    }

    public void postLoad(){
        tick();
        for (TileEntity tile : ((DummyChunkProvider)chunkProvider).getTileEntities()) {
            MinecraftForge.EVENT_BUS.post(new StructureTileEvent.AfterLoad(this,tile,tile.getPos()));
        }
    }

    private void clearWorld() {
        ((DummyChunkProvider)chunkProvider).clear();
        currentStructure=null;
        this.loadedEntityList.clear();
        this.unloadedEntityList.clear();
    }

    public void loadStructure(PonderStructure structure){
        StructureRenderManager.STRUCTURE_OFFSET=structure.pos;
        loadStructure(structure, StructureRenderManager.STRUCTURE_OFFSET);
    }

    @Override
    public void tick() {
        for (TileEntity tile : ((DummyChunkProvider)chunkProvider).getTileEntities()) {
            if(tile instanceof ITickable){
                ((ITickable)tile).update();
            }
        }
    }

    public void loadStructure(PonderStructure structure, BlockPos pos){
        clearWorld();
        currentStructure=structure;
        structureOffset=pos;
        templateSize=structure.getSize();
        structure.putToWorld(this,pos);
        postLoad();
    }
}
