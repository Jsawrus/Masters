package com.ftbmasters.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
public class mastersCommand implements ICommandable {
    private String permission = "op";

    @Override
    public boolean run(CommandSender sender, Command command, String label, String[] args) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPermission() {
        return this.permission;
    }
}
