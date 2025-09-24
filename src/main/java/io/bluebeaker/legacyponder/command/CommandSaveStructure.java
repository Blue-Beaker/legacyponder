package io.bluebeaker.legacyponder.command;

import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.structure.StructureConversion;
import io.bluebeaker.legacyponder.utils.Palette;
import io.bluebeaker.legacyponder.utils.TemplateLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandSaveStructure extends CommandBase {
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.legacyponder.save.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 7)
        {
            throw new WrongUsageException("commands.legacyponder.save.usage", new Object[0]);
        }
        BlockPos pos1 = parseBlockPos(sender, args, 0, false);
        BlockPos pos2 = parseBlockPos(sender, args, 3, false);
        String structureName = args[6];
        boolean replaceExisting = args.length>=8 && parseBoolean(args[7]);

        PonderStructure capture = PonderStructure.capture(sender.getEntityWorld(), pos1, pos2);
        if(!replaceExisting && TemplateLoader.getStructure(structureName)!=null){
            sender.sendMessage(new TextComponentTranslation("commands.legacyponder.save.fail_exist",structureName));
            return;
        }
        TemplateLoader.writeStructure(structureName,capture);
        sender.sendMessage(new TextComponentTranslation("commands.legacyponder.save.success",structureName));
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length > 0 && args.length <= 3)
        {
            return getTabCompletionCoordinate(args, 0, targetPos);
        }
        else if (args.length > 3 && args.length <= 6)
        {
            return getTabCompletionCoordinate(args, 3, targetPos);
        }
        return Collections.emptyList();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
