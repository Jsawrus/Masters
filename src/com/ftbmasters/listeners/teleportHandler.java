package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class teleportHandler implements Listener {
	Plugin plugin;

	public teleportHandler(Plugin pl) {
		this.plugin = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void teleport(final PlayerTeleportEvent ev) {
		if (ev.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL &&
				ev.getPlayer().hasPermission("masters.enderport")) {
			Location from = ev.getFrom();
			Location to = ev.getTo();

			// teleported to the same block? (almost)
			if (from.distance(to) < 0.8) {
				ev.setCancelled(true);
				this.teleportToBed(ev.getPlayer());
			}
		}
	}

	private void teleportToBed(final Player player) {
		Location targetLocation = player.getBedSpawnLocation();
		String message;

		player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 20, 1, true));
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 36 * 20, 1, true));
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 6 * 20, 4, true));
		player.playSound(player.getLocation(), Sound.ENDERMAN_SCREAM, 4, 1);

		if (targetLocation == null) {
			targetLocation = Bukkit.getWorld("world").getSpawnLocation();

			message = ChatColor.LIGHT_PURPLE + "You find yourself in the world where all began." + ChatColor.WHITE + " Was it a dream? ...";
		} else {
			message = ChatColor.LIGHT_PURPLE + "You find yourself to your bed." + ChatColor.WHITE + " Was it a dream? ...";
		}

		final Location finalTargetLocation = targetLocation;
		final String finalMessage = message;
		new BukkitRunnable() {
			@Override
			public void run() {
				player.teleport(
						finalTargetLocation,
						PlayerTeleportEvent.TeleportCause.PLUGIN);

				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 6, 1);
				player.sendMessage(finalMessage);

				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 4, true));
			}
		}.runTaskLater(this.plugin, 3 * 20); // teleport after 3 seconds
	}
}
