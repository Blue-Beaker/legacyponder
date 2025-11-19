package io.bluebeaker.legacyponder.structure;

import io.bluebeaker.legacyponder.LegacyPonder;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class StructureLoader {
    public static final Map<String, PonderStructure> structures = new HashMap<>();

    public static final File configDir = new File(Loader.instance().getConfigDir(),"legacyponder");
    public static final File structureDir = new File(configDir,"structures");

    public static void loadTemplates(){
        structures.clear();
        configDir.mkdirs();
        structureDir.mkdirs();
    }
    private static void readTemplateOrStructure(String name, File file){
        InputStream inputstream = null;
        try
        {
            inputstream = Files.newInputStream(file.toPath());

            NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(inputstream);

            if (!nbttagcompound.hasKey("DataVersion", 99))
            {
                nbttagcompound.setInteger("DataVersion", 500);
            }
            if(nbttagcompound.hasKey("LegacyPonder_StructureVersion")){
                // Custom structure format
                PonderStructure structure = PonderStructure.loadFromNBT(nbttagcompound);
                structures.put(name,structure);
            }else {
                structures.put(name, StructureConversion.convertTemplateNBTToStructure(nbttagcompound));
            }
        }
        catch (Throwable e)
        {
            LegacyPonder.getLogger().error("Failed to load structure:",e);
        }
        finally
        {
            IOUtils.closeQuietly(inputstream);
        }
    }

    public static void writeStructure(String name, PonderStructure structure){
        File file = new File(structureDir, name + ".nbt");
        OutputStream stream = null;
        try {
            stream=Files.newOutputStream(file.toPath());
            NBTTagCompound nbt = structure.saveToNBT();
            CompressedStreamTools.writeCompressed(nbt,stream);
            structures.put(name,structure);
        } catch (IOException e) {
            LegacyPonder.getLogger().error("Failed to save structure:",e);
        }finally {
            IOUtils.closeQuietly(stream);
        }
    }

    @Nullable
    public static PonderStructure getStructure(String id){
        if(!structures.containsKey(id)){
            readTemplateOrStructure(id,new File(structureDir,id+".nbt"));
            LegacyPonder.getLogger().info("Loaded structure {}",id);
        }
        return structures.get(id);
    }
}
