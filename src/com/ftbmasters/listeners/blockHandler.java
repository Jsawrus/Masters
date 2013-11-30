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

public class blockHandler implements Listener {

	public blockHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void mossify(final PlayerInteractEvent evt) {
		if (evt.getPlayer() != null) {
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (evt.getClickedBlock().getType().equals(Material.COBBLESTONE)) {
					if (evt.getPlayer().getItemInHand().getTypeId() == 1944 || evt.getPlayer().getItemInHand().getTypeId() == 8070) {
						evt.getClickedBlock().setType(Material.MOSSY_COBBLESTONE);
						evt.getPlayer().getInventory().remove(new ItemStack(1944, 1));
					}
				}
			} 
		}
	}
}
