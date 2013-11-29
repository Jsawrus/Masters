package com.ftbmasters.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi;

public class teleportHandler implements Listener {
    public teleportHandler(Plugin pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void teleport(final PlayerTeleportEvent ev){
        if (ev.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Location from = ev.getFrom();
            Location to = ev.getTo();

            if (Math.abs(from.getX() - to.getX()) <= 2 && Math.abs(from.getY() - to.getY()) <=2) {
                this.teleportToBed(ev.getPlayer());
            }
        }

    }

    private void teleportToBed(Player player) {
        Location bed = player.getBedSpawnLocation();
        Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
        if (bed == null)
            player.teleport(spawn);
        else
            player.teleport(bed);

        player.chat(ChatColor.LIGHT_PURPLE + "You find yourself at your bed." + ChatColor.WHITE + " Was it a dream? ...");
    }
}
