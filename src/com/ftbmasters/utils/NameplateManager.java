package com.ftbmasters.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class NameplateManager {
    private ScoreboardManager manager;
    private Scoreboard scoreboard;

    private ConfigurationSection config;
    private Plugin plugin;

    private int refreshInterval = 60000;
    private Timer timer;

    private final String colours = "1234569abc";

    public NameplateManager (Plugin plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();

        manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getMainScoreboard();

        if (config.getBoolean("nameplates.healthbar"))
            setHealthBar();

        startTimer();
    }

    private void setNameplate(Player player) {
        String color = this.config.getString("nameplates.players." + player.getName());

        if (color == null) {
            color = String.format("%s", colours.charAt(new Random().nextInt(100) / 10));
            config.set("nameplates.players." + player.getName(), color);
        }

        color = ChatColor.COLOR_CHAR + color;

        Team team = scoreboard.getPlayerTeam(player);
        if (team == null)
            team = scoreboard.registerNewTeam(player.getName());

        team.setPrefix(color);
        team.setSuffix(ChatColor.RESET.toString());
        team.addPlayer(player.getPlayer());

        player.setPlayerListName(color + player.getName());
        player.setDisplayName(color + player.getName());
    }

    private void setHealthBar () {
        Objective healthBar = scoreboard.getObjective("showHealth");

        if (!config.getBoolean("nameplates.healthbar"))
            return;
        if (healthBar == null)
            healthBar = scoreboard.registerNewObjective("showHealth", Criterias.HEALTH);

        healthBar.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthBar.setDisplayName(ChatColor.RED + "\u2764");
    }

    public void hideHealthBar () {
        if (scoreboard.getObjective("showHealth") != null)
            scoreboard.getObjective("showHealth").unregister();
    }

    public void showHealthBar () {
        if (scoreboard.getObjective("showHealth") == null)
            setHealthBar();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void startTimer () {
        if (timer != null)
            timer.cancel();

        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run (){
                refreshNameplates();
            }
        }, 0, this.refreshInterval);
    }
    public void stopTimer () {
        if (timer != null)
            timer.cancel();
    }
    public void refreshNameplates () {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setNameplate(player);
        }
    }
    public void refreshNameplate (Player player) {
        setNameplate(player);
    }
}
