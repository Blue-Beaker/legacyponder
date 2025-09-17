package io.bluebeaker.legacyponder.utils;

import io.bluebeaker.legacyponder.LegacyPonder;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class TemplateLoader {

    public static final HashMap<String, Template> templates = new HashMap<>();
    public static void loadTemplates(){
        templates.clear();
        File configDir = new File(Loader.instance().getConfigDir(),"legacyponder");
        configDir.mkdirs();
        File structureDir = new File(configDir,"structures");
        structureDir.mkdirs();
        File[] files = structureDir.listFiles();
        if(files==null) return;
        for (File file : files) {
            String name = file.getName();
            if(name.endsWith(".nbt")){
                name=name.substring(0,name.length()-4);
                Template template = readTemplate(file);
                if(template!=null){
                    templates.put(name,template);
                }
            }
        }
        LegacyPonder.getLogger().info("Loaded {} structures: {}",templates.size(),templates.keySet());
    }
    private static Template readTemplate(File file){
        InputStream inputstream = null;
        try
        {
            inputstream = Files.newInputStream(file.toPath());

            NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(inputstream);

            if (!nbttagcompound.hasKey("DataVersion", 99))
            {
                nbttagcompound.setInteger("DataVersion", 500);
            }

            Template template = new Template();
            template.read(nbttagcompound);
            return template;
        }
        catch (Throwable e)
        {
            LegacyPonder.getLogger().error("Failed to load structure:",e);
        }
        finally
        {
            IOUtils.closeQuietly(inputstream);
        }

        return null;
    }

    public static Template getTemplate(String id){
        return templates.get(id);
    }
}
