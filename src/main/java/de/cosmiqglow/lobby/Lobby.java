package de.cosmiqglow.lobby;

import de.cosmiqglow.aves.util.LocationUtil;
import de.cosmiqglow.lobby.commands.SetCommand;
import de.cosmiqglow.lobby.listener.*;
import de.cosmiqglow.lobby.map.MapService;
import de.cosmiqglow.lobby.profile.ProfileCache;
import de.cosmiqglow.lobby.utils.*;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.spigot.Cloud;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    private MapService mapService;
    private CooldownUtil cooldownUtil;
    private InventoryUtil inventoryUtil;
    private ItemUtil itemUtil;
    private VisibilityUtil visibilityUtil;
    private SettingsUtil settingsUtil;
    private DailyRewardUtil dailyRewardUtil;
    //private ParticleUtil particleUtil;
    private ProfileCache profileCache;

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
        dailyRewardUtil.despawn();
        super.onDisable();
    }

    private void load() {
        this.mapService = new MapService();
        this.cooldownUtil = new CooldownUtil();
        this.itemUtil = new ItemUtil();
        this.settingsUtil = new SettingsUtil();
        this.inventoryUtil = new InventoryUtil(this);
        this.profileCache = new ProfileCache();
        this.visibilityUtil = new VisibilityUtil(profileCache);
        this.dailyRewardUtil = new DailyRewardUtil(LocationUtil.getCenter(mapService.getLocation("daily").getBlock()));
      // this.particleUtil = new ParticleUtil(this, mapService.getLocation("daily"));
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new EntityInteractListener(dailyRewardUtil), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFoodListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
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

    public VisibilityUtil getVisibilityUtil() {
        return visibilityUtil;
    }

    public SettingsUtil getSettingsUtil() {
        return settingsUtil;
    }

    public DailyRewardUtil getDailyRewardUtil() {
        return dailyRewardUtil;
    }

    public ProfileCache getProfileCache() {
        return profileCache;
    }

   /* public ParticleUtil getParticleUtil() {
        return particleUtil;
    }*/
}