package org.achymake.bundle.handlers;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialHandler {
    public Material get(String materialName) {
        return Material.getMaterial(materialName.toUpperCase());
    }
    public boolean isBundle(ItemStack itemStack) {
        return Tag.ITEMS_BUNDLES.isTagged(itemStack.getType());
    }
    public boolean isShulkerBox(ItemStack itemStack) {
        return Tag.SHULKER_BOXES.isTagged(itemStack.getType());
    }
    public boolean isStorages(ItemStack itemStack) {
        if (itemStack != null) {
            return isBundle(itemStack) || isShulkerBox(itemStack);
        } else return false;
    }
    public Enchantment getEnchantment(String enchantmentName) {
        return Enchantment.getByName(enchantmentName.toUpperCase());
    }
    public boolean isEnchantment(String enchantmentName) {
        return getEnchantment(enchantmentName) != null;
    }
    public int getEnchantment(ItemMeta itemMeta, String enchantmentName) {
        if (isEnchantment(enchantmentName)) {
            return itemMeta.getEnchantLevel(getEnchantment(enchantmentName));
        } else return 0;
    }
    public boolean hasInventory(Block block) {
        var material = block.getType();
        if (block.getState() instanceof BlockInventoryHolder) {
            return true;
        } else if (material.equals(get("trapped_chest"))) {
            return true;
        } else if (material.equals(get("crafting_table"))) {
            return true;
        } else if (material.equals(get("stonecutter"))) {
            return true;
        } else if (material.equals(get("cartography_table"))) {
            return true;
        } else if (material.equals(get("smithing_table"))) {
            return true;
        } else if (material.equals(get("grindstone"))) {
            return true;
        } else if (material.equals(get("loom"))) {
            return true;
        } else if (Tag.CAMPFIRES.isTagged(material)) {
            return true;
        } else if (Tag.ANVIL.isTagged(material)) {
            return true;
        } else if (material.equals(get("note_block"))) {
            return true;
        } else if (material.equals(get("enchanting_table"))) {
            return true;
        } else if (material.equals(get("ender_chest"))) {
            return true;
        } else if (material.equals(get("bell"))) {
            return true;
        } else if (material.equals(get("beacon"))) {
            return true;
        } else if (material.equals(get("lodestone"))) {
            return true;
        } else if (Tag.ALL_SIGNS.isTagged(material)) {
            return true;
        } else if (Tag.WOODEN_DOORS.isTagged(material)) {
            return true;
        } else if (Tag.BEDS.isTagged(material)) {
            return true;
        } else return Tag.FENCE_GATES.isTagged(material);
    }
}