package io.bluebeaker.legacyponder.structure;

import io.bluebeaker.legacyponder.utils.NBTTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3d;

public class StructureEntity {
    public final NBTTagCompound entityNBT;
    public final Vec3d relPos;

    public StructureEntity(NBTTagCompound entityNBT, Vec3d relPos) {
        this.entityNBT = entityNBT;
        this.relPos = relPos;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt){
        nbt.setTag("entityNBT",entityNBT);

        NBTTagList posTag = new NBTTagList();
        posTag.appendTag(new NBTTagDouble(relPos.x));
        posTag.appendTag(new NBTTagDouble(relPos.y));
        posTag.appendTag(new NBTTagDouble(relPos.z));
        nbt.setTag("pos",posTag);
        return nbt;
    }

    public static StructureEntity readFromNBT(NBTTagCompound nbt){
        NBTTagCompound entityNBT1 = nbt.getCompoundTag("entityNBT");
        NBTTagList pos1 = nbt.getTagList("pos", NBTTypes.Double);
        Vec3d pos2 = new Vec3d(pos1.getDoubleAt(0),pos1.getDoubleAt(1),pos1.getDoubleAt(2));
        return new StructureEntity(entityNBT1,pos2);
    }
}
