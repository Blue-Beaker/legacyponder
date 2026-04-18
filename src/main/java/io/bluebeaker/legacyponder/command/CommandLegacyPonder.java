package io.bluebeaker.legacyponder.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.Collections;
import java.util.List;

public class CommandLegacyPonder extends CommandTreeBase {
    public CommandLegacyPonder(){
        this.addSubcommand(new CommandSaveStructure());
        this.addSubcommand(new CommandLoadStructure());
    }
    @Override
    public String getName() {
        return "legacyponder";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("unconfusion");
    }
}
