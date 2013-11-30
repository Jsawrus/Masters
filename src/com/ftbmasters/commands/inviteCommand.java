package com.ftbmasters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class inviteCommand implements ICommandable {
	private Plugin plugin;

	public inviteCommand(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			String player2 = args[0];

			if (Bukkit.getPlayer(player2) != null) {

				Player player = (Player) sender;

				double x = Math.round(player.getLocation().getBlockX() + 0.5D);
				double y = Math.round(player.getLocation().getBlockY() + 0.5D);
				double z = Math.round(player.getLocation().getBlockZ() + 0.5D);

				Bukkit.getPlayer(player2).sendMessage(ChatColor.GREEN + ((Player) sender).getDisplayName() + ChatColor.GREEN + " invites you to " + ChatColor.RED + "(" + ChatColor.GOLD + x + ", " + y + ", " + z + ChatColor.RED + ")");
				sender.sendMessage(ChatColor.GREEN + "You have invited " + Bukkit.getPlayer(player2).getDisplayName() + ChatColor.GREEN + " to your location");
			} else {
				sender.sendMessage(ChatColor.RED + "The player " + player2 + " doesn't exist.");
			}

		} else {
			sender.sendMessage(ChatColor.RED + "Correct usage: /invite <player>");
		}
		return true;
	}

	@Override
	public String getPermission() {
		return "misc.invite";
	}

	@Override
	public boolean needPlayer() {
		return true;
	}
}
