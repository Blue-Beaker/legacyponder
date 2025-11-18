package io.bluebeaker.legacyponder.command;

import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandLoadStructure extends CommandBase {
    @Override
    public String getName() {
        return "load";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.legacyponder.load.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 4)
        {
            throw new WrongUsageException("commands.legacyponder.load.usage", new Object[0]);
        }
        BlockPos pos = parseBlockPos(sender, args, 0, false);
        String structureName = args[3];

        PonderStructure structure = StructureLoader.getStructure(structureName);
        if(structure==null){
            sender.sendMessage(new TextComponentTranslation("commands.legacyponder.load.fail_unexist",structureName));
            return;
        }
        structure.putToWorld(sender.getEntityWorld(),pos);
        sender.sendMessage(new TextComponentTranslation("commands.legacyponder.load.success",structureName));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length > 0 && args.length <= 3)
        {
            return getTabCompletionCoordinate(args, 0, targetPos);
        }
        return Collections.emptyList();
    }
}
