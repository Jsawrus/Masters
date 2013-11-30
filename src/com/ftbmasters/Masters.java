package com.ftbmasters;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.commands.commandHandler;
import com.ftbmasters.commands.reloadHandler;
import com.ftbmasters.commands.slapCommand;
import com.ftbmasters.commands.tellHandler;
import com.ftbmasters.listeners.TagWorker;
import com.ftbmasters.listeners.blockHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
import com.ftbmasters.listeners.teleportHandler;
import com.ftbmasters.listeners.worldHandler;

public class Masters extends JavaPlugin {

	protected Plugin plugin;

	public void onEnable() {
		this.plugin = this;

		fileHandlers();
		eventHandlers();
		commandHandlers();
	}


	private void eventHandlers() {
		new blockHandler(this.plugin);
		new playerHandler(this.plugin);
		new serverHandler(this.plugin);
		new worldHandler(this.plugin);
		new teleportHandler(this.plugin);
		new TagWorker(this.plugin);
	}

	private void commandHandlers() {
		getCommand("masters").setExecutor(new commandHandler());
		getCommand("tell").setExecutor(new tellHandler());
		getCommand("slap").setExecutor(new slapCommand());
		getCommand("reload").setExecutor(new reloadHandler());
		getCommand("invite").setExecutor(new inviteCommand());
	}

	public static void fileHandlers() {
		new fileHandler();
	}

}
