package com.ftbmasters.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class itemCommand implements ICommandable {
	@SuppressWarnings ("unused")
	private Plugin plugin;
	private Logger logger;

	public itemCommand(Plugin plugin) {
		this.plugin = plugin;
		this.logger = this.plugin.getLogger();
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		String item;
		int stackSize = 1;
		Player target = null;

		// parse args
		// item
		if (args.length < 1) {
			return false;
		} else {
			item = args[0];
		}
		// itemStack
		if (args.length >= 2) {
			try {
				stackSize = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				if (sender.getServer().getPlayer(args[1]) != null) {
					target = sender.getServer().getPlayer(args[1]);
				}
			}
		}
		if (args.length > 2 && target == null) {
			target = sender.getServer().getPlayer(args[2]);
		}

		if (!(sender instanceof Player) && target == null){
			sender.sendMessage("Get a proper inventory and we can talk!");
			return false;
		}

		if (args.length == 2 ) {
			target = sender.getServer().getPlayer(args[1]);
		}

		if (target == null) {
			target = sender.getServer().getPlayer(sender.getName());
		}

		ItemStack myItem = getItem(item, stackSize);
		if (myItem == null) {
			sender.sendMessage("Can't create " + item);
			return true;
		}
		log("[item]" + sender.getName() + " gave " + target.getName() + " " + stackSize + "x" + item);
		target.getInventory().addItem(myItem);

		// check if Forge items have a name!
		target.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.RESET + ChatColor.AQUA +
				" gave you " +
				ChatColor.GOLD + stackSize + ChatColor.LIGHT_PURPLE + " " +
				(myItem.getType().name() != null ? myItem.getType().name() : args[0]) +
				ChatColor.RESET);

		return true;
	}

	@Override
	public String getPermission() {
		return "item.give"; // basic permissions
	}

	@Override
	public boolean needPlayer() {
		return false; // Command needs a player
	}

	@SuppressWarnings("deprecation")
	private ItemStack getItem(String name, int stackSize) {
		int id;
		int dmg = 0;
		String dataName = null;

		if (name.contains(":")) {
			String[] parts = name.split(":", 2);
			dataName = parts[1];
			name = parts[0];
		}

		try {
			id = Integer.parseInt(name);
		} catch (NumberFormatException e) {
			return null;
		}

		if (dataName != null) {
			try {
				dmg = Integer.parseInt(dataName);
			} catch (NumberFormatException e) {
				// not a number, ignore that
			}
		}

		return new ItemStack(id, stackSize, (short) dmg);
	}

	private void log (String str) {
		this.logger.log(Level.INFO, str);
	}

}
