package com.ftbmasters.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PrivateMessage {
	
	public PrivateMessage(Player p1, Player p2, String msg) {
		p1.sendMessage(ChatColor.DARK_GRAY + "(" + ChatColor.WHITE + "to " + p2.getDisplayName() + ChatColor.DARK_GRAY + ") " + ChatColor.WHITE + msg);
		p2.sendMessage(ChatColor.DARK_GRAY + "(" + ChatColor.WHITE + "from " + p1.getDisplayName() + ChatColor.DARK_GRAY + ") " + ChatColor.WHITE + msg);
	}

}
