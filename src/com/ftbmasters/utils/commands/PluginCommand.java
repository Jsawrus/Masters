package com.ftbmasters.utils.commands;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;


/**
 * org.bukkit.command.PluginCommand Re-implementation
 */
public class PluginCommand extends org.bukkit.command.Command implements PluginIdentifiableCommand {

	private final Plugin owningPlugin;
	private final CommandExecutor executor;

	protected PluginCommand(Plugin owner, String name) {
		super(name);

		this.executor = owner;
		this.owningPlugin = owner;
		this.usageMessage = "";
	}

	/**
	 * Executes the command, returning its success
	 *
	 * @param sender Source object which is executing this command
	 * @param commandLabel The alias of the command used
	 * @param args All arguments passed to the command, split via ' '
	 * @return true if the command was successful, otherwise false
	 */
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		boolean success;

		if (!owningPlugin.isEnabled()) {
			return false;
		}

		if (!testPermission(sender)) {
			return true;
		}

		try {
			success = executor.onCommand(sender, this, commandLabel, args);
		} catch (Throwable ex) {
			throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + owningPlugin.getDescription().getFullName(), ex);
		}

		if (!success && usageMessage.length() > 0) {
			for (String line : usageMessage.replace("<command>", commandLabel).split("\n")) {
				sender.sendMessage(line);
			}
		}

		return success;
	}

	@Override
	public Plugin getPlugin() {
		return this.owningPlugin;
	}
}
