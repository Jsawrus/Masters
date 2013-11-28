package com.ftbmasters;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.commands.commandHandler;
import com.ftbmasters.listeners.blockHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
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
	}
	
	private void commandHandlers() {
		getCommand("masters").setExecutor(new commandHandler());
	}
	
	public static void fileHandlers() {
		new fileHandler(new File("plugins" + File.separator + "Masters" + File.separator + "motd.cfg"));
	}

}
