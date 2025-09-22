package io.bluebeaker.legacyponder.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandLegacyPonder extends CommandTreeBase {
    public CommandLegacyPonder(){
        this.addSubcommand(new CommandSaveStructure());
    }
    @Override
    public String getName() {
        return "legacyponder";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "";
    }
}
