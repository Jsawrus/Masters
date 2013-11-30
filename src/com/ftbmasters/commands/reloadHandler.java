package com.ftbmasters.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ftbmasters.Reloader;

public class reloadHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Please send commands from in-game.");
			return true;
		}
		if (sender.hasPermission("masters.plugin.op")) {
		Reloader.reloadMasters((Player) sender);
		} else {
			sender.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
		}
		return false;
	}

}
