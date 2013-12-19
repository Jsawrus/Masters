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
            name = "nameplate",
            aliases = {},
            permission = "masters.nameplate",
            usage = "/<command> [color]",
            description = "Change your color! choose from: 1,2,3,4,5,6,7,9,a,b,c,d,e",
            player = true
    )
    public void nameplate(CommandSender sender, String[] args) {
        Masters plugin = (Masters) Masters.getInstance();

        if (!args[0].matches("[1-79a-e]")) {
            throw new CommandArgumentException("wrong color format!");
        }
        plugin.getConfig().set("nameplates.players." + sender.getName(), args[0].charAt(0));
        plugin.getNameplateManager().refreshNameplate(sender.getServer().getPlayer(sender.getName()));
    }

    @Command(
            name = "colors",
            aliases = {},
            permission = "masters.colors",
            usage = "/colors",
            description = "List all the color codes you can use",
            player = true
    )
    public void colors(CommandSender sender, String[] args) {
        sender.sendMessage(new String[]{
                ChatColor.BLACK + "&0" + ChatColor.DARK_BLUE + "&1" + ChatColor.DARK_GREEN + "&2",
                ChatColor.DARK_AQUA + "&3" + ChatColor.DARK_RED + "&4" + ChatColor.DARK_PURPLE + "&5",
                ChatColor.GOLD + "&6" + ChatColor.GRAY + "&7" + ChatColor.DARK_GRAY + "&8",
                ChatColor.BLUE + "&9" + ChatColor.GREEN + "&a" + ChatColor.AQUA + "&b",
                ChatColor.RED + "&c" + ChatColor.LIGHT_PURPLE + "&d" + ChatColor.YELLOW + "&e",
                ChatColor.WHITE + "&f",
                "&r resets color to the default.",
                ChatColor.GOLD + " ~~ # ~~~~~~ ",
                "&6 Gold Text &c Red Text &r default text",
                "becomes: ",
                ChatColor.translateAlternateColorCodes('&', "&6 Gold Text &c Red Text &r default text")
        });
    }
}
