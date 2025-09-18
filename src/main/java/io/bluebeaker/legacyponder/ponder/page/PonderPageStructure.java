package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.utils.TemplateLoader;
import net.minecraft.world.gen.structure.template.Template;

public class PonderPageStructure extends PonderPageBase{
    private Template template;

    public PonderPageStructure(Template template){
        this.template=template;
    }
    public PonderPageStructure(String id){
        this.template= TemplateLoader.getTemplate(id);
    }
}
