package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class teleportHandler implements Listener {
    Plugin plugin;

    public teleportHandler(Plugin pl) {
        this.plugin = pl;
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void teleport(final PlayerTeleportEvent ev){
        if (ev.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Location from = ev.getFrom();
            Location to = ev.getTo();

            if (from.distance(to) < 1) {
                ev.setCancelled(true);
                this.teleportToBed(ev.getPlayer());
            }
        }
    }

    private void teleportToBed(final Player player) {
        Location targetLocation = player.getBedSpawnLocation();
        String message;
        if (targetLocation == null) {
            targetLocation = Bukkit.getWorld("world").getSpawnLocation();

            message = ChatColor.LIGHT_PURPLE + "You find yourself at your bed." + ChatColor.WHITE + " Was it a dream? ...";
        } else {
            message = ChatColor.LIGHT_PURPLE + "You find yourself to the world spawn." + ChatColor.WHITE + " Was it a dream? ...";
        }

        final Location finalTargetLocation = targetLocation;
        final String finalMessage = message;
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.teleport(
                        finalTargetLocation,
                        PlayerTeleportEvent.TeleportCause.PLUGIN);
                player.sendMessage(finalMessage);
            }
        }.runTaskLater(this.plugin, 10);
    }
}
