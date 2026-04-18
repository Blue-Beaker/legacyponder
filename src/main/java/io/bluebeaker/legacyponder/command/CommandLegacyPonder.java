package io.bluebeaker.legacyponder.command;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandLegacyPonder extends CommandTreeBase {
    public CommandLegacyPonder(){
        this.addSubcommand(new CommandSaveStructure());
        this.addSubcommand(new CommandLoadStructure());
        this.addSubcommand(new CommandTreeHelp(this));
    }
    @Override
    public String getName() {
        return "legacyponder";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        List<String> subcommands = this.getSubCommands().stream().map(ICommand::getName).collect(Collectors.toList());
        return I18n.format("commands.legacyponder.usage",String.join("|", subcommands));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("unconfusion");
    }
}
