package com.ftbmasters.commands;

import com.ftbmasters.Reloader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class reloadCommand implements ICommandable {
	private Plugin plugin;

	public reloadCommand(Plugin plugin){
		this.plugin = plugin;
	}
	@Override
	public boolean run(CommandSender sender, Command cmd, String label, String[] args) {
		Reloader.reloadMasters((Player) sender);
		return true;
	}

	public String getPermission() {
		return "op";
	}

	@Override
	public boolean needPlayer() {
		return false;
	}
}
