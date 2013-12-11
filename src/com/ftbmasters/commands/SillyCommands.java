package com.ftbmasters.commands;

import com.ftbmasters.utils.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SillyCommands {

	@Command(
			name="slap",
			aliases={"sl", "spal"},
			permission = "masters.slap",
			usage = "/<command> [User]",
			description = "Slap people around a bit with a large Primed TNT",
			player = true
	)
	public void slap(CommandSender sender, String[] args) {
		Player player = ((Player) sender).getPlayer();
		Player target;
		String targetName;

		if (args.length == 0) {
			target = player;
			targetName = target.getName();
		} else {
			target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				target = player;
				targetName = args[0];
			} else {
				targetName = target.getName();
			}
		}

		Bukkit.getServer().broadcastMessage(
				String.format("%s%s slaps %s%s%s around a bit with a large %sTNT%s!",
						player.getName(), ChatColor.GOLD, ChatColor.RESET, targetName, ChatColor.GOLD, ChatColor.RED, ChatColor.RESET));

		Location explosionLocation = target.getLocation();
		explosionLocation.setY(target.getLocation().getY() + 1);
		target.getWorld().createExplosion(
				explosionLocation, 0, false);
		target.damage(0.0D, target);
		target.setVelocity(new Vector(1, 1, 1));
		target.setFallDistance(0);
		target.setNoDamageTicks(40);
	}
}
