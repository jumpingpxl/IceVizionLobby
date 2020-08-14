package de.icevizion.lobby.utils.inventories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.GamesItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class GamesInventory extends InventoryBuilder {

	private final LobbyPlugin lobbyPlugin;
	private final GamesItemFactory itemFactory;

	public GamesInventory(LobbyPlugin lobbyPlugin, Locale locale) {
		super(lobbyPlugin.getLocales().getString(locale, "inventoryGamesTitle"), 27);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new GamesItemFactory(lobbyPlugin, locale);
	}

	@Override
	public boolean isCacheable() {
		return true;
	}

	@Override
	public void draw() {
		setItem(0, itemFactory.getBackgroundItem());
		setItem(4, itemFactory.getGuessItItem(),
				event -> teleportPlayer(event.getWhoClicked(), "guessit"));
		setItem(8, itemFactory.getBackgroundItem());

		setItem(9, itemFactory.getBackgroundItem());
		setItem(11, itemFactory.getKnockBackFFAItem(),
				event -> teleportPlayer(event.getWhoClicked(), "kbffa"));
		setItem(13, itemFactory.getSpawnItem(),
				event -> teleportPlayer(event.getWhoClicked(), "spawn"));
		setItem(15, itemFactory.getOneLineItem(),
				event -> teleportPlayer(event.getWhoClicked(), "oneline"));
		setItem(17, itemFactory.getBackgroundItem());

		setItem(18, itemFactory.getBackgroundItem());
		setItem(22, itemFactory.getBedWarsItem(),
				event -> teleportPlayer(event.getWhoClicked(), "bedwars"));
		setItem(26, itemFactory.getBackgroundItem());
	}

	private void teleportPlayer(HumanEntity humanEntity, String locationName) {
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer((Player) humanEntity);
		Location location = lobbyPlugin.getLocationProvider().getLocation(locationName);
		cloudPlayer.getPlayer().teleport(location);
	}
}
