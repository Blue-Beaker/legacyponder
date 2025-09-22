package io.bluebeaker.legacyponder.structure;

import ic2.core.block.TileEntityBlock;
import io.bluebeaker.legacyponder.utils.Palette;
import io.bluebeaker.legacyponder.utils.WorkaroundBlockstateIC2;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;

public class PonderStructure {
    public final Palette<IBlockState> palette;

    public final HashMap<BlockPos, NBTTagCompound> tileEntities;
    public int[][][] blocks;
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
        this.tileEntities.put(pos,tileEntity.serializeNBT());
    }

    public IBlockState getBlockAt(int x, int y, int z){
        int i = blocks[z][y][x];
        return palette.get(i);
    }
    public IBlockState getBlockAt(BlockPos pos){
        return getBlockAt(pos.getZ(),pos.getY(),pos.getX());
    }

    public static PonderStructure capture(World world, BlockPos pos1, BlockPos pos2){
        BlockPos minPoint = new BlockPos(Math.min(pos2.getX(),pos1.getX()),
                Math.min(pos2.getY(),pos1.getY()),
                Math.min(pos2.getZ(),pos1.getZ()));
        BlockPos maxPoint = new BlockPos(Math.max(pos2.getX(),pos1.getX()),
                Math.max(pos2.getY(),pos1.getY()),
                Math.max(pos2.getZ(),pos1.getZ()));
        BlockPos size = maxPoint.subtract(minPoint).add(1,1,1);

        PonderStructure structure = new PonderStructure(size);
        for (BlockPos pos3:BlockPos.getAllInBox(pos1,pos2)){
            BlockPos relative = pos3.subtract(minPoint);

            TileEntity tileEntity = world.getTileEntity(pos3);
            if(tileEntity!=null){
                structure.addTileEntity(relative,tileEntity);
            }

            IBlockState state = null;
            if(Loader.isModLoaded("ic2")){
                state = WorkaroundBlockstateIC2.getStateFromIC2TileEntity(tileEntity);
            }
            if(state==null){
                state=world.getBlockState(pos3);
            }
            structure.addBlock(relative,state);

        }
        return structure;
    }
}
