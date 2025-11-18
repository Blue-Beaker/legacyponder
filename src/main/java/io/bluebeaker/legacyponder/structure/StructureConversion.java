package io.bluebeaker.legacyponder.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class StructureConversion {

    public static NBTTagCompound convertStructureToTemplateNBT(PonderStructure structure){
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList blockList = new NBTTagList();
        NBTTagList palette = new NBTTagList();

        for (IBlockState entry:structure.palette){
            palette.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), entry));
        }
        for (int z = 0; z < structure.size.getZ(); z++) {
            for (int y = 0; y < structure.size.getY(); y++) {
                for (int x = 0; x < structure.size.getX(); x++) {
                    NBTTagCompound nbtBlock = new NBTTagCompound();
                    NBTTagList posList = new NBTTagList();
                    posList.appendTag(new NBTTagInt(x));
                    posList.appendTag(new NBTTagInt(y));
                    posList.appendTag(new NBTTagInt(z));
                    nbtBlock.setTag("pos", posList);
                    nbtBlock.setInteger("state", structure.blocks[z][y][x]);

                    NBTTagCompound tile = structure.getTileEntity(x,y,z);
                    if(tile!=null){
                        nbtBlock.setTag("nbt", tile);
                    }
                    blockList.appendTag(nbtBlock);
                }
            }
        }
        net.minecraftforge.fml.common.FMLCommonHandler.instance().getDataFixer().writeVersionData(nbt); //Moved up for MC updating reasons.
        nbt.setTag("palette", palette);
        nbt.setTag("blocks", blockList);
        nbt.setTag("entities", new NBTTagList());
        NBTTagList listSize = new NBTTagList();
        listSize.appendTag(new NBTTagInt(structure.size.getX()));
        listSize.appendTag(new NBTTagInt(structure.size.getY()));
        listSize.appendTag(new NBTTagInt(structure.size.getZ()));
        nbt.setTag("size", listSize);
        nbt.setString("author", "");
        nbt.setInteger("DataVersion", 1343);
        return nbt;
    }

    public static PonderStructure convertTemplateNBTToStructure(NBTTagCompound template){
        NBTTagList sizeNBT = template.getTagList("size",3);
        // Init structure with size
        BlockPos size = getPos(sizeNBT);
        PonderStructure structure = new PonderStructure(size);

        if(size.getX()==0 && size.getY()==0 && size.getZ()==0) return structure;

        // Read palette
        List<IBlockState> palette = new ArrayList<>();
        NBTTagList paletteNBT = template.getTagList("palette",10);
        for (NBTBase state : paletteNBT) {
            if (!(state instanceof NBTTagCompound)) continue;
            palette.add(NBTUtil.readBlockState((NBTTagCompound) state));
        }

        // Convert blocks
        NBTTagList blocksNBT = template.getTagList("blocks",10);
        for (NBTBase blockTag : blocksNBT) {
            if (!(blockTag instanceof NBTTagCompound)) continue;

            NBTTagCompound block1 = (NBTTagCompound) blockTag;
            BlockPos pos = getPos(block1.getTagList("pos",3));
            int state = block1.getInteger("state");
            if(state>=palette.size()) return structure;

            structure.addBlock(pos,palette.get(state));
            if(block1.hasKey("nbt")){
                structure.addTileEntity(pos, block1.getCompoundTag("nbt"));
            }
        }

        return structure;
    }

    private static BlockPos getPos(NBTTagList sizeNBT) {
        return new BlockPos(sizeNBT.getIntAt(0), sizeNBT.getIntAt(1), sizeNBT.getIntAt(2));
    }
}
