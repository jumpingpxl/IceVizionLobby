package de.icevizion.lobby;

import de.icevizion.lobby.commands.LocationCommand;
import de.icevizion.lobby.listener.WeatherChangeListener;
import de.icevizion.lobby.listener.block.BlockBreakListener;
import de.icevizion.lobby.listener.block.BlockIgniteListener;
import de.icevizion.lobby.listener.block.BlockPhysicsListener;
import de.icevizion.lobby.listener.block.BlockPlaceListener;
import de.icevizion.lobby.listener.block.LeavesDecayListener;
import de.icevizion.lobby.listener.entity.EntityDamageByEntityListener;
import de.icevizion.lobby.listener.entity.EntityDamageListener;
import de.icevizion.lobby.listener.entity.EntityExplodeListener;
import de.icevizion.lobby.listener.entity.EntityInteractListener;
import de.icevizion.lobby.listener.entity.HangingBreakByEntityListener;
import de.icevizion.lobby.listener.entity.VehicleDamageListener;
import de.icevizion.lobby.listener.entity.VehicleEnterListener;
import de.icevizion.lobby.listener.network.FriendUpdateListener;
import de.icevizion.lobby.listener.network.NetworkPlayerJoinListener;
import de.icevizion.lobby.listener.network.NetworkPlayerQuitListener;
import de.icevizion.lobby.listener.network.PlayerCoinChangeListener;
import de.icevizion.lobby.listener.network.PlayerRankChangeListener;
import de.icevizion.lobby.listener.network.PlayerServerSwitchListener;
import de.icevizion.lobby.listener.network.RankReloadListener;
import de.icevizion.lobby.listener.network.ServerAvailableListener;
import de.icevizion.lobby.listener.network.ServerUnavailableListener;
import de.icevizion.lobby.listener.player.FoodLevelChangeListener;
import de.icevizion.lobby.listener.player.PlayerArmorStandManipulateListener;
import de.icevizion.lobby.listener.player.PlayerInteractEntityListener;
import de.icevizion.lobby.listener.player.PlayerInteractListener;
import de.icevizion.lobby.listener.player.PlayerJoinListener;
import de.icevizion.lobby.listener.player.PlayerMoveListener;
import de.icevizion.lobby.listener.player.PlayerQuitListener;
import de.icevizion.lobby.listener.player.PlayerSpawnListener;
import de.icevizion.lobby.listener.player.PlayerToggleFlightListener;
import de.icevizion.lobby.listener.player.inventory.InventoryClickListener;
import de.icevizion.lobby.listener.player.inventory.InventoryCloseListener;
import de.icevizion.lobby.listener.player.item.PlayerDropItemListener;
import de.icevizion.lobby.listener.player.item.PlayerItemConsumeListener;
import de.icevizion.lobby.listener.player.item.PlayerPickupItemListener;
import de.icevizion.lobby.utils.DoubleJump;
import de.icevizion.lobby.utils.Inventories;
import de.icevizion.lobby.utils.Items;
import de.icevizion.lobby.utils.LobbyScoreboard;
import de.icevizion.lobby.utils.LobbySwitcher;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.LocationProvider;
import de.icevizion.lobby.utils.VisibilityTool;
import de.icevizion.lobby.utils.inventorybuilder.InventoryLoader;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.lib.redisevent.events.PlayerServerSwitchEvent;
import net.titan.lib.redisevent.events.ServerAvailableEvent;
import net.titan.lib.redisevent.events.ServerUnavailableEvent;
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
	private LobbyScoreboard lobbyScoreboard;
	private Items items;
	private DoubleJump doubleJump;
	private VisibilityTool visibilityTool;
	private Inventories inventories;

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

	}

	private void loadUtilities() {
		locales = new Locales(this);
		locationProvider = new LocationProvider(this);
		inventoryLoader = new InventoryLoader(this);
		inventories = new Inventories(this);
		lobbySwitcher = new LobbySwitcher(this);
		lobbyScoreboard = new LobbyScoreboard(this);
		items = new Items(this);
		doubleJump = new DoubleJump();
		visibilityTool = new VisibilityTool(this);
	}

	private void registerListener() {
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BlockBreakListener(), this);
		pluginManager.registerEvents(new BlockIgniteListener(), this);
		pluginManager.registerEvents(new BlockPhysicsListener(), this);
		pluginManager.registerEvents(new BlockPlaceListener(), this);
		pluginManager.registerEvents(new LeavesDecayListener(), this);

		pluginManager.registerEvents(new EntityDamageByEntityListener(this), this);
		pluginManager.registerEvents(new EntityDamageListener(), this);
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
		cloud.getRedisEventManager().registerListener(PlayerServerSwitchEvent.class,
				new PlayerServerSwitchListener(this));
		pluginManager.registerEvents(new RankReloadListener(this), this);
		cloud.getRedisEventManager().registerListener(ServerAvailableEvent.class,
				new ServerAvailableListener(this));
		cloud.getRedisEventManager().registerListener(ServerUnavailableEvent.class,
				new ServerUnavailableListener(this));

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
		pluginManager.registerEvents(new PlayerMoveListener(this), this);
		pluginManager.registerEvents(new PlayerQuitListener(this), this);
		pluginManager.registerEvents(new PlayerSpawnListener(this), this);
		pluginManager.registerEvents(new PlayerToggleFlightListener(this), this);

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

	public LobbyScoreboard getLobbyScoreboard() {
		return lobbyScoreboard;
	}

	public Items getItems() {
		return items;
	}

	public DoubleJump getDoubleJump() {
		return doubleJump;
	}

	public VisibilityTool getVisibilityTool() {
		return visibilityTool;
	}

	public Inventories getInventories() {
		return inventories;
	}
}

