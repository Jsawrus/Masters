package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class snowballHandler implements Listener {
	private final Plugin plugin;

	protected static List<Material> replaceableMaterials = Arrays.asList(
			Material.GRASS, Material.AIR, Material.SNOW, Material.YELLOW_FLOWER, Material.RED_ROSE);
	private final ItemStack snowball;

	private List<UUID> snowBalls = new ArrayList<UUID>();

	public snowballHandler(Plugin plugin, ItemStack snowBall) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;

		this.snowball = snowBall;
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onSnowballHit (ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.SNOWBALL) {

			UUID uuid = event.getEntity().getUniqueId();
			if (this.snowBalls.contains(uuid)) {
				Location loc = event.getEntity().getLocation();

				loc.setY(loc.getBlockY() - 1);
				Block lBlock = loc.getBlock();
				if (lBlock.getType().isSolid()) {
					growSnow(loc);
				}
				this.snowBalls.remove(uuid);
			}
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onSnowballLauch (ProjectileLaunchEvent event) {
		Projectile ent = event.getEntity();

		if (event.getEntity().getShooter() instanceof Player) {
			ItemStack item = ((Player) event.getEntity().getShooter()).getItemInHand();
			if (item.isSimilar(this.snowball)) {
				this.snowBalls.add(ent.getUniqueId()); // Unsupported Operation Exception
			}
		}
	}

	private void growSnow(Location solidBlock) {
		double x = solidBlock.getBlockX();
		double y = solidBlock.getBlockY();
		double z = solidBlock.getBlockZ();

		Location snowBlock = new Location(
				solidBlock.getWorld(), solidBlock.getBlockX(), solidBlock.getBlockY(), solidBlock.getBlockZ());
		snowBlock.setY(y + 1);

		// WTB: lambda!
		for(int i = -1; -1 <= i && i <= 1; i++) {
			solidBlock.setX(x + i);
			snowBlock.setX(x + i);
			if (solidBlock.getBlock().getType().isSolid()
					&& replaceableMaterials.contains(snowBlock.getBlock().getType())) {
				snowBlock.getBlock().setType(Material.SNOW);
			}
		}

		solidBlock.setX(x);
		snowBlock.setX(x);
		for(int i = -1; -1 <= i && i <= 1; i++) {
			solidBlock.setZ(z + i);
			snowBlock.setZ(z + i);
			if (solidBlock.getBlock().getType().isSolid()
					&& replaceableMaterials.contains(snowBlock.getBlock().getType())) {
				snowBlock.getBlock().setType(Material.SNOW);
			}
		}
	}

	private void log(String s) {
		this.plugin.getLogger().log(Level.INFO, "[snowballHandler] " + s);
	}
}
