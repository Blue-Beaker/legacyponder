package io.bluebeaker.legacyponder.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;

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

                    NBTTagCompound tile = structure.tileEntities.get(new BlockPos(x, y, z));
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
}
