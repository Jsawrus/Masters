package com.ftbmasters.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommandable {
    boolean run(CommandSender sender, Command command, String label, String[] args);
    String getPermission();

	boolean needPlayer();
}
