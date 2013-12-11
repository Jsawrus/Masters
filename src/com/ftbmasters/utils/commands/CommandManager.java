package com.ftbmasters.utils.commands;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R3.CraftServer;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandManager {
	private final Plugin plugin;
	private final CraftServer server;
	private final Logger logger;

	private Map<Method, Object> commands = new HashMap<>();
	private Map<String, Method> aliases = new HashMap<>();

	/**
	 * Constructor
	 * @param plugin Bukkit Plugin
	 */
	public CommandManager(Plugin plugin){
		this.plugin = plugin;
		this.server = (CraftServer) plugin.getServer();
		this.logger = this.plugin.getLogger();
	}

	/**
	 * Register Methods of a Class as a Bukkit commands
	 * @param clazz Class
	 */
	public void register(Class<?> clazz) {

		List<org.bukkit.command.Command> registeredCommands = new LinkedList<>();

		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			this.log("Couldn't register command for " + clazz.getCanonicalName());
		}

		if (obj == null)
			return;

		for (Method method: clazz.getMethods()) {

			// non command method
			if (!method.isAnnotationPresent(Command.class))
				continue;

			Command command = method.getAnnotation(Command.class);

			if (isRegistered(command.name())) {
				log("Command " + command.name() + " from " + clazz.getCanonicalName() + " is already registered.");
				continue;
			}

			this.commands.put(method, obj);
			this.aliases.put(command.name(), method);
			for (String alias: command.aliases()) {
				this.aliases.put(alias, method);
			}

			// Create a new Command
			org.bukkit.command.Command pluginCommand;
			pluginCommand = new PluginCommand(plugin, command.name());
			pluginCommand.setAliases(Arrays.asList(command.aliases()));
			pluginCommand.setUsage(command.usage());
			pluginCommand.setDescription(command.description());
			pluginCommand.setPermission(command.permission());
			registeredCommands.add(pluginCommand);
		}

		server.getCommandMap().registerAll(plugin.getName(), registeredCommands);
	}

	/**
	 * Dispatch commands handling onCommand events
	 * @param sender Command Sender
	 * @param cmd Command Object
	 * @param args Command arguments
	 * @return standard boolean based on Exception handling
	 */
	public boolean dispatch(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
		boolean result = false;

		if (!this.isRegistered(cmd.getName())) {
			log("dispatched non-registered command: " + cmd.getName());
			return false;
		}

		Method method = this.aliases.get(cmd.getName());
		Object instance = this.commands.get(method);

		Object[] methodArgs = {sender, args};

		try {
			method.invoke(instance, methodArgs);
			result = true;
		} catch (InvocationTargetException | IllegalAccessException e) {
			log("Exception invoking method.");
			e.printStackTrace();
		} catch (CommandArgumentException ex) {
			sender.sendMessage(ex.getMessage());
		} catch (CommandException ex) {
			log("General CommandException: " + ex.getMessage());
			sender.sendMessage("\u00A7cError resolving command quantum physics equations");
		}
		return result;
	}

	private boolean isRegistered(String s) {
		return aliases.containsKey(s);
	}

	private void log(String s) {
		log(Level.SEVERE, s);
	}
	private void log(Level level, String s) {
		logger.log(level, s);
	}
}
