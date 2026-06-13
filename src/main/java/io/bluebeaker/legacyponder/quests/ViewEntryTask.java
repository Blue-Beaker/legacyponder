package io.bluebeaker.legacyponder.quests;

import com.feed_the_beast.ftblib.lib.config.ConfigGroup;
import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.util.misc.NameMap;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestData;
import com.feed_the_beast.ftbquests.quest.task.BooleanTaskData;
import com.feed_the_beast.ftbquests.quest.task.Task;
import com.feed_the_beast.ftbquests.quest.task.TaskData;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ViewEntryTask extends Task {
    public String entryID = "";
    public int page = 1;
    public ViewEntryTask(Quest quest) {
        super(quest);
    }

    @Override
    public TaskType getType() {
        return FTBQuestsIntegration.VIEW_ENTRY;
    }

    @Override
    public TaskData<ViewEntryTask> createData(QuestData data) {
        return new BooleanTaskData<>(this, data);
    }

    @Override
    public void onButtonClicked(boolean canClick) {
        Entry entry = ManualRegistry.get(entryID);
        if(entry!=null){
            GuiUnconfusion screen = new GuiUnconfusion();
            screen.clearHistory();
            screen.jumpTo(entryID);
            screen.setCurrentPageID(page);
            Minecraft.getMinecraft().displayGuiScreen(screen);
        }
        super.onButtonClicked(canClick);
    }

    @Override
    public void writeData(NBTTagCompound nbt)
    {
        super.writeData(nbt);
        nbt.setString("entryID", entryID);
        nbt.setInteger("page", page);
    }

    @Override
    public void readData(NBTTagCompound nbt)
    {
        super.readData(nbt);
        entryID = nbt.getString("entryID");
        page = nbt.getInteger("page");
    }

    @Override
    public void writeNetData(DataOut data)
    {
        super.writeNetData(data);
        data.writeString(entryID);
        data.writeInt(page);
    }

    @Override
    public void readNetData(DataIn data)
    {
        super.readNetData(data);
        entryID = data.readString();
        page = data.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getConfig(ConfigGroup config)
    {
        super.getConfig(config);

        config.addEnum("entryID", ()->this.entryID,(id)->this.entryID=id, NameMap.create("", ManualRegistry.getEntries().keySet().toArray(new String[0])) );
        config.addString("entryID_input", () -> entryID, v -> entryID = v, "");
    }

    @Override
    public String getAltTitle()
    {
        Entry entry1 = ManualRegistry.get(entryID);
        if (entry1 != null)
        {
            return page==1? TextUtils.formatEntry(entry1) : TextUtils.formatEntry(entry1,page);
        }
        return super.getAltTitle();
    }
}
