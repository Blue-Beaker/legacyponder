package io.bluebeaker.legacyponder.manual.gui;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPageDefault<T extends PageBase> extends GuiInfoPage<T> {
    public GuiPageDefault(GuiUnconfusion parent, T page) {
        super(parent, page);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        page.draw(parent,mouseX,mouseY,partialTicks);
    }
}
