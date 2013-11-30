package com.ftbmasters.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class teleportCommand implements ICommandable {
	@SuppressWarnings("unused")
	private Plugin plugin;

	public teleportCommand(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		// In the future ...
		return true;
	}

	@Override
	public String getPermission() {
		return "op";
	}

	@Override
	public boolean needPlayer() {
		return true;
	}

}
