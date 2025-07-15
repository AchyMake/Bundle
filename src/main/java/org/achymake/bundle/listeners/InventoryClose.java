package org.achymake.bundle.listeners;

import org.achymake.bundle.Bundle;
import org.achymake.bundle.handlers.InventoryHandler;
import org.achymake.bundle.handlers.MaterialHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.PluginManager;

public class InventoryClose implements Listener {
    private Bundle getInstance() {
        return Bundle.getInstance();
    }
    private InventoryHandler getInventoryHandler() {
        return getInstance().getInventoryHandler();
    }
    private MaterialHandler getMaterials() {
        return getInstance().getMaterialHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public InventoryClose() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event) {
        var player = (Player) event.getPlayer();
        var itemStack = player.getInventory().getItemInMainHand();
        if (!getMaterials().isBundle(itemStack))return;
        if (!getInventoryHandler().hasBundle(player))return;
        if (getInventoryHandler().getBundle(player) != event.getInventory())return;
        getInventoryHandler().close(player, itemStack, event.getInventory());
    }
}