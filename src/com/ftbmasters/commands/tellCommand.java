package com.ftbmasters.commands;

import com.ftbmasters.misc.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class tellCommand implements ICommandable {
	private Plugin plugin;

	public tellCommand(Plugin plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		if (args.length <= 2)
			return false;

		String target = args[0];

		if (Bukkit.getServer().getPlayerExact(target) == null) {
			sender.sendMessage(ChatColor.RED + "Your message could not be delivered!");
			return true;
		}
		StringBuilder str = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			str.append(args[i] + " ");
		}

		String message = str.substring(0, str.length() - 1) + ".";

		new PrivateMessage(
				(Player) sender, Bukkit.getServer().getPlayerExact(target), message);
		return true;
	}

	@Override
	public String getPermission() {
		return "tell";
	}

	@Override
	public boolean needPlayer() {
		return false;
	}
}
