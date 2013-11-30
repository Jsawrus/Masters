package com.ftbmasters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ftbmasters.listeners.*;
import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.listeners.blockHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
import com.ftbmasters.listeners.worldHandler;
import com.ftbmasters.misc.Reloader;

public class Masters extends JavaPlugin {
	
	protected Plugin plugin;
    protected CommandManager commandManager = new CommandManager(this, "masters");

	public void onEnable() {
		this.plugin = this;

        this.commandManager.loadFromDescription(this.getDescription(), this.getClassLoader());

		fileHandlers();
		eventHandlers();

		// dirty hacky gross
        // and undoable :)
		// Bukkit.getServer().getConsoleSender().setOp(true);
	}

    @Override
	public void onDisable() {
		Reloader.enablePlugin(Bukkit.getServer().getConsoleSender(), Bukkit.getPluginManager().getPlugin("Masters"));
	}

    @SuppressWarnings("unused")
    public boolean onCommand(Player player, Command command, String label, String[] args) {
        return commandManager.dispatch(player, command, label, args);
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
