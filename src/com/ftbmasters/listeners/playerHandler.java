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

        String message = String.format("%s%s%s", evt.getEntity().getDisplayName(), ChatColor.RED, evt.getDeathMessage().substring(evt.getEntity().getName().length()+4, evt.getDeathMessage().length()));

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.sendMessage(message);
		}

        Bukkit.getConsoleSender().sendMessage(message);
        evt.setDeathMessage(null);
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void join(final PlayerJoinEvent evt) {
		String name = evt.getPlayer().getDisplayName();
		evt.setJoinMessage(null);

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(evt.getPlayer().getName())) break;
			if (pl.hasPermission("group.mod")) {
				pl.sendMessage(evt.getPlayer().getDisplayName() + ChatColor.GREEN + " has joined the game " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
				if (!evt.getPlayer().hasPlayedBefore()) {
					pl.sendMessage(String.format("%sThis is their first time on the server!", ChatColor.GREEN));
				}
			} else {
				if (evt.getPlayer().hasPlayedBefore()) {
					pl.sendMessage(String.format("%s%s has joined the game", evt.getPlayer().getDisplayName(), ChatColor.GREEN));
				} else {
					pl.sendMessage(String.format("%s%s has joined the game for the first time", evt.getPlayer().getDisplayName(), ChatColor.GREEN));
				}
			}
		}


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
		String name = evt.getPlayer().getDisplayName();
		evt.setQuitMessage(null);

		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (pl.getName().equalsIgnoreCase(evt.getPlayer().getName())) break;
			if (pl.hasPermission("masters.debug")) {
				pl.sendMessage(name + ChatColor.GREEN + " left the game " + ChatColor.RED + "(" + ChatColor.WHITE + address.get(name) + ChatColor.RED + ")");
            } else {
				pl.sendMessage(name + ChatColor.GREEN  + " has left the game");
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

			Integer minutes = (int) (timeDifference / 1000000000.0 / 60);

			if (minutes >= 120) {
				slept.remove(name);
				slept.put(name, time);

				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.sendMessage(evt.getPlayer().getDisplayName() + " slept through the night!");
				}

				// evt.setCancelled(true);
				evt.getPlayer().getWorld().setTime(0);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        evt.getPlayer().teleport(
                                new Location(evt.getPlayer().getWorld(), evt.getPlayer().getLocation().getX() + 0.5D, evt.getPlayer().getLocation().getY() + 1.0D, evt.getPlayer().getLocation().getZ() + 0.5D),
                                PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }.runTaskLater(plugin, 6*20L);

			} else {
				evt.getPlayer().sendMessage(
                        String.format("%s%sThe bed is comfortable, but it seems you can't fall asleep", ChatColor.BOLD, ChatColor.GRAY));
                evt.getPlayer().sendMessage(
                        String.format("%s%sYou may try again in %d minutes!", ChatColor.ITALIC, ChatColor.DARK_GRAY, 120 - minutes));
				// evt.setCancelled(true);
			}


		} else {
			slept.put(name, time);
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				pl.sendMessage(evt.getPlayer().getDisplayName() + " slept through the night!");
			}


			// evt.setCancelled(true);
			evt.getPlayer().getWorld().setTime(0);
			evt.getPlayer().teleport(new Location(evt.getPlayer().getWorld(), evt.getPlayer().getLocation().getX() + 0.5D, evt.getPlayer().getLocation().getY() + 1.0D, evt.getPlayer().getLocation().getZ() + 0.5D));
		}
	}
}
