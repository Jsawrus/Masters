package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class worldHandler implements Listener {
	
	public worldHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

}
