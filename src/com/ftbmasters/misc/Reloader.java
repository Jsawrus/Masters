package com.ftbmasters.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Reloader {
	
	public Reloader(Player pl, String str) {
		if (str.equalsIgnoreCase("reload")) {
			reloadPlugin(pl, Bukkit.getPluginManager().getPlugin("Masters"));
		} else {
			return;
		}
	}
	
    static PluginManager serverPM ;

	private void disablePlugin(CommandSender sender, Plugin targetPlugin) {
        String pluginName = targetPlugin.getDescription().getName();
        serverPM = Bukkit.getServer().getPluginManager();
        boolean error = false;

        if (!(sender.hasPermission("masters.plugin.op"))) {
            sender.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
            return;
        }
        if (targetPlugin.isEnabled() == false) {
            sender.sendMessage(ChatColor.RED + "This plugin isn't currently enabled.");
            error = true;
        }
        
        if (!error) {
            serverPM.disablePlugin(targetPlugin);
            if (!targetPlugin.isEnabled()) {
               sender.sendMessage(ChatColor.GREEN + pluginName + " has been disabled.");
            } else {
            	sender.sendMessage(ChatColor.RED + "An unknown error occurred while disabling " + pluginName + ".");
            }
        }
    }

    public static void enablePlugin(CommandSender sender, Plugin targetPlugin) {
        String pluginName = targetPlugin.getDescription().getName();
        boolean error = false;

        if (!(sender.hasPermission("plugman.admin"))) {
        	 sender.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
            return;
        }
        if (targetPlugin.isEnabled() == true) {
        	sender.sendMessage(ChatColor.RED + "This plugin is already enabled.");
            error = true;
        }
        if (!error) {
            serverPM.enablePlugin(targetPlugin);
            if (targetPlugin.isEnabled()) {
            	 sender.sendMessage(ChatColor.GREEN + pluginName + " has been enabled.");
            } else {
            	sender.sendMessage(ChatColor.RED + "An unknown error occurred while enabling " + pluginName + ".");
            }
        }
    }

    private void reloadPlugin(CommandSender sender, Plugin targetPlugin) {
        if (!(sender.hasPermission("plugman.admin"))) {
        	sender.sendMessage(ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action.");
            return;
        }
        disablePlugin(sender, targetPlugin);
        enablePlugin(sender, targetPlugin);
    }
	
}
