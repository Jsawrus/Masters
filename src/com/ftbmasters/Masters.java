package com.ftbmasters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ftbmasters.listeners.*;
import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.listeners.blockHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
import com.ftbmasters.listeners.worldHandler;
import com.ftbmasters.Reloader;

import java.util.logging.Level;

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

    @Override
	public void onDisable() {
	    try {
			Reloader.enablePlugin(Bukkit.getServer().getConsoleSender(), Bukkit.getPluginManager().getPlugin("Masters"));
	    } catch (NoClassDefFoundError e) {
		    this.plugin.getLogger().log(Level.WARNING, "Reloader Class not found! maybe you issued /stop?");
	    }
	}

    @SuppressWarnings("unused")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandManager.dispatch(sender, command, label, args);
    }
	
	private void eventHandlers() {
		new blockHandler(this.plugin);
		new playerHandler(this.plugin);
		new serverHandler(this.plugin);
		new worldHandler(this.plugin);
        new teleportHandler(this.plugin);
	}

	public static void fileHandlers() {
		new fileHandler();
	}

}
