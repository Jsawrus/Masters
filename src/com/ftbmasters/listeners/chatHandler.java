package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class chatHandler implements Listener {
	
	public chatHandler(Plugin pl) {
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void chat(final AsyncPlayerChatEvent evt) {
		// lets add in some colour
		
		String message = evt.getMessage();
		String name = evt.getPlayer().getDisplayName();
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			pl.sendMessage("<" + name + ChatColor.WHITE + "> " + ChatColor.GRAY + message);
		}
		
		evt.setCancelled(true);
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void preProcess(final PlayerCommandPreprocessEvent evt) {
		String msg = evt.getMessage();
		
		if (msg.equalsIgnoreCase("/plugins") || msg.equalsIgnoreCase("/pl")) {
			if (evt.getPlayer().hasPermission("masters.plugin.op")) {
				return;
			} else {
				evt.getPlayer().sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
				return;
			}
		} else if (msg.equalsIgnoreCase("/rl") || msg.equalsIgnoreCase("/reload")) {
			if (evt.getPlayer().hasPermission("masters.plugin.op")) {
				return;
			} else {
				evt.getPlayer().sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
				return;
			}
		} else if (msg.equalsIgnoreCase("/ver") || msg.equalsIgnoreCase("/version")) {
			if (evt.getPlayer().hasPermission("masters.plugin.op")) {
				return;
			} else {
				evt.getPlayer().sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
				return;
			}
		}
	}
}
