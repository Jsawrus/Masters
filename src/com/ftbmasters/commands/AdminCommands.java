package com.ftbmasters.commands;

import com.ftbmasters.Masters;
import com.ftbmasters.utils.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminCommands {
	@Command (
			name = "masters",
			aliases = {"mas"},
			player = false,
			permission = "masters.admin",
			usage = "/<command> [save]",
			description = "Masters administration command\n"+
					"reloads or save configuration files."
	)
	public void masters(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "Reloading configuration files...");
		Masters.fileHandlers();
        if (args[0].equals("save"))
            Masters.getInstance().saveConfig();
        else
            Masters.getInstance().reloadConfig();
	}

    @Command (
            name = "worlds",
            aliases = {},
            player = false,
            permission = "masters.admin",
            usage = "/<command>",
            description = "List Bukkit worlds"
    )
    public void worldList(CommandSender sender, String[] args) {
        String r = ChatColor.AQUA + "";
        for (World w: Bukkit.getServer().getWorlds()) {
            r += w.getName() + ChatColor.RED + "("+ ChatColor.DARK_AQUA + w.getPlayers().size() + ChatColor.RED + ") " + ChatColor.AQUA;
        }

        sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "World List:");
        sender.sendMessage(r + ChatColor.RESET);
    }

    @Command (
            name = "setspawn",
            aliases = {},
            player = true,
            permission = "masters.admin",
            usage = "/<command>",
            description = "Set the default spawn point in the current world"
    )
    public void setSpawn(CommandSender sender, String[] args) {
        Player p = ((Player) sender).getPlayer();
        Location l = p.getLocation();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        p.getWorld().setSpawnLocation(x, y, z);

        sender.sendMessage(ChatColor.AQUA + "Spawn location set to " +
                ChatColor.RED + x + " " + y + " " + z +
                ChatColor.AQUA + " for world " + ChatColor.BOLD + l.getWorld().getName());
    }
}
