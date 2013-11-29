package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class chatHandler implements Listener {
	
	public chatHandler(Plugin pl) {
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void chat(final AsyncPlayerChatEvent evt) {
		// lets add in some colour
		
		String message = evt.getMessage();
		String name = evt.getPlayer().getDisplayName();
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			pl.sendMessage("<" + name + ChatColor.WHITE + "> " + ChatColor.GRAY + message);
		}
		
		evt.setCancelled(true);
	}

}
