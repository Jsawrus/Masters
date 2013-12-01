package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.Plugin;

public class worldHandler implements Listener {

	public worldHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void weather(final WeatherChangeEvent evt) {
		// until i can figure out how to manage weather..
		evt.setCancelled(true);
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void unload(final WorldUnloadEvent evt) {
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.hasPermission("masters.plugin.op")) {
				//pl.sendMessage(ChatColor.RED + "World " + evt.getWorld().getName() + " has been unloaded.");
			}
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void initialize(final WorldInitEvent evt) {
		StringBuilder players = new StringBuilder();

		for (Player pls : evt.getWorld().getPlayers()) {
			players.append(pls);
		}

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.hasPermission("masters.plugin.op")) {
				//pl.sendMessage(ChatColor.RED + "World " + evt.getWorld().getName() + " has been loaded by " + players + ".");
			}
		}

		//set string to null
		players.setLength(0);
		players.replace(0, 0, " ");
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void load(final WorldSaveEvent evt) {
	    /*for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.hasPermission("masters.plugin.op")) {
				pl.sendMessage(ChatColor.RED + "World " + evt.getWorld().getName() + " has been saved.");
			}
		}*/
	}

}
