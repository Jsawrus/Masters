package com.ftbmasters;

import com.ftbmasters.commands.ICommandable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

class CommandManager {
	private String basePermission;
	private Plugin plugin;
	private Map<String, Class<? extends ICommandable>> commands = new Hashtable<>();

	public CommandManager(Plugin plugin, String basePermission) {
		this.plugin = plugin;
		this.basePermission = basePermission;
	}

	public void loadFromDescription(PluginDescriptionFile desc, ClassLoader loader) {
		Object object = desc.getCommands();
		if (object == null)
			return;
		@SuppressWarnings ("unchecked")
		Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) object;
		for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
			String label = entry.getKey();
			String classname = entry.getValue().get("class").toString();
			this.plugin.getLogger().log(Level.INFO, "Loaded '" + label + "' command with class '" + classname + "'.");

			try {
				Class<?> klass = Class.forName(classname, true, loader);
				Class<? extends ICommandable> commandClass = klass.asSubclass(ICommandable.class);
				addCommand(label, commandClass);
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to load command class for command /" + label);
			}
		}
	}

	public void addCommand(String label, Class<? extends ICommandable> klass) {
		commands.put(label, klass);
	}

	public boolean dispatch(CommandSender sender, Command command, String label, String[] args) {
		label = command.getName();
		if (!commands.containsKey(label))
			return false;
		boolean handled = true;
		Class<? extends ICommandable> klass = commands.get(label);
		try {
			Constructor<? extends ICommandable> ctor = klass.getConstructor(Plugin.class);
			ICommandable c = ctor.newInstance(plugin);
			if (!sender.hasPermission(this.basePermission + "." + c.getPermission())) {
				sender.sendMessage(
						ChatColor.RED + "Sorry, you lack sufficient privileges to perform this action." + ChatColor.RESET);
				return true;
			}
			if (c.needPlayer() && !(sender instanceof Player)) {
				sender.sendMessage("Please send commands from in-game.");
				return true;
			}
			handled = c.run(sender, command, label, args);
		} catch (NoSuchMethodException e) {
			System.out.println("No constructor that accepts a Plugin.");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("Error while creating a Commandable object.");
		} catch (IllegalAccessException e) {
			System.out.println("Illegal access to the Commandable object constructor.");
		} catch (InvocationTargetException e) {
			System.out.println(e.getCause().getMessage());
		}
		return handled;
	}
}
