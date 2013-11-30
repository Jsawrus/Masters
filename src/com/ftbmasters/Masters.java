package com.ftbmasters;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ftbmasters.listeners.*;
import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.commands.commandHandler;
import com.ftbmasters.listeners.blockHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
import com.ftbmasters.listeners.worldHandler;
import com.ftbmasters.misc.Reloader;

public class Masters extends JavaPlugin {
	
	protected Plugin plugin;
	
	public void onEnable() {
		this.plugin = this;
		
		fileHandlers();
		eventHandlers();
		commandHandlers();
		
		// dirty hacky gross
		Bukkit.getServer().getConsoleSender().setOp(true);
	}
	
	public void onDisable() {
		Reloader.enablePlugin(Bukkit.getServer().getConsoleSender(), Bukkit.getPluginManager().getPlugin("Masters"));
	}
	
	private void eventHandlers() {
		new blockHandler(this.plugin);
		new playerHandler(this.plugin);
		new serverHandler(this.plugin);
		new worldHandler(this.plugin);
        new teleportHandler(this.plugin);
	}
	
	private void commandHandlers() {
		getCommand("masters").setExecutor(new commandHandler());
	}
	
	public static void fileHandlers() {
		new fileHandler();
	}

}
