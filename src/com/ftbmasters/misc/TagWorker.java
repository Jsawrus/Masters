package com.ftbmasters.misc;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class TagWorker implements Listener {

	public TagWorker(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onNameTag(final PlayerReceiveNameTagEvent event) {
		event.setTag(event.getNamedPlayer().getDisplayName());
	}

}
