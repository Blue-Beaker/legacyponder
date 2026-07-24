package io.bluebeaker.legacyponder.quests;

import com.feed_the_beast.ftblib.lib.config.ConfigGroup;
import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftblib.lib.icon.IconAnimation;
import com.feed_the_beast.ftblib.lib.icon.ItemIcon;
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
import io.bluebeaker.legacyponder.utils.TextUtils;
import io.bluebeaker.legacyponder.utils.UnconfusionScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewEntryTask extends Task {
    public String entryID = "";
    public int page = 1;
    public boolean use_entry_icon = false;

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
    public Icon getAltIcon() {
        if (use_entry_icon) {
            Entry entry = ManualRegistry.get(entryID);
            if (entry == null) return super.getAltIcon();

            List<Icon> icons = new ArrayList<>();
            for(ItemStack stack : entry.getIcon()) {
                Icon icon = ItemIcon.getItemIcon(ItemHandlerHelper.copyStackWithSize(stack, 1));
                if (!icon.isEmpty()) {
                    icons.add(icon);
                }
            }
            if (icons.isEmpty()) {
                return super.getAltIcon();
            } else {
                return IconAnimation.fromList(icons, false);
            }
        }
        return super.getAltIcon();
    }

    @Override
    public void onButtonClicked(boolean canClick) {
        UnconfusionScreenHandler.openUnconfusionScreen(entryID, page);
        super.onButtonClicked(canClick);
    }

    @Override
    public void writeData(NBTTagCompound nbt)
    {
        super.writeData(nbt);
        nbt.setString("entryID", entryID);
        nbt.setInteger("page", page);
        nbt.setBoolean("use_entry_icon", use_entry_icon);
    }

    @Override
    public void readData(NBTTagCompound nbt)
    {
        super.readData(nbt);
        entryID = nbt.getString("entryID");
        page = nbt.getInteger("page");
        use_entry_icon = nbt.getBoolean("use_entry_icon");
    }

    @Override
    public void writeNetData(DataOut data)
    {
        super.writeNetData(data);
        data.writeString(entryID);
        data.writeInt(page);
        data.writeBoolean(use_entry_icon);
    }

    @Override
    public void readNetData(DataIn data)
    {
        super.readNetData(data);
        entryID = data.readString();
        page = data.readInt();
        use_entry_icon = data.readBoolean();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getConfig(ConfigGroup config)
    {
        super.getConfig(config);

        config.addEnum("entryID", ()->this.entryID,(id)->this.entryID=id, NameMap.createWithName("",
                (sender,id)-> new TextComponentTranslation(ManualRegistry.getEntryTitle(id)),
                ManualRegistry.getVisibleEntries().keySet().toArray(new String[0])));
        config.addInt("page",()->this.page,(p)->this.page=p,1,0,2147483647);
        config.addBool("use_entry_icon",()->use_entry_icon,(b)->this.use_entry_icon=b,false);
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
