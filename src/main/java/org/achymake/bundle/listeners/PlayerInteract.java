package org.achymake.bundle.listeners;

import org.achymake.bundle.Bundle;
import org.achymake.bundle.handlers.InventoryHandler;
import org.achymake.bundle.handlers.MaterialHandler;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.PluginManager;

public class PlayerInteract implements Listener {
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
    public PlayerInteract() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock() == null)return;
            if (event.getHand() != EquipmentSlot.HAND)return;
            if (getMaterialHandler().hasInventory(event.getClickedBlock()))return;
            var itemStack = player.getInventory().getItemInMainHand();
            if (!getMaterialHandler().isBundle(itemStack))return;
            if (!player.hasPermission("bundle.event.bundle"))return;
            event.setCancelled(true);
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
            getInventoryHandler().open(player, itemStack);
        } else if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getHand() != EquipmentSlot.HAND)return;
            var itemStack = event.getItem();
            if (itemStack == null)return;
            if (!getMaterialHandler().isBundle(itemStack))return;
            if (!player.hasPermission("bundle.event.bundle"))return;
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            getInventoryHandler().open(player, itemStack);
        }
    }
}