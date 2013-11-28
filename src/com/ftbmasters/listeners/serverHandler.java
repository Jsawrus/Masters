package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class serverHandler implements Listener {
	
	public serverHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

}
