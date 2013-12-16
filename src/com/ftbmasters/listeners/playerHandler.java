package com.ftbmasters.listeners;

import com.ftbmasters.IO.fileHandler;
import com.ftbmasters.Masters;
import com.ftbmasters.misc.PlayerList;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.logging.Level;

public class playerHandler implements Listener {

	private Masters plugin;

	public playerHandler(Masters pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
		this.plugin = pl;
	}

	// black, d_blue, d_green, d_aqua, d_red, d_purple, gold, gray, d_gray, indigo, green, aqua, red, pink, yellow
	protected String colours = "123456789abc";

	private HashMap<String, String> address = new HashMap<>();
	private HashMap<String, Long> slept = new HashMap<>();

	@EventHandler (priority = EventPriority.NORMAL)
	public void login(final PlayerLoginEvent evt) {
		String ip = evt.getAddress().toString().substring(1, evt.getAddress().toString().length());
		String name = evt.getPlayer().getName();

        this.plugin.getLogger().log(Level.INFO, "Refreshing nameplate for " + evt.getPlayer().getName());
        this.plugin.getNameplateManager().refreshNameplate(evt.getPlayer());

		address.put(name, ip);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void death(final PlayerDeathEvent evt) {

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(evt.getEntity().getName()))
				break;

			pl.sendMessage(evt.getEntity().getDisplayName() + ChatColor.RED + evt.getDeathMessage().substring(evt.getEntity().getName().length(), 0));

		}

		evt.setDeathMessage(null);
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void join(final PlayerJoinEvent evt) {
		String name = evt.getPlayer().getDisplayName();
		evt.setJoinMessage(null);
		//int random = new Random().nextInt(100) / 10;
		//String playerColor = colours[random];
		//evt.getPlayer().setDisplayName(playerColor + evt.getPlayer().getName());
		//evt.getPlayer().setPlayerListName(playerColor + evt.getPlayer().getName());

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(evt.getPlayer().getName())) break;
			if (pl.hasPermission("group.mod")) {
				pl.sendMessage(evt.getPlayer().getDisplayName() + ChatColor.GREEN + " has joined the game " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
				if (!evt.getPlayer().hasPlayedBefore()) {
					pl.sendMessage(ChatColor.GREEN + "This is their first time on the server!");
				}
			} else {
				if (evt.getPlayer().hasPlayedBefore()) {
					pl.sendMessage(evt.getPlayer().getDisplayName() + ChatColor.GREEN + " has joined the game");
				} else {
					pl.sendMessage(evt.getPlayer().getDisplayName() + ChatColor.GREEN + " has joined the game for the first time");
				}
			}
		}


		// name colouring.

        if (!evt.getPlayer().hasPlayedBefore() && !evt.getPlayer().hasPermission("group.users"))
            evt.getPlayer().sendMessage(
                    ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("new_player").replaceAll("<player>", name)));

		new PlayerList(evt.getPlayer());
		evt.getPlayer().sendMessage(fileHandler.contents);

		new BukkitRunnable() {
			@Override
			public void run() {
				evt.getPlayer().playSound(evt.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 2500, 2500);
				for (int i = 0; i < 3; i++) {
					evt.getPlayer().playEffect(EntityEffect.WOLF_HEARTS);
				}
			}
		}.runTaskLater(plugin, 2);
	}


	@EventHandler (priority = EventPriority.NORMAL)
	public void quit(final PlayerQuitEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setQuitMessage(null);

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(evt.getPlayer().getName())) break;
			if (pl.hasPermission("masters.debug")) {
				pl.sendMessage(ChatColor.GREEN + name + " left the game " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
			} else {
				pl.sendMessage(ChatColor.GREEN + name + " has left the game");
			}
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void kick(final PlayerKickEvent evt) {
		String name = evt.getPlayer().getName();
		evt.setLeaveMessage(null);

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(evt.getPlayer().getName())) {
				break;
			}
			if (pl.hasPermission("masters.debug")) {
				pl.sendMessage(ChatColor.GREEN + name + " was kicked/crashed " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
			} else {
				pl.sendMessage(ChatColor.GREEN + name + " has left the game");
			}
		}
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void getInBed(final PlayerBedEnterEvent evt) {
		// super accurate nanotime ?!
		Long time = System.nanoTime();
		String name = evt.getPlayer().getName();

		if (Bukkit.getServer().getOnlinePlayers().length < 2) return;

		if (slept.containsKey(name)) {
			//check to see if currenttime - slepttime is different by 120m.
			long sleptTime = slept.get(name);
			long timeDifference = (time - sleptTime);

			int minutes = (int) ((int) timeDifference / 1000000000.0 / 60);

			if (minutes >= 120) {
				slept.remove(name);
				slept.put(name, time);

				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.sendMessage(evt.getPlayer().getDisplayName() + " slept through the night!");
				}

				evt.setCancelled(true);
				evt.getPlayer().teleport(new Location(evt.getPlayer().getWorld(), evt.getPlayer().getLocation().getX() + 0.5D, evt.getPlayer().getLocation().getY() + 1.0D, evt.getPlayer().getLocation().getZ() + 0.5D));
				evt.getPlayer().getWorld().setTime(0);

			} else {
				evt.getPlayer().sendMessage(ChatColor.RED + "Please try again in " + (120 - minutes) + " minutes!");
				evt.setCancelled(true);
			}


		} else {
			slept.put(name, time);
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				pl.sendMessage(evt.getPlayer().getDisplayName() + " slept through the night!");
			}


			evt.setCancelled(true);
			evt.getPlayer().getWorld().setTime(0);
			evt.getPlayer().teleport(new Location(evt.getPlayer().getWorld(), evt.getPlayer().getLocation().getX() + 0.5D, evt.getPlayer().getLocation().getY() + 1.0D, evt.getPlayer().getLocation().getZ() + 0.5D));
		}
	}
}
