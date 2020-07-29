package de.icevizion.lobby;

import de.icevizion.lobby.commands.LocationCommand;
import de.icevizion.lobby.listener.WeatherChangeListener;
import de.icevizion.lobby.listener.block.*;
import de.icevizion.lobby.listener.entity.*;
import de.icevizion.lobby.listener.network.*;
import de.icevizion.lobby.listener.player.*;
import de.icevizion.lobby.listener.player.inventory.InventoryClickListener;
import de.icevizion.lobby.listener.player.inventory.InventoryCloseListener;
import de.icevizion.lobby.listener.player.item.PlayerDropItemListener;
import de.icevizion.lobby.listener.player.item.PlayerItemConsumeListener;
import de.icevizion.lobby.listener.player.item.PlayerPickupItemListener;
import de.icevizion.lobby.utils.*;
import de.icevizion.lobby.utils.inventorybuilder.InventoryLoader;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.lib.redisevent.events.PlayerServerSwitchEvent;
import net.titan.spigot.Cloud;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;

public class LobbyPlugin extends JavaPlugin {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy");

	private Cloud cloud;
	private Locales locales;
	private LocationProvider locationProvider;
	private InventoryLoader inventoryLoader;
	private LobbySwitcher lobbySwitcher;
	private Scoreboard scoreboard;
	private Items items;

	@Override
	public void onEnable() {
		//TODO -> Remove Singleton Pattern
		cloud = Cloud.getInstance();

		loadUtilities();
		registerListener();
		registerCommands();

		cloud.setPlayerLimit(50);
		cloud.setSpigotState(SpigotState.AVAILABLE);
	}

	@Override
	public void onDisable() {
		uselessChestService.despawn();
	}

	private void loadUtilities() {
		locales = new Locales(this);
		locationProvider = new LocationProvider(this);
		inventoryLoader = new InventoryLoader(this);
		lobbySwitcher = new LobbySwitcher(this);
		scoreboard = new Scoreboard(this);
		items = new Items(this);

/*        this.mapService = new MapService();
        this.itemUtil = new ItemUtil();
        this.visibilityUtil = new VisibilityUtil();
        this.inventoryUtil = new InventoryUtil(this);
        this.settingsUtil = new SettingsUtil(visibilityUtil);
        this.dailyRewardUtil = new DailyRewardUtil();
        this.doubleJumpService = new DoubleJumpService();
        this.lobbyUtil = new LobbyUtil();
        this.friendUtil = new FriendUtil();
        this.uselessChestService = new UselessChestService(this); */
	}

	private void registerListener() {
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BlockBreakListener(), this);
		pluginManager.registerEvents(new BlockIgniteListener(), this);
		pluginManager.registerEvents(new BlockPhysicsListener(), this);
		pluginManager.registerEvents(new BlockPlaceListener(), this);
		pluginManager.registerEvents(new LeavesDecayListener(), this);

		pluginManager.registerEvents(new EntityExplodeListener(), this);
		pluginManager.registerEvents(new EntityInteractListener(), this);
		pluginManager.registerEvents(new HangingBreakByEntityListener(), this);
		pluginManager.registerEvents(new VehicleDamageListener(), this);
		pluginManager.registerEvents(new VehicleEnterListener(), this);

		pluginManager.registerEvents(new FriendUpdateListener(this), this);
		pluginManager.registerEvents(new NetworkPlayerJoinListener(this), this);
		pluginManager.registerEvents(new NetworkPlayerQuitListener(this), this);
		pluginManager.registerEvents(new PlayerCoinChangeListener(this), this);
		pluginManager.registerEvents(new PlayerRankChangeListener(this), this);
		cloud.getRedisEventManager().registerListener(PlayerServerSwitchEvent.class, new PlayerServerSwitchListener(this));

		pluginManager.registerEvents(new InventoryClickListener(this), this);
		pluginManager.registerEvents(new InventoryCloseListener(this), this);

		pluginManager.registerEvents(new PlayerDropItemListener(), this);
		pluginManager.registerEvents(new PlayerItemConsumeListener(), this);
		pluginManager.registerEvents(new PlayerPickupItemListener(), this);

		pluginManager.registerEvents(new FoodLevelChangeListener(), this);
		pluginManager.registerEvents(new PlayerArmorStandManipulateListener(), this);
		pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
		pluginManager.registerEvents(new PlayerInteractListener(this), this);
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new PlayerQuitListener(this), this);

		pluginManager.registerEvents(new WeatherChangeListener(), this);
	}

	private void registerCommands() {
		getCommand("location").setExecutor(new LocationCommand(this));
	}

	public Cloud getCloud() {
		return cloud;
	}

	public Locales getLocales() {
		return locales;
	}

	public InventoryLoader getInventoryLoader() {
		return inventoryLoader;
	}

	public LocationProvider getLocationProvider() {
		return locationProvider;
	}

	public LobbySwitcher getLobbySwitcher() {
		return lobbySwitcher;
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public Items getItems() {
		return items;
	}
}