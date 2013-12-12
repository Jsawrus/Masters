package com.ftbmasters.commands;

import com.ftbmasters.misc.PrivateMessage;
import com.ftbmasters.utils.commands.Command;
import com.ftbmasters.utils.commands.CommandArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommands {
	@Command (
			name = "tell",
			aliases = {"msg", "m", "t", "w"},
			permission = "masters.tell",
			usage = "/<command> <Player> message to send",
			description = "Send a private message to a Player"
	)
	public void tell(CommandSender sender, String[] args) {
		if (args.length <= 2)
			throw new CommandArgumentException("This command need at least a target player.");

		String target = args[0];

		if (Bukkit.getServer().getPlayerExact(target) == null)
			throw new CommandArgumentException(ChatColor.RED + "Your message could not be delivered!");

		StringBuilder str = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			str.append(args[i] + " ");
		}

		String message = str.substring(0, str.length() - 1) + ".";

		new PrivateMessage((Player) sender, Bukkit.getServer().getPlayerExact(target), message);
	}
}
