package com.ftbmasters;

import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.commands.AdminCommands;
import com.ftbmasters.commands.ChatCommands;
import com.ftbmasters.commands.InventoryCommands;
import com.ftbmasters.commands.SillyCommands;
import com.ftbmasters.commands.TeleportCommands;
import com.ftbmasters.listeners.chatHandler;
import com.ftbmasters.listeners.playerHandler;
import com.ftbmasters.listeners.serverHandler;
import com.ftbmasters.listeners.signHandler;
import com.ftbmasters.listeners.snowballHandler;
import com.ftbmasters.listeners.teleportHandler;
import com.ftbmasters.listeners.worldHandler;
import com.ftbmasters.recipes.ExpandingSnowBall;
import com.ftbmasters.utils.NameplateManager;
import com.ftbmasters.utils.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Masters extends JavaPlugin {

	protected Plugin plugin;
    protected CommandManager commandManager;
    private NameplateManager NameplateManager;

    public void onEnable() {
		this.plugin = this;
        getServer().getConsoleSender().sendMessage("[" + ChatColor.GOLD + "Masters" + ChatColor.RESET + "]" + ChatColor.AQUA + " enabled");

        this.saveDefaultConfig();

		commandManager = new CommandManager(this);

		this.commandManager.register(SillyCommands.class);
		this.commandManager.register(ChatCommands.class);
		this.commandManager.register(TeleportCommands.class);
		this.commandManager.register(AdminCommands.class);
		this.commandManager.register(InventoryCommands.class);

		fileHandlers();

        this.NameplateManager = new NameplateManager(this);

		eventHandlers();
	}

    public void onDisable() {
        this.NameplateManager.stopTimer();
        this.saveConfig();
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandManager.dispatch(sender, command, args);
    }
	
	private void eventHandlers() {
		// new blockHandler(this.plugin); // MCPC doesn't fire the event for mod items
		new playerHandler(this);
		new serverHandler(this);
		new worldHandler(this);
		new teleportHandler(this);
		new chatHandler(this);
		new signHandler(this);
		new snowballHandler(this, (new ExpandingSnowBall()).snowBall);
	}


	public static void fileHandlers() {
		new fileHandler();
	}

    public static Plugin getInstance() {
        return Bukkit.getServer().getPluginManager().getPlugin("Masters");
    }

    public NameplateManager getNameplateManager() {
        return this.NameplateManager;
    }
}
