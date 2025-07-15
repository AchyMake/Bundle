package org.achymake.bundle.handlers;

import org.achymake.bundle.Bundle;
import org.achymake.bundle.data.Message;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryHandler {
    private final HashMap<Player, Inventory> bundles = new HashMap<>();
    private Bundle getInstance() {
        return Bundle.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    public Inventory create(Player player, int size, String title) {
        return getInstance().getServer().createInventory(player, size, title);
    }
    public Inventory open(Player player, ItemStack itemStack) {
        if (itemStack.getItemMeta() instanceof BundleMeta bundleMeta) {
            getBundles().remove(player);
            var result = getInstance().getMaterialHandler().getEnchantment(bundleMeta, "storage") + 1;
            var inventory = create(player, 9 * result, getMessage().addColor(getConfig().getString("bundle.title")));
            var items = bundleMeta.getItems();
            if (bundleMeta.hasItems()) {
                for (var i = 0; i < items.size(); i++) {
                    var item = items.get(i);
                    if (item != null) {
                        inventory.setItem(i, item);
                    }
                }
            }
            getBundles().put(player, inventory);
            player.openInventory(inventory);
            playSound(player);
            return inventory;
        } else return null;
    }
    public ItemStack close(Player player, ItemStack itemStack, Inventory inventory) {
        if (itemStack.getItemMeta() instanceof BundleMeta bundleMeta) {
            var listed = new ArrayList<ItemStack>();
            for (var items : inventory) {
                if (items != null) {
                    listed.add(items);
                }
            }
            bundleMeta.setItems(listed);
            itemStack.setItemMeta(bundleMeta);
            getBundles().remove(player);
            playSound(player);
        }
        return itemStack;
    }
    public boolean hasBundle(Player player) {
        return getBundles().containsKey(player);
    }
    public Inventory getBundle(Player player) {
        return getBundles().get(player);
    }
    public void playSound(Player player) {
        var sound = getConfig().getString("bundle.sound.type");
        if (sound == null)return;
        var volume = (float) getConfig().getDouble("bundle.sound.volume");
        var pitch = (float) getConfig().getDouble("bundle.sound.pitch");
        player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
    }
    public HashMap<Player, Inventory> getBundles() {
        return bundles;
    }
}