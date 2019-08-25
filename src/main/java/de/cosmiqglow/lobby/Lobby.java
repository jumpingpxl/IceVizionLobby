package de.cosmiqglow.lobby;

import de.cosmiqglow.lobby.commands.SetCommand;
import de.cosmiqglow.lobby.listener.*;
import de.cosmiqglow.lobby.map.MapService;
import de.cosmiqglow.lobby.utils.CooldownUtil;
import de.cosmiqglow.lobby.utils.InventoryUtil;
import de.cosmiqglow.lobby.utils.ItemUtil;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.spigot.Cloud;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    private MapService mapService;
    private CooldownUtil cooldownUtil;
    private InventoryUtil inventoryUtil;
    private ItemUtil itemUtil;

    @Override
    public void onEnable() {
        load();
        registerListener();
        registerCommands();

        Cloud.getInstance().setSpigotState(SpigotState.AVAILABLE);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void load() {
        this.mapService = new MapService();
        this.cooldownUtil = new CooldownUtil();
        this.itemUtil = new ItemUtil();
        this.inventoryUtil = new InventoryUtil(itemUtil);
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFoodListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerSpawnListener(mapService), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);
    }

    private void registerCommands() {
        getCommand("location").setExecutor(new SetCommand(mapService));
    }

    public InventoryUtil getInventoryUtil() {
        return inventoryUtil;
    }

    public ItemUtil getItemUtil() {
        return itemUtil;
    }

    public MapService getMapService() {
        return mapService;
    }

    public CooldownUtil getCooldownUtil() {
        return cooldownUtil;
    }
}