package org.achymake.bundle;

import org.achymake.bundle.commands.*;
import org.achymake.bundle.data.*;
import org.achymake.bundle.handlers.*;
import org.achymake.bundle.listeners.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public final class Bundle extends JavaPlugin {
    private static Bundle instance;
    private Message message;
    private InventoryHandler inventoryHandler;
    private MaterialHandler materialHandler;
    private ScheduleHandler scheduleHandler;
    private UpdateChecker updateChecker;
    private BukkitScheduler bukkitScheduler;
    private PluginManager pluginManager;
    @Override
    public void onEnable() {
        instance = this;
        message = new Message();
        inventoryHandler = new InventoryHandler();
        materialHandler = new MaterialHandler();
        scheduleHandler = new ScheduleHandler();
        updateChecker = new UpdateChecker();
        bukkitScheduler = getServer().getScheduler();
        pluginManager = getServer().getPluginManager();
        commands();
        events();
        reload();
        sendInfo("Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
        getUpdateChecker().getUpdate();
    }
    @Override
    public void onDisable() {
        getScheduleHandler().disable();
        sendInfo("Disabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
    }
    private void commands() {
        new BundleCommand();
    }
    private void events() {
        new InventoryClick();
        new InventoryClose();
        new PlayerInteract();
        new PlayerJoin();
    }
    public void reload() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        } else reloadConfig();
    }
    public PluginManager getPluginManager() {
        return pluginManager;
    }
    public BukkitScheduler getBukkitScheduler() {
        return bukkitScheduler;
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public ScheduleHandler getScheduleHandler() {
        return scheduleHandler;
    }
    public MaterialHandler getMaterialHandler() {
        return materialHandler;
    }
    public InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }
    public Message getMessage() {
        return message;
    }
    public static Bundle getInstance() {
        return instance;
    }
    public void sendInfo(String message) {
        getLogger().info(message);
    }
    public void sendWarning(String message) {
        getLogger().warning(message);
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
    public String getMinecraftVersion() {
        return getServer().getBukkitVersion();
    }
    public String getMinecraftProvider() {
        return getServer().getName();
    }
}