package com.ftbmasters.listeners;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class playerHandler implements Listener {
	
	public playerHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	private HashMap<String, InetAddress> address = new HashMap<String, InetAddress>();
	private HashMap<String, Long> slept = new HashMap<String, Long>();
	
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
				if (!evt.getPlayer().hasPlayedBefore()) {
					pl.sendMessage(ChatColor.GREEN + "This is their first time on the server!");
				}
			} else {
				if (evt.getPlayer().hasPlayedBefore()) {
				pl.sendMessage(ChatColor.GREEN + name + " has joined the game");
				} else {
					pl.sendMessage(ChatColor.GREEN + name + " has joined the game for the first time");
				}
			}
		}
		address.remove(name);
		
		// name colouring.
		// black, d_blue, d_green, d_aqua, d_red, d_purple, gold, gray, d_gray, indigo, green, aqua, red, pink, yellow
		String[] colours = {"&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&A", "&B", "&C", "&D", "&E"};
		int random = new Random().nextInt(13);
		
		evt.getPlayer().setDisplayName(colours[random] + evt.getPlayer().getName());
		evt.getPlayer().setPlayerListName(colours[random] + evt.getPlayer().getName());
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void quit(final PlayerQuitEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setQuitMessage(null);
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.hasPermission("masters.plugin.op")) {
				pl.sendMessage(ChatColor.GREEN + name + " left the game " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
			} else {
				pl.sendMessage(ChatColor.GREEN + name + " has left the game");
			}
		}
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void kick(final PlayerKickEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setLeaveMessage(null);
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.hasPermission("masters.plugin.op")) {
				pl.sendMessage(ChatColor.GREEN + name + " was kicked/crashed " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
			} else {
				pl.sendMessage(ChatColor.GREEN + name + " has left the game");
			}
		}
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void chat(final AsyncPlayerChatEvent evt) {
		// lets add in some colour
		
		String message = evt.getMessage();
		String name = evt.getPlayer().getDisplayName();
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			pl.sendMessage("<" + name + ChatColor.WHITE + "> " + message);
		}
		
		evt.setCancelled(true);
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void getInBed(final PlayerBedEnterEvent evt) {
		// super accurate nanotime ?!
		Long time = System.nanoTime();
		String name = evt.getPlayer().getName();
		
		if (Bukkit.getServer().getOnlinePlayers().length <= 1) return;
		
		if (slept.containsKey(name)) {
			//check to see if currenttime - slepttime is different by 120m.
			long sleptTime = slept.get(name);
			long timeDifference = (time - sleptTime);
			
			double minutes = (double)timeDifference / 1000000000.0 / 60;
			
			if (minutes >= 120) {
				slept.remove(name);
				slept.put(name, time);
				
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.sendMessage(ChatColor.RED + name + " slept through the night!");
				}
				
			} else {
				evt.getPlayer().sendMessage(ChatColor.RED + "Please try again in " + (120 - minutes) + " minutes!");
			}
			
			
		} else {
			slept.put(name, time);
			
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				pl.sendMessage(ChatColor.RED + name + " slept through the night!");
			}
		}
	}
}
