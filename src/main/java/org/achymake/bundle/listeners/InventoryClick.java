package org.achymake.bundle.listeners;

import org.achymake.bundle.Bundle;
import org.achymake.bundle.handlers.InventoryHandler;
import org.achymake.bundle.handlers.MaterialHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.PluginManager;

public class InventoryClick implements Listener {
    private Bundle getInstance() {
        return Bundle.getInstance();
    }
    private InventoryHandler getInventoryHandler() {
        return getInstance().getInventoryHandler();
    }
    private MaterialHandler getMaterialHandler() {
        return getInstance().getMaterialHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public InventoryClick() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event) {
        var player = (Player) event.getWhoClicked();
        if (getInventoryHandler().hasBundle(player)) {
            if (getInventoryHandler().getBundle(player) != event.getInventory())return;
            if (event.getClick().equals(ClickType.SWAP_OFFHAND)) {
                var offHand = player.getInventory().getItemInOffHand();
                var clicked = event.getCurrentItem();
                if (getMaterialHandler().isStorages(offHand)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                } else if (getMaterialHandler().isStorages(clicked)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                }
            } else if (event.getClick().equals(ClickType.NUMBER_KEY)) {
                var switched = player.getInventory().getItem(event.getHotbarButton());
                var switched2 = event.getCurrentItem();
                if (switched != null) {
                    if (getMaterialHandler().isStorages(switched)) {
                        event.setCancelled(true);
                        event.setResult(Event.Result.DENY);
                    } else if (getMaterialHandler().isStorages(switched2)) {
                        event.setCancelled(true);
                        event.setResult(Event.Result.DENY);
                    }
                }
                if (switched2 != null) {
                    if (getMaterialHandler().isStorages(switched)) {
                        event.setCancelled(true);
                        event.setResult(Event.Result.DENY);
                    } else if (getMaterialHandler().isStorages(switched2)) {
                        event.setCancelled(true);
                        event.setResult(Event.Result.DENY);
                    }
                }
            } else {
                var clicked = event.getCurrentItem();
                if (clicked == null)return;
                if (!getMaterialHandler().isStorages(clicked))return;
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        } else if (event.getClick().equals(ClickType.SWAP_OFFHAND)) {
            var offHand = player.getInventory().getItemInOffHand();
            var clicked = event.getCurrentItem();
            if (getMaterialHandler().isStorages(offHand)) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            } else if (getMaterialHandler().isStorages(clicked)) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        } else {
            var cursorItem = event.getCursor();
            if (cursorItem == null)return;
            if (!getMaterialHandler().isBundle(cursorItem))return;
            var clicked = event.getCurrentItem();
            if (clicked == null)return;
            if (!getMaterialHandler().isStorages(clicked))return;
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
}