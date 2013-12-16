package com.ftbmasters.listeners;

import com.ftbmasters.Masters;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class doubleJumpHandler implements Listener {
    private Masters plugin;

    public doubleJumpHandler(Masters pl) {
        Bukkit.getPluginManager().registerEvents(this, pl);
        this.plugin = pl;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.isFlying()) return;

        if ((event.getPlayer().hasPermission("masters.doublejump")) &&
                (event.getPlayer().getGameMode() != GameMode.CREATIVE) &&
                (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR))
            event.getPlayer().setAllowFlight(true);
    }

    @EventHandler
    public void onFlyToggle(PlayerToggleFlightEvent event)
    {
        Player p = event.getPlayer();

        if ((p.hasPermission("masters.doublejump")) && (p.getGameMode() != GameMode.CREATIVE) && !p.getAllowFlight()) {
            event.setCancelled(true);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setVelocity(p.getLocation().getDirection().multiply(0.5D).setY(2));
        }
    }
}
