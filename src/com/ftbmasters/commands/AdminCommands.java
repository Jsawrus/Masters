package com.ftbmasters.commands;

import com.ftbmasters.Masters;
import com.ftbmasters.utils.commands.Command;
import org.bukkit.command.CommandSender;

public class AdminCommands {
	@Command (
			name = "masters",
			aliases = {"mas"},
			player = false,
			permission = "masters.admin",
			usage = "/<command>",
			description = "Masters administration command\n"+
					"reloads configuration files."
	)
	public void masters(CommandSender sender, String[] args) {
		Masters.fileHandlers();
	}
}
