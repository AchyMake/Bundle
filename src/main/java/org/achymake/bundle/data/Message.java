package org.achymake.bundle.data;

import org.bukkit.ChatColor;

public class Message {
    public String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}