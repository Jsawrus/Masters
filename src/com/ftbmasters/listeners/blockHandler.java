package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class blockHandler implements Listener {

	private final Logger logger;

	public blockHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
		this.logger = pl.getLogger();
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void mossify(final PlayerInteractEvent evt) {
		if (evt.getPlayer() != null) {
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (evt.getClickedBlock().getType().equals(Material.COBBLESTONE)) {
					log("item in hand: " + evt.getPlayer().getItemInHand().getTypeId());
					if (evt.getPlayer().getItemInHand().getTypeId() == 1944) {
						log("Event fired item in hand 1944, Target COBBLE!");
						evt.getClickedBlock().setType(Material.MOSSY_COBBLESTONE);
						evt.getPlayer().getInventory().remove(new ItemStack(1944, 1));
					}
				}
			} 
		}
	}

	private void log(String str) {
		logger.log(Level.INFO, "[mossify]" +str);
	}
}
