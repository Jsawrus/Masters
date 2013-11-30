package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class TagWorker implements Listener {
	
	public TagWorker(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNameTag(final PlayerReceiveNameTagEvent event) {
		if (event.getNamedPlayer().getName().equals(event.getPlayer().getName())) {
			event.setTag(ChatColor.RED + event.getPlayer().getDisplayName());
		}
	}
}
