package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class signHandler implements Listener {
	private Plugin plugin;

	public signHandler(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onSignChange(SignChangeEvent event) {
		String line;
		for (int i = 0; i < 4; i++) {
			line = ChatColor.translateAlternateColorCodes('&', event.getLine(i));
			event.setLine(i, line);
		}
	}
}
