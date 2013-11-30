package com.ftbmasters;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.listeners.blockHandler;
import com.ftbmasters.listeners.chatHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
import com.ftbmasters.listeners.teleportHandler;
import com.ftbmasters.listeners.worldHandler;
import com.ftbmasters.misc.TagWorker;

public class Masters extends JavaPlugin {

	protected Plugin plugin;
    protected CommandManager commandManager;

	public Masters() {
		super();
		commandManager = new CommandManager(this, "masters");
	}

	public void onEnable() {
		this.plugin = this;

        this.commandManager.loadFromDescription(this.getDescription(), this.getClassLoader());

		fileHandlers();
		eventHandlers();
	}


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandManager.dispatch(sender, command, label, args);
    }
	
	private void eventHandlers() {
		new blockHandler(this.plugin);
		new playerHandler(this.plugin);
		new serverHandler(this.plugin);
		new worldHandler(this.plugin);
		new teleportHandler(this.plugin);
		new TagWorker(this.plugin);
		new chatHandler(this.plugin);
	}


	public static void fileHandlers() {
		new fileHandler();
	}

}
