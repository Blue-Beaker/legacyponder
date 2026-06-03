package io.bluebeaker.legacyponder.structure;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.utils.FileUtils;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;

public class StructureLoader {
    public static final Map<String, PonderStructure> structures = new HashMap<>();

    public static final File configDir = new File(Loader.instance().getConfigDir(),LegacyPonder.MODID);
    public static final File structureDir = new File(configDir,"structures");

    public static void loadTemplates(){
        structures.clear();
        configDir.mkdirs();
        structureDir.mkdirs();
    }

    private static void readStructure(String name, File file){
        InputStream inputstream = null;
        try
        {
            inputstream = Files.newInputStream(file.toPath());

            readStructureFromStream(name, inputstream);
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
    private static void readStructureFromJar(ResourceLocation id){
        InputStream inputstream = null;
        try
        {
            inputstream = LegacyPonder.class.getResourceAsStream("/assets/" + id.getNamespace() + "/structures/" + id.getPath() + ".nbt");
            readStructureFromStream(id.toString(), inputstream);
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

    private static void readStructureFromStream(String name, InputStream inputstream) throws IOException {
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

    public static void writeStructure(String name, PonderStructure structure) throws IOException {
        File file = new File(structureDir, name + ".nbt");
        OutputStream stream = null;
        try {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            stream=Files.newOutputStream(file.toPath());
            NBTTagCompound nbt = structure.saveToNBT();
            CompressedStreamTools.writeCompressed(nbt,stream);
            structures.put(name,structure);
        } catch (IOException e) {
            LegacyPonder.getLogger().error("Failed to save structure:",e);
            throw e;
        }finally {
            IOUtils.closeQuietly(stream);
        }
    }

    @Nullable
    public static PonderStructure getStructure(String id){
        id=id.replace(" ","_");
        if(!structures.containsKey(id)){
            if(id.contains(":")){
                ResourceLocation res = new ResourceLocation(id);
                readStructureFromJar(res);
            }else {
                readStructure(id,new File(structureDir,id+".nbt"));
            }
            LegacyPonder.getLogger().info("Loaded structure {}",id);
        }
        return structures.get(id);
    }

    /** Get a list of all structure names, including loaded structures, and files from the config folder. The list is unmodifiable.
     * @return the structure names
     */
    public static List<String> getStructuresNames(){
        Set<String> list = new HashSet<>(structures.keySet());

        List<File> files = new ArrayList<>();
        FileUtils.listFilesRecursive(structureDir, files);
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".nbt")) {
                String path = file.getAbsolutePath();
                String parentPath = structureDir.getAbsolutePath();
                path = path.substring(parentPath.length()+1,path.length()-4);
                list.add(path.replace(File.separator,"/"));
            }
        }

        return Collections.unmodifiableList(new  ArrayList<>(list));
    }
}
