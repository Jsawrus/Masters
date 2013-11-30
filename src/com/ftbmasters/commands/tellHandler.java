package com.ftbmasters.commands;

import com.ftbmasters.misc.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tellHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tell")) {
			if (args.length <= 2) {
				String p2Name = args[0];

				if (Bukkit.getServer().getPlayerExact(p2Name) == null) {
					sender.sendMessage(ChatColor.RED + "Your message could not be delivered!");
					return true;
				} else {

					StringBuilder str = new StringBuilder();

					for (int i = 1; i < args.length; i++) {
						str.append(args[i] + " ");
					}

					String message = str.substring(0, str.length() - 1) + ".";

					new PrivateMessage((Player) sender, Bukkit.getServer().getPlayerExact(p2Name), message);
					return true;
				}
			}
		}
		return true;
	}

}