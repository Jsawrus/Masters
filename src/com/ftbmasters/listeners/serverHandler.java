package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;

public class serverHandler implements Listener {
	private final int maxPlayer = (int) (((((6 ^ 2 * 10) + Math.sqrt((5000 * 3) - 600)) / 4) * 4) - Math.log((1 * 10 ^ 11))) / 2;
    private final Plugin plugin;

    public serverHandler(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
        plugin = pl;
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void server(final ServerListPingEvent evt) {
		evt.setMotd(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("motd")));
		// max players is 69
		evt.setMaxPlayers(maxPlayer);
	}

}
