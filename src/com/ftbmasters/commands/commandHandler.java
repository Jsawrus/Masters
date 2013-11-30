package com.ftbmasters.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ftbmasters.Masters;

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
				player.sendMessage(ChatColor.GREEN + "Reloaded Masters Data.");
				Masters.fileHandlers();
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
				return true;
			}
		}
		
		
		return true;
	}

}
