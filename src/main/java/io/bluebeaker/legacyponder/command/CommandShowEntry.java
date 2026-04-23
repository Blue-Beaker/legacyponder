package io.bluebeaker.legacyponder.command;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.Entry;
import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;

public class CommandShowEntry extends CommandBase {
    @Override
    public String getName() {
        return "legacyponder-show";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("lpshow","unconfusion-show");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.legacyponder.show.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.legacyponder.show.usage");
        }
        String id = args[0];
        Entry entry = PonderRegistry.getEntries().get(id);
        if(entry==null){
            throw new EntryNotFoundException(I18n.format("commands.legacyponder.show.entrynotfound",id));
        }
        GuiUnconfusion screen = new GuiUnconfusion();
        screen.setEntryID(id);
        if (args.length>=2){
            screen.setCurrentPageID(parseInt(args[1]));
        }
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

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length < 2)
        {
            String match = args.length==1? args[0] : "";
            List<String> completions = new ArrayList<>();
            for (String id : PonderRegistry.getEntries().keySet()) {
                if(id.startsWith(match)){
                    completions.add(id);
                }
            }
            Collections.sort(completions);
            return completions;
        }
        return Collections.emptyList();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
