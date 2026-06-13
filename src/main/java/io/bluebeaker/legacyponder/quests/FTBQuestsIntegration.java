package io.bluebeaker.legacyponder.quests;

import com.feed_the_beast.ftblib.lib.icon.Icon;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FTBQuestsIntegration {
    public static TaskType VIEW_ENTRY;
    @SubscribeEvent
    public static void registerTasks(RegistryEvent.Register<TaskType> event)
    {
        event.getRegistry().register(VIEW_ENTRY = new TaskType(ViewEntryTask::new).setRegistryName("view_entry").setIcon(Icon.getIcon("legacyponder:textures/gui/icon/icon.png")));
    }
}
