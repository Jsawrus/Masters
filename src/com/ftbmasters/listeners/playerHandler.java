package com.ftbmasters.listeners;

import java.net.InetAddress;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class playerHandler implements Listener {
	
	public playerHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	private HashMap<String, InetAddress> address = new HashMap<String, InetAddress>();
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void login(final PlayerLoginEvent evt) {
		InetAddress ip = evt.getRealAddress();
		String name = evt.getPlayer().getName();
		
		System.out.println("Player " + name + " has logged in with IP Address " + ip + ".");
		address.put(name, ip);
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void join(final PlayerJoinEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setJoinMessage(null);
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.hasPermission("masters.plugin.op")) {
				pl.sendMessage(ChatColor.GREEN + name + " has joined the game " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
			} else {
				pl.sendMessage(ChatColor.GREEN + name + " has joined the game");
			}
		}
		
		address.remove(name);
	}

}
