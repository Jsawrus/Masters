package com.ftbmasters.commands;

import com.ftbmasters.Masters;
import com.ftbmasters.utils.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

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
}
