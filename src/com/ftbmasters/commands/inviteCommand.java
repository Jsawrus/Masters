package com.ftbmasters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class inviteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("invite")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please send commands from in-game.");
				return true;
			}

			if (sender.hasPermission("masters.invite")) {
				if (args.length == 1) {
					String player2 = args[0];

					if (Bukkit.getPlayer(player2) != null) {

						Player player = (Player) sender;

						double x = Math.round(player.getLocation().getBlockX() + 0.5D);
						double y = Math.round(player.getLocation().getBlockY() + 0.5D);
						double z = Math.round(player.getLocation().getBlockZ() + 0.5D);

						Bukkit.getPlayer(player2).sendMessage(ChatColor.GREEN + sender.getName() + " invites you to " + ChatColor.RED + "(" + ChatColor.WHITE + x + ChatColor.GREEN + ", " + y + ChatColor.GREEN + ", " + z + ChatColor.RED + ")");
						sender.sendMessage(ChatColor.GREEN + "You have invited " + player2 + " to your location");
					} else {
						sender.sendMessage(ChatColor.RED + "The player " + player2 + " doesn't exist.");
					}

				} else {
					sender.sendMessage(ChatColor.RED + "Correct usage: /invite <player>");
				}
				return true;
			}

		}

		return true;
	}

}
