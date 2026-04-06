package org.achymake.bundle.commands;

import org.achymake.bundle.Bundle;
import org.achymake.bundle.data.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BundleCommand implements CommandExecutor, TabCompleter {
    private Bundle getInstance() {
        return Bundle.getInstance();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                getInstance().reload();
                player.sendMessage(getMessage().addColor("&6" + getInstance().name() + "&f: reloaded"));
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                getInstance().reload();
                consoleCommandSender.sendMessage(getInstance().name() + ": reloaded");
                return true;
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var commands = new ArrayList<String>();
        if (sender instanceof Player && args.length == 1) {
            commands.add("reload");
        }
        return commands;
    }
}