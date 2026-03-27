package io.bluebeaker.legacyponder.structure;

import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import io.bluebeaker.legacyponder.utils.NBTTypes;
import io.bluebeaker.legacyponder.utils.NBTUtils;
import io.bluebeaker.legacyponder.utils.Palette;
import io.bluebeaker.legacyponder.utils.PosUtils;
import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.*;

public class PonderStructure {
    public final Palette<IBlockState> palette;

    public final Map<Long, NBTTagCompound> tileEntities;
    public final Map<Long, NBTTagCompound> extraDatas;

    public final List<StructureEntity> entities;

    public int[][][] blocks;

    public BlockPos pos = new BlockPos(0,0,0);

    public BlockPos getSize() {
        return size;
    }

    protected BlockPos size;

    public PonderStructure(int x, int y, int z){
        this(new BlockPos(x,y,z));
    }
    public PonderStructure(BlockPos size){
        this.size=size;
        blocks=new int[size.getZ()][size.getY()][size.getX()];

        palette=new Palette<>();

        palette.add(Blocks.AIR.getDefaultState());

        tileEntities=new HashMap<>();
        extraDatas =new HashMap<>();

        entities = new ArrayList<>();
    }

    public void addBlock(BlockPos pos, IBlockState state){
        if(!palette.contains(state)){
            palette.add(state);
        }
        blocks[pos.getZ()][pos.getY()][pos.getX()]=palette.indexOf(state);
    }

    public void addExtraData(BlockPos pos, NBTTagCompound data){
        this.extraDatas.put(pos.toLong(),data);
    }
    public NBTTagCompound getExtraData(int x, int y, int z) {
        return getExtraData(new BlockPos(x,y,z));
    }
    public NBTTagCompound getExtraData(BlockPos pos) {
        return extraDatas.get(pos.toLong());
    }

    public void addTileEntity(BlockPos pos, NBTTagCompound tileentityNBT){
        tileentityNBT.removeTag("x");
        tileentityNBT.removeTag("y");
        tileentityNBT.removeTag("z");
        this.tileEntities.put(pos.toLong(), tileentityNBT);
    }

    public NBTTagCompound getTileEntity(int x, int y, int z) {
        return getTileEntity(new BlockPos(x,y,z));
    }
    public NBTTagCompound getTileEntity(BlockPos pos) {
        return tileEntities.get(pos.toLong());
    }

    public IBlockState getBlockAt(int x, int y, int z){
        int i = blocks[z][y][x];
        if(i>=palette.size()) return Blocks.STRUCTURE_VOID.getDefaultState();
        return palette.get(i);
    }
    public IBlockState getBlockAt(BlockPos pos){
        return getBlockAt(pos.getX(),pos.getY(),pos.getZ());
    }

    public boolean isInStructure(BlockPos pos){
        return isInStructure(pos.getX(),pos.getY(),pos.getZ());
    }
    public boolean isInStructure(int x, int y, int z){
        return x >= 0 && y >= 0 && z >= 0 && x < size.getX() && y < size.getY() && z < size.getZ();
    }

    public List<StructureEntity> getEntities(){
        return new ArrayList<>(this.entities);
    }

    public void addEntity(NBTTagCompound entityNBT, Vec3d pos){
        this.entities.add(new StructureEntity(entityNBT,pos));
    }

    /** Capture entities from a world to a structure
     * @param world The world to capture block from
     * @param minPos Corner 1
     * @param maxPos Corner 2
     */
    public void captureEntities(World world, BlockPos minPos, BlockPos maxPos){
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(minPos,maxPos), e -> !(e instanceof EntityPlayer));
        this.entities.clear();
        for (Entity entity : list) {
            addEntityFromWorld(minPos, entity);
        }
    }

    /** Capture blocks from a world to a structure
     * @param world The world to capture block from
     * @param minPoint Corner with min x, y, z
     * @param maxPoint Corner with max x, y, z
     */
    public void captureBlocks(World world, BlockPos minPoint, BlockPos maxPoint){
        for (BlockPos pos:BlockPos.getAllInBox(minPoint,maxPoint)){
            BlockPos relative = pos.subtract(minPoint);

            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity!=null){
                // Attempt to save extra data from the tile
                StructureTileEvent.Save event = new StructureTileEvent.Save(world, tileEntity, pos, NBTUtils.getTileEntityNBT(tileEntity));
                MinecraftForge.EVENT_BUS.post(event);

                this.addTileEntity(relative,event.tileData);
                if(!event.extraData.isEmpty()){
                    this.addExtraData(relative,event.extraData);
                }
            }
            BlockSnapshot blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pos);
            IBlockState state = blockSnapshot.getCurrentBlock().getActualState(world,pos);
            this.addBlock(relative,state);
        }
    }

    /** Capture blocks from a world to a structure
     * @param world The world to capture block from
     * @param corner1 One corner
     * @param corner2 Another corner
     * @return Captured structure
     */
    public static PonderStructure capture(World world, BlockPos corner1, BlockPos corner2){
        return capture(world, corner1, corner2,false);
    }

    /** Capture blocks from a world to a structure
     * @param world The world to capture block from
     * @param corner1 One corner
     * @param corner2 Another corner
     * @param withEntities Whether to include entities
     * @return Captured structure
     */
    public static PonderStructure capture(World world, BlockPos corner1, BlockPos corner2, boolean withEntities){
        // Calculate 2 corner points
        BlockPos minPoint = new BlockPos(Math.min(corner2.getX(),corner1.getX()),
                Math.min(corner2.getY(),corner1.getY()),
                Math.min(corner2.getZ(),corner1.getZ()));
        BlockPos maxPoint = new BlockPos(Math.max(corner2.getX(),corner1.getX()),
                Math.max(corner2.getY(),corner1.getY()),
                Math.max(corner2.getZ(),corner1.getZ()));
        BlockPos size = maxPoint.subtract(minPoint).add(1,1,1);

        PonderStructure structure = new PonderStructure(size);
        structure.pos=minPoint;

        structure.captureBlocks(world,minPoint,maxPoint);

        if(withEntities){
            structure.captureEntities(world,minPoint,maxPoint);
        }

        return structure;
    }

    public void putToWorld(World world, BlockPos pos){
        boolean loadExtraData = world instanceof DummyWorld;
        for (int z = 0; z < this.size.getZ(); z++) {
            for (int y = 0; y < this.size.getY(); y++) {
                for (int x = 0; x < this.size.getX(); x++) {
                    // Skip structure void
                    if(this.getBlockAt(x,y,z).getBlock()==Blocks.STRUCTURE_VOID){
                        continue;
                    }
                    BlockPos absPos = pos.add(x, y, z);
                    world.setBlockState(absPos, this.getBlockAt(x, y, z));
                    NBTTagCompound tileEntity = this.getTileEntity(x, y, z);
                    if(tileEntity!=null){
                        TileEntity tileEntityIn = TileEntity.create(world, tileEntity);
                        world.setTileEntity(absPos, tileEntityIn);
                        // Update Tile with stored extra data
                        NBTTagCompound extraData = this.getExtraData(x, y, z);
                        if(loadExtraData && extraData !=null){
                            MinecraftForge.EVENT_BUS.post(new StructureTileEvent.Load(world,tileEntityIn,absPos,extraData));
                        }
                    }
                }
            }
        }

        for (StructureEntity structureEntity : entities) {
            putEntityToWorld(world, pos, structureEntity);
        }
    }

    private void addEntityFromWorld(BlockPos basePos, Entity entity){
        Vec3d relPos = entity.getPositionVector().subtract(new Vec3d(basePos));
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        entity.writeToNBTOptional(nbttagcompound);
        addEntity(nbttagcompound, relPos);
    }
    private void putEntityToWorld(World world, BlockPos basePos, StructureEntity structureEntity) {
        NBTTagCompound entityNBT = structureEntity.entityNBT.copy();
        Vec3d absPos = new Vec3d(basePos).add(structureEntity.relPos);
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.appendTag(new NBTTagDouble(absPos.x));
        nbttaglist.appendTag(new NBTTagDouble(absPos.y));
        nbttaglist.appendTag(new NBTTagDouble(absPos.z));
        entityNBT.setTag("Pos", nbttaglist);
        entityNBT.setUniqueId("UUID", UUID.randomUUID());

        Entity entity;
        try
        {
            entity = EntityList.createEntityFromNBT(entityNBT, world);
        }
        catch (Exception var15)
        {
            entity = null;
        }

        if (entity != null)
        {
            entity.setLocationAndAngles(absPos.x, absPos.y, absPos.z, entity.rotationYaw, entity.rotationPitch);
            world.spawnEntity(entity);
        }
    }

    public NBTTagCompound saveToNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList blockList = new NBTTagList();
        NBTTagCompound tileEntities = new NBTTagCompound();
        NBTTagCompound extraDatas = new NBTTagCompound();

        NBTTagList palette = new NBTTagList();
        // Write Palette
        for (IBlockState entry:this.palette){
            palette.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), entry));
        }

        // Write Tileentities
        writePosNBT(this.tileEntities, tileEntities);

        // Write Extra Data
        writePosNBT(this.extraDatas, extraDatas);

        // Write blocks
        for (int z = 0; z < this.size.getZ(); z++) {
            NBTTagList layer = new NBTTagList();
            for (int y = 0; y < this.size.getY(); y++) {
                NBTTagList row = new NBTTagList();
                for (int x = 0; x < this.size.getX(); x++) {
                    row.appendTag(new NBTTagInt(this.blocks[z][y][x]));
                }
                layer.appendTag(row);
            }
            blockList.appendTag(layer);
        }

        // Write entities
        NBTTagList entities = new NBTTagList();
        for (StructureEntity entity : this.entities) {
            entities.appendTag(entity.writeToNBT(new NBTTagCompound()));
        }

        FMLCommonHandler.instance().getDataFixer().writeVersionData(nbt);

        NBTTagList listSize = new NBTTagList();
        listSize.appendTag(new NBTTagInt(this.size.getX()));
        listSize.appendTag(new NBTTagInt(this.size.getY()));
        listSize.appendTag(new NBTTagInt(this.size.getZ()));
        nbt.setTag("palette", palette);
        nbt.setTag("blocks", blockList);
        if(!tileEntities.isEmpty())
            nbt.setTag("tileEntities", tileEntities);
        if(!extraDatas.isEmpty())
            nbt.setTag("extraDatas", extraDatas);
        if(!entities.isEmpty())
            nbt.setTag("entities", entities);
        nbt.setTag("size", listSize);
        nbt.setInteger("LegacyPonder_StructureVersion", 1);
        nbt.setString("pos",PosUtils.blockPosToString(this.pos));
        return nbt;
    }

    public static PonderStructure loadFromNBT(NBTTagCompound nbt){
        NBTTagList sizeList = nbt.getTagList("size", 3);
        NBTTagList blocksList = nbt.getTagList("blocks", 9);
        NBTTagList paletteList = nbt.getTagList("palette", 10);
        NBTTagCompound tileEntitiesTag = nbt.getCompoundTag("tileEntities");
        NBTTagCompound extraDatasTag = nbt.getCompoundTag("extraDatas");

        NBTTagList entitiesList = nbt.getTagList("entities", NBTTypes.Compound);

        BlockPos size = new BlockPos(sizeList.getIntAt(0),sizeList.getIntAt(1),sizeList.getIntAt(2));
        PonderStructure structure = new PonderStructure(size);

        // Read blocks
        for (int z = 0; z < size.getZ(); z++) {
            NBTTagList layer = (NBTTagList) blocksList.get(z);
            for (int y = 0; y < size.getY(); y++) {
                NBTTagList row = (NBTTagList) layer.get(y);
                for (int x = 0; x < size.getX(); x++) {
                    structure.blocks[z][y][x]=row.getIntAt(x);
                }
            }
        }
        // Read Palette
        for (NBTBase entry:paletteList){
            structure.palette.add(NBTUtil.readBlockState((NBTTagCompound) entry));
        }
        // Read Tileentities
        readPosNBT(tileEntitiesTag, structure.tileEntities);
        // Read ExtraData
        readPosNBT(extraDatasTag, structure.extraDatas);

        // Read Entities
        for (NBTBase nbtBase : entitiesList) {
            if (!(nbtBase instanceof NBTTagCompound)){continue;}
            structure.entities.add(StructureEntity.readFromNBT((NBTTagCompound) nbtBase));
        }

        if(nbt.hasKey("pos")){
            structure.pos=PosUtils.blockPosFromString(nbt.getString("pos"));
        }

        return structure;
    }


    private void writePosNBT(Map<Long, NBTTagCompound> posToNBT, NBTTagCompound nbt) {
        for (Map.Entry<Long, NBTTagCompound> entry : posToNBT.entrySet()) {
            BlockPos pos = BlockPos.fromLong(entry.getKey());
            nbt.setTag(PosUtils.blockPosToString(pos), entry.getValue());
        }
    }

    private static void readPosNBT(NBTTagCompound nbt, Map<Long, NBTTagCompound> posToNBT) {
        for (String posStr : nbt.getKeySet()) {
            BlockPos pos = PosUtils.blockPosFromString(posStr);
            if (pos != null) {
                posToNBT.put(pos.toLong(), nbt.getCompoundTag(posStr));
            }
        }
    }
}
