package com.ftbmasters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("masters")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Make sure commands are sent from in-game."); 
				return false;
			}
			
			Player player = (Player) sender;
			
			if (player.hasPermission("masters.plugin.op")) {
				// for lack of anything useful at this time..
				player.sendMessage(ChatColor.GREEN + "This server is running " + Bukkit.getVersion() + ".");
			} else {
				player.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
			}
		}
		return false;
	}

}
