package com.ftbmasters.commands;

import com.ftbmasters.Masters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class mastersCommand implements ICommandable {
	private Plugin plugin;

	public mastersCommand(Plugin plugin){
		this.plugin = plugin;
	}

    @Override
    public boolean run(CommandSender sender, Command command, String label, String[] args) {
	    Masters.fileHandlers();
        return true;
    }

    @Override
    public String getPermission() {
        return "op";
    }

	@Override
	public boolean needPlayer() {
		return false;
	}
}
