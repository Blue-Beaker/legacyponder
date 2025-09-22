package io.bluebeaker.legacyponder.utils;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class TemplateLoader {

    public static final HashMap<String, Template> templates = new HashMap<>();
    public static final HashMap<String, PonderStructure> structures = new HashMap<>();

    public static final File configDir = new File(Loader.instance().getConfigDir(),"legacyponder");
    public static final File structureDir = new File(configDir,"structures");

    public static void loadTemplates(){
        templates.clear();
        structures.clear();
        configDir.mkdirs();
        structureDir.mkdirs();
        File[] files = structureDir.listFiles();
        if(files==null) return;
        for (File file : files) {
            String name = file.getName();
            if(name.endsWith(".nbt")){
                name=name.substring(0,name.length()-4);
                readTemplateOrStructure(name,file);
            }
        }
        LegacyPonder.getLogger().info("Loaded {} structures: {}",templates.size(),templates.keySet());
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
                // Vanilla structure
                Template template = new Template();
                template.read(nbttagcompound);
                templates.put(name, template);
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

    public static Template getTemplate(String id){
        return templates.get(id);
    }
    public static PonderStructure getStructure(String id){
        return structures.get(id);
    }
}
