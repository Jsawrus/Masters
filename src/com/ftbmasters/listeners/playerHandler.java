package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class playerHandler implements Listener {
	
	public playerHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

}
