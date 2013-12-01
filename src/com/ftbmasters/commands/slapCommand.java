package com.ftbmasters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class slapCommand implements ICommandable {
	private Plugin plugin;

	public slapCommand(Plugin plugin){
		this.plugin = plugin;
	}

    public boolean run(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Please send commands from in-game.");
			return true;
		}

        Player player = ((Player) sender).getPlayer();
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
        return true;
    }

    @Override
    public String getPermission() {
        return "slap";
    }

	@Override
	public boolean needPlayer() {
		return true;
	}
}
