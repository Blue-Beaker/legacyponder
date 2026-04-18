package io.bluebeaker.legacyponder.command;

import net.minecraft.command.CommandException;

public class EntryNotFoundException extends CommandException {
    public EntryNotFoundException(String message, Object... objects) {
        super(message, objects);
    }
}
