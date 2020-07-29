package de.icevizion.lobby.utils.inventories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.GamesItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class GamesInventory extends InventoryBuilder {

	private final LobbyPlugin lobbyPlugin;
	private final GamesItemFactory itemFactory;

	public GamesInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(cloudPlayer, lobbyPlugin.getLocales().getString(cloudPlayer, "inventoryGamesTitle"), 27);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new GamesItemFactory(lobbyPlugin, cloudPlayer);
	}

	@Override
	public void draw() {
		setItem(0, itemFactory.getBackgroundItem());
		setItem(4, itemFactory.getGuessItItem(), event -> {
			Location guessItLocation = lobbyPlugin.getLocationProvider().getLocation("guessit");
			getCloudPlayer().getPlayer().teleport(guessItLocation);
		});

		setItem(8, itemFactory.getBackgroundItem());

		setItem(9, itemFactory.getBackgroundItem());
		setItem(11, itemFactory.getKnockBackFFAItem(), event -> {
			Location knockbackFFALocation = lobbyPlugin.getLocationProvider().getLocation("kbffa");
			getCloudPlayer().getPlayer().teleport(knockbackFFALocation);
		});

		setItem(13, itemFactory.getSpawnItem(), event -> {
			Location spawnLocation = lobbyPlugin.getLocationProvider().getLocation("spawn");
			getCloudPlayer().getPlayer().teleport(spawnLocation);
		});

		setItem(14, itemFactory.getOneLineItem(), event -> {
			Location oneLineLocation = lobbyPlugin.getLocationProvider().getLocation("oneline");
			getCloudPlayer().getPlayer().teleport(oneLineLocation);
		});

		setItem(17, itemFactory.getBackgroundItem());

		setItem(18, itemFactory.getBackgroundItem());
		setItem(22, itemFactory.getBedWarsItem(), event -> {
			Location bedWarsLocation = lobbyPlugin.getLocationProvider().getLocation("bedwars");
			getCloudPlayer().getPlayer().teleport(bedWarsLocation);
		});

		setItem(26, itemFactory.getBackgroundItem());
	}
}
