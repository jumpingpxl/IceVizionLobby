package de.icevizion.lobby.utils.inventories;

import de.icevizion.aves.inventory.InventoryRows;
import de.icevizion.aves.inventory.TranslatedInventory;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.GamesItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class GamesInventory extends TranslatedInventory {

	private final LobbyPlugin lobbyPlugin;
	private final GamesItemFactory itemFactory;

	public GamesInventory(LobbyPlugin lobbyPlugin, Locale locale) {
		super(lobbyPlugin.getLocales(), locale, InventoryRows.THREE, "inventoryGamesTitle");
		this.lobbyPlugin = lobbyPlugin;

		itemFactory = new GamesItemFactory(getTranslator(), locale);
	}

	@Override
	public void draw() {
		System.out.println("GAMES DRAW");
		setItem(0, itemFactory.getBackgroundItem());
		setItem(4, itemFactory.getGuessItItem(),
				event -> teleportPlayer(event.getCloudPlayer(), "guessit"));
		setItem(8, itemFactory.getBackgroundItem());

		setItem(9, itemFactory.getBackgroundItem());
		setItem(11, itemFactory.getKnockBackFFAItem(),
				event -> teleportPlayer(event.getCloudPlayer(), "kbffa"));
		setItem(13, itemFactory.getSpawnItem(),
				event -> teleportPlayer(event.getCloudPlayer(), "spawn"));
		setItem(15, itemFactory.getOneLineItem(),
				event -> teleportPlayer(event.getCloudPlayer(), "oneline"));
		setItem(17, itemFactory.getBackgroundItem());

		setItem(18, itemFactory.getBackgroundItem());
		setItem(22, itemFactory.getBedWarsItem(),
				event -> teleportPlayer(event.getCloudPlayer(), "bedwars"));
		setItem(26, itemFactory.getBackgroundItem());
	}

	private void teleportPlayer(CloudPlayer cloudPlayer, String locationName) {
		Location location = lobbyPlugin.getLocationProvider().getLocation(locationName);
		cloudPlayer.getPlayer().teleport(location);
	}
}
