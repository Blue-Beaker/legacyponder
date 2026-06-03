package io.bluebeaker.legacyponder.command;

import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.HistoryTracker;
import io.bluebeaker.legacyponder.manual.page.PageStructure;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CommandShowStructure extends CommandBase {
    @Override
    public String getName() {
        return "legacyponder-showstructure";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.legacyponder.showstructure.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.legacyponder.showstructure.usage", new Object[0]);
        }
        String structureName = args[0];
        Entry entry = new Entry(I18n.format("temp_entry.legacyponder.structure.title",structureName), "temp_entry.legacyponder.structure.desc");
        entry.addPage(new PageStructure(structureName));
        String entryID = "_temp:"+structureName.replace(" ","_").replace("-","_").replace("/","_");
        ManualRegistry.getEntries().put(entryID,entry);

        GuiUnconfusion screen = new GuiUnconfusion();

        HistoryTracker.get().clear();
        screen.jumpTo(entryID);

        // Delay before showing screen, to make sure it opens
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Minecraft mc = Minecraft.getMinecraft();
                mc.addScheduledTask(() -> {
                    mc.displayGuiScreen(screen);});
            }
        },100);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
            if (args.length == 1)
            {
                return getListOfStringsMatchingLastWord(args, StructureLoader.getStructuresNames());
            }
        return Collections.emptyList();
    }
}
