package com.ftbmasters.commands;

import com.ftbmasters.utils.commands.Command;
import com.ftbmasters.utils.commands.CommandArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommands {
	@Command (
			name = "teleport",
			permission = "masters.teleport",
			usage = "/teleport [from] <to> | <x> <y> <z>",
			description = "Teleport you to someone or somewhere"
	)
	public void teleport(CommandSender sender, String[] args) {
		throw new CommandArgumentException(ChatColor.RED + "This is the future!~");
	}

	@Command (
			name = "invite",
			permission = "masters.invite",
			usage = "/invite <player>",
			description = "Invite a Player to your location"
	)
	public void invite(CommandSender sender, String[] args) throws CommandArgumentException{
		if (args.length != 1)
			throw new CommandArgumentException(ChatColor.RED + "Correct usage: /invite <player>");

		Player player2 = Bukkit.getPlayer(args[0]);

		if (player2 == null)
			throw new CommandArgumentException(ChatColor.RED + "The player " + args[0] + " doesn't exist.");


		Player player = ((Player) sender).getPlayer();
		Location location = player.getLocation();

		double x = Math.round(location.getBlockX() + 0.5D);
		double y = Math.round(location.getBlockY() + 0.5D);
		double z = Math.round(location.getBlockZ() + 0.5D);

        player2.sendMessage(ChatColor.GREEN + player.getDisplayName() + ChatColor.GREEN + " invites you to " + ChatColor.RED + "(" + ChatColor.GOLD + x + ", " + y + ", " + z + ChatColor.RED + ")");
		sender.sendMessage(ChatColor.GREEN + "You have invited " + player2.getDisplayName() + ChatColor.GREEN + " to your location");
	}
}
