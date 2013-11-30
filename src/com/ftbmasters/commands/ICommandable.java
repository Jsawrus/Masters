package com.ftbmasters.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommandable {
    @SuppressWarnings("unused")
    private String permission;

    boolean run(CommandSender sender, Command command, String label, String[] args);
}
