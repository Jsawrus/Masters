package com.ftbmasters.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class itemCommand implements ICommandable {
	@SuppressWarnings ("unused")
	private Plugin plugin;

	public itemCommand(Plugin plugin) {
		this.plugin = plugin;
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
				if (args.length == 3) {
					target = sender.getServer().getPlayer(args[1]);
				}
			} catch (NumberFormatException e) {
				if (sender.getServer().getPlayer(args[1]) != null) {
					target = sender.getServer().getPlayer(args[1]);
				}
			}
		}

		if (!(sender instanceof Player) && target == null){
			sender.sendMessage("Get a proper inventory and we can talk!");
			return false;
		}

		if (target == null) {
			target = sender.getServer().getPlayer(sender.getName());
		}


		if (args.length == 2 ) {
			target = sender.getServer().getPlayer(args[2]);
		}

		ItemStack myItem = getItem(item, stackSize);

		target.getInventory().addItem(myItem);

		// check if Forge items have a name!
		target.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.RESET + ChatColor.AQUA +
				" gave you " +
				ChatColor.GOLD + stackSize + ChatColor.LIGHT_PURPLE +
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
		return true; // Command needs a player
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

}
