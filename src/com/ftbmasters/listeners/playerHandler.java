package com.ftbmasters.listeners;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.misc.PlayerList;

public class playerHandler implements Listener {
	
	public playerHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	private HashMap<String, String> address = new HashMap<String, String>();
	private HashMap<String, Long> slept = new HashMap<String, Long>();
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void login(final PlayerLoginEvent evt) {
		String ip = evt.getAddress().toString().substring(1, evt.getAddress().toString().length());
		String name = evt.getPlayer().getName();
		
		address.put(name, ip);
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void join(final PlayerJoinEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setJoinMessage(null);
	
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName() == evt.getPlayer().getName()) break;
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
		String[] colours = {"&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&10", "&11", "&12"};
		int random = new Random().nextInt(11);
		
		evt.getPlayer().setDisplayName(replaceColour(colours[random]) + evt.getPlayer().getName());
		evt.getPlayer().setPlayerListName(replaceColour(colours[random]) + evt.getPlayer().getName());
		
		new PlayerList(evt.getPlayer());
		evt.getPlayer().sendMessage(ChatColor.DARK_GRAY + fileHandler.contents);
	}
	
	public static String replaceColour(String str) {
        str = str.replace("&1", ChatColor.RED.toString());
        str = str.replace("&2", ChatColor.DARK_RED.toString());
        str = str.replace("&3", ChatColor.YELLOW.toString());
        str = str.replace("&4", ChatColor.GOLD.toString());
        str = str.replace("&5", ChatColor.GREEN.toString());
        str = str.replace("&6", ChatColor.DARK_GREEN.toString());
        str = str.replace("&7", ChatColor.AQUA.toString());
        str = str.replace("&8", ChatColor.DARK_AQUA.toString());
        str = str.replace("&9", ChatColor.BLUE.toString());
        str = str.replace("&10", ChatColor.DARK_BLUE.toString());
        str = str.replace("&11", ChatColor.LIGHT_PURPLE.toString());
        str = str.replace("&12", ChatColor.DARK_PURPLE.toString());
        return str;
    }
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void quit(final PlayerQuitEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setQuitMessage(null);
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName() == evt.getPlayer().getName()) break;
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
			if (pl.getName() == evt.getPlayer().getName()) break;
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
					pl.sendMessage(evt.getPlayer().getDisplayName() + " slept through the night!");
				}
				
				evt.getPlayer().teleport(new Location(evt.getPlayer().getWorld(), evt.getPlayer().getLocation().getX() + 0.5D, evt.getPlayer().getLocation().getY() + 1.0D, evt.getPlayer().getLocation().getZ() + 0.5D));
				evt.getPlayer().getWorld().setTime(0);
				
			} else {
				Integer intg = new Integer((int) (120 - minutes));
				evt.getPlayer().sendMessage(ChatColor.RED + "Please try again in " + intg + " minutes!");
				evt.setCancelled(true);
			}
			
			
		} else {
			slept.put(name, time);
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				pl.sendMessage(evt.getPlayer().getDisplayName() + " slept through the night!");
			}
			
			evt.getPlayer().getWorld().setTime(0);
			evt.getPlayer().teleport(new Location(evt.getPlayer().getWorld(), evt.getPlayer().getLocation().getX() + 0.5D, evt.getPlayer().getLocation().getY() + 1.0D, evt.getPlayer().getLocation().getZ() + 0.5D));
		}
	}
}
