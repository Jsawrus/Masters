package com.ftbmasters.misc;

import org.bukkit.ChatColor;

public class Colorizer {
    private String str;
	
	public Colorizer(String str) {
		this.makeItFancy(str);
	}
	
	private void makeItFancy(String str) {
        str = str.replace("&1", ChatColor.RED.toString());
        str = str.replace("&2", ChatColor.DARK_RED.toString());
        str = str.replace("&3", ChatColor.YELLOW.toString());
        str = str.replace("&4", ChatColor.GOLD.toString());
        str = str.replace("&5", ChatColor.GREEN.toString());
        str = str.replace("&6", ChatColor.DARK_GREEN.toString());
        str = str.replace("&7", ChatColor.AQUA.toString());
        str = str.replace("&8", ChatColor.DARK_AQUA.toString());
        str = str.replace("&9", ChatColor.BLUE.toString());
        str = str.replace("&0", ChatColor.DARK_BLUE.toString());
        System.out.println(str);
        this.str = str;
	}

    @Override
    public String toString() {
        return this.str;
    }
}
