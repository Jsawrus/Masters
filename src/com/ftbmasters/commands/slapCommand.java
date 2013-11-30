package com.ftbmasters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class slapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Please send commands from in-game.");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("slap")) {
			if (sender.hasPermission("masters.plugin.slap")) {

				Player player = (Player) sender;
				Player target;
				String targetName = "Greg";

				if (args.length == 0) {
					target = player;
					targetName = target.getName();
				} else {
					target = Bukkit.getServer().getPlayer(args[0]);
					if (target == null) {
						target = player;
						targetName = args[0];
					}
				}

				Bukkit.getServer().broadcastMessage(
						String.format("%s%s slaps %s%s%s around a bit with a large %sTNT%s!",
								player.getName(), ChatColor.GOLD, ChatColor.RESET, targetName, ChatColor.GOLD, ChatColor.RED, ChatColor.RESET));

				Location explosionLocation = target.getLocation();
				explosionLocation.setY(target.getLocation().getY() + 1); // head explosion!
				target.getWorld().createExplosion(
						explosionLocation, 0, false);
				target.damage(0.0D, target);
				target.setVelocity(new Vector(1, 1, 1));
				target.setNoDamageTicks(40);
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
			}
		}
		return true;
	}
}
