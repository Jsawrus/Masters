package com.ftbmasters.commands;

import com.ftbmasters.Masters;
import com.ftbmasters.utils.commands.Command;
import com.ftbmasters.utils.commands.CommandArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SillyCommands {

    @Command (
            name = "slap",
            aliases = {"sl", "spal"},
            permission = "masters.slap",
            usage = "/<command> [User]",
            description = "Slap people around a bit with a large Primed TNT",
            player = true
    )
    public void slap(CommandSender sender, String[] args) {
        Player player = ((Player) sender).getPlayer();
        Player target;
        String targetName;

        if (args.length == 0) {
            target = player;
            targetName = target.getName();
        } else {
            target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                target = player;
                targetName = args[0];
            } else {
                targetName = target.getName();
            }
        }

        Bukkit.getServer().broadcastMessage(
                String.format("%s%s slaps %s%s%s around a bit with a large %sTNT%s!",
                        player.getName(), ChatColor.GOLD, ChatColor.RESET, targetName, ChatColor.GOLD, ChatColor.RED, ChatColor.RESET));

        Location explosionLocation = target.getLocation();
        explosionLocation.setY(target.getLocation().getY() + 1);
        target.getWorld().createExplosion(
                explosionLocation, 0, false);
        target.damage(0.0D, target);
        target.setVelocity(new Vector(1, 1, 1));
        target.setFallDistance(0);
        target.setNoDamageTicks(3 * 20);
    }

    @Command (
            name = "color",
            aliases = {},
            permission = "masters.color",
            usage = "/<command> [color]",
            description = "Change your color! choose from: 1,2,3,4,5,6,7,9,a,b,c,d,e",
            player = true
    )
    public void color(CommandSender sender, String[] args) {
        Masters plugin = (Masters) Masters.getInstance();

        if (!args[0].matches("[1-79a-e]")) {
            throw new CommandArgumentException("wrong color format!");
        }
        plugin.getConfig().set("nameplates.players." + sender.getName(), args[0]);
        plugin.getNameplateManager().refreshNameplate(sender.getServer().getPlayer(sender.getName()));
    }
}
