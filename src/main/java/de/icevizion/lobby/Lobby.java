package de.icevizion.lobby;

import de.icevizion.lobby.commands.EventCommand;
import de.icevizion.lobby.commands.SetCommand;
import de.icevizion.lobby.feature.SnowService;
import de.icevizion.lobby.map.MapService;
import de.icevizion.lobby.profile.ProfileCache;
import de.icevizion.lobby.listener.*;
import de.icevizion.lobby.utils.*;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.spigot.Cloud;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;

public class Lobby extends JavaPlugin {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    private String prefix;
    private MapService mapService;
    private InventoryUtil inventoryUtil;
    private ItemUtil itemUtil;
    private VisibilityUtil visibilityUtil;
    private SettingsUtil settingsUtil;
    private DailyRewardUtil dailyRewardUtil;
    private ProfileCache profileCache;
    private DoubleJumpService doubleJumpService;
    private LobbyUtil lobbyUtil;
    private FriendUtil friendUtil;
    private SnowService snowService;

    @Override
    public void onEnable() {
        this.prefix = "§eLobby §8» §r";
        load();
        registerListener();
        registerCommands();
        Cloud.getInstance().setSpigotState(SpigotState.AVAILABLE);
    }

    @Override
    public void onDisable() {
        dailyRewardUtil.despawn();
        snowService.cancel();
    }

    private void load() {
        this.mapService = new MapService();
        this.itemUtil = new ItemUtil();
        this.settingsUtil = new SettingsUtil();
        this.inventoryUtil = new InventoryUtil(itemUtil, settingsUtil);
        this.profileCache = new ProfileCache();
        this.visibilityUtil = new VisibilityUtil();
        this.dailyRewardUtil = new DailyRewardUtil(mapService.getLocation("daily"));
        this.doubleJumpService = new DoubleJumpService();
        this.lobbyUtil = new LobbyUtil();
        this.friendUtil = new FriendUtil();
        snowService = new SnowService(this);
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new EntityInteractListener(prefix, dailyRewardUtil), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new NetworkListener(lobbyUtil, this), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFoodListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFriendListener(profileCache, friendUtil), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerItemListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(snowService, profileCache), this);
        getServer().getPluginManager().registerEvents(new PlayerSettingsListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerSpawnListener(mapService), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);
        getServer().getPluginManager().registerEvents(doubleJumpService, this);
        getServer().getPluginManager().registerEvents(new ScoreboardService(this), this);
    }

    private void registerCommands() {
        getCommand("location").setExecutor(new SetCommand(mapService));
        getCommand("schnee").setExecutor(new EventCommand(this));
    }

    public String getPrefix() {
        return prefix;
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

    public VisibilityUtil getVisibilityUtil() {
        return visibilityUtil;
    }

    public SettingsUtil getSettingsUtil() {
        return settingsUtil;
    }

    public ProfileCache getProfileCache() {
        return profileCache;
    }

    public SnowService getSnowService() {
        return snowService;
    }

    public LobbyUtil getLobbyUtil() {
        return lobbyUtil;
    }

    public FriendUtil getFriendUtil() {
        return friendUtil;
    }
}