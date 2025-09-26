package io.bluebeaker.legacyponder.structure;

import io.bluebeaker.legacyponder.utils.Palette;
import io.bluebeaker.legacyponder.utils.PosUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.HashMap;
import java.util.Map;

public class PonderStructure {
    public final Palette<IBlockState> palette;

    public final HashMap<Long, NBTTagCompound> tileEntities;
    public int[][][] blocks;

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
    }

    public void addBlock(BlockPos pos, IBlockState state){
        if(!palette.contains(state)){
            palette.add(state);
        }
        blocks[pos.getZ()][pos.getY()][pos.getX()]=palette.indexOf(state);
    }

    public void addTileEntity(BlockPos pos, TileEntity tileEntity){
        NBTTagCompound tileentityNBT = tileEntity.writeToNBT(new NBTTagCompound());
        tileentityNBT.removeTag("x");
        tileentityNBT.removeTag("y");
        tileentityNBT.removeTag("z");
        tileentityNBT.setString("id", TileEntity.getKey(tileEntity.getClass()).toString());
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

    /** Capture blocks from a world to a structure
     * @param world The world to capture block from
     * @param pos1 One corner
     * @param pos2 Another corner
     * @return Captured structure
     */
    public static PonderStructure capture(World world, BlockPos pos1, BlockPos pos2){
        // Calculate 2 corner points
        BlockPos minPoint = new BlockPos(Math.min(pos2.getX(),pos1.getX()),
                Math.min(pos2.getY(),pos1.getY()),
                Math.min(pos2.getZ(),pos1.getZ()));
        BlockPos maxPoint = new BlockPos(Math.max(pos2.getX(),pos1.getX()),
                Math.max(pos2.getY(),pos1.getY()),
                Math.max(pos2.getZ(),pos1.getZ()));
        BlockPos size = maxPoint.subtract(minPoint).add(1,1,1);

        PonderStructure structure = new PonderStructure(size);
        for (BlockPos pos3:BlockPos.getAllInBox(minPoint,maxPoint)){
            BlockPos relative = pos3.subtract(minPoint);

            TileEntity tileEntity = world.getTileEntity(pos3);
            if(tileEntity!=null){
                structure.addTileEntity(relative,tileEntity);

            }
            BlockSnapshot blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pos3);
            IBlockState state = blockSnapshot.getCurrentBlock().getActualState(world,pos3);
            structure.addBlock(relative,state);

        }
        return structure;
    }

    public void putToWorld(World world, BlockPos pos){
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
                    }
                }
            }
        }
    }

    public NBTTagCompound saveToNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList blockList = new NBTTagList();
        NBTTagCompound tileEntities = new NBTTagCompound();
        NBTTagList palette = new NBTTagList();
        // Write Palette
        for (IBlockState entry:this.palette){
            palette.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), entry));
        }
        // Write Tileentities
        for (Map.Entry<Long, NBTTagCompound> entry : this.tileEntities.entrySet()) {
            BlockPos pos = BlockPos.fromLong(entry.getKey());
            tileEntities.setTag(PosUtils.blockPosToString(pos),entry.getValue());
        }
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

        FMLCommonHandler.instance().getDataFixer().writeVersionData(nbt);

        NBTTagList listSize = new NBTTagList();
        listSize.appendTag(new NBTTagInt(this.size.getX()));
        listSize.appendTag(new NBTTagInt(this.size.getY()));
        listSize.appendTag(new NBTTagInt(this.size.getZ()));
        nbt.setTag("palette", palette);
        nbt.setTag("blocks", blockList);
        nbt.setTag("tileEntities", tileEntities);
        nbt.setTag("size", listSize);
        nbt.setInteger("LegacyPonder_StructureVersion", 1);
        return nbt;
    }

    public static PonderStructure loadFromNBT(NBTTagCompound nbt){
        NBTTagList sizeList = nbt.getTagList("size", 3);
        NBTTagList blocksList = nbt.getTagList("blocks", 9);
        NBTTagList paletteList = nbt.getTagList("palette", 10);
        NBTTagCompound tileEntitiesTag = nbt.getCompoundTag("tileEntities");

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
        for (String posStr : tileEntitiesTag.getKeySet()) {
            BlockPos pos = PosUtils.blockPosFromString(posStr);
            if (pos != null) {
                structure.tileEntities.put(pos.toLong(),tileEntitiesTag.getCompoundTag(posStr));
            }
        }

        return structure;
    }
}
