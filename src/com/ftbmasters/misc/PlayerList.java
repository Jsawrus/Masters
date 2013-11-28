package com.ftbmasters.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerList {
	
	public PlayerList(Player player) {
		StringBuilder string = new StringBuilder();
		
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			string.append(pl.getDisplayName() + ", ");
		}
		
		player.sendMessage(ChatColor.DARK_GRAY + "Online Players (" + ChatColor.WHITE + Bukkit.getServer().getOnlinePlayers().length + ChatColor.DARK_GRAY + ")");
		player.sendMessage(string.toString().substring(0, string.toString().length() - 2) + ChatColor.WHITE + ".");
		
		string.setLength(0);
		string.replace(0, 0, " ");
	}

}
