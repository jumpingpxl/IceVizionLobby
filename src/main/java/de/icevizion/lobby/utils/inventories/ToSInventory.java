package de.icevizion.lobby.utils.inventories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.ToSItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ToSInventory extends InventoryBuilder {

	private final LobbyPlugin lobbyPlugin;
	private final ToSItemFactory itemFactory;

	public ToSInventory(LobbyPlugin lobbyPlugin, Locale locale) {
		super(lobbyPlugin.getLocales().getString(locale, "inventoryToSTitle"), 27);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new ToSItemFactory(lobbyPlugin, locale);
	}

	@Override
	public boolean isCacheable() {
		return true;
	}

	@Override
	public void draw() {
		setItem(1, itemFactory.getBackgroundItem());

		setItem(9, itemFactory.getInfoItem(), event -> {
			CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer((Player) event.getWhoClicked());
			lobbyPlugin.getLocales().sendMessage(cloudPlayer, "tosInfoClick");
		});

		setItem(10, itemFactory.getBackgroundItem());
		setItem(13, itemFactory.getAcceptItem(), event -> {
			CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer((Player) event.getWhoClicked());
			cloudPlayer.setField("tos", System.currentTimeMillis());
			lobbyPlugin.getLocales().sendMessage(cloudPlayer, "tosAcceptClick");
			event.getWhoClicked().closeInventory();
		});

		setItem(15, itemFactory.getDenyItem(), event -> {
			CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer((Player) event.getWhoClicked());
			cloudPlayer.kick(lobbyPlugin.getLocales().getString(cloudPlayer, "tosDenyClick"));
		});
		setItem(19, itemFactory.getBackgroundItem());
	}

	@Override
	public boolean onInventoryClose(InventoryCloseEvent event) {
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer((Player) event.getPlayer());
		return Objects.isNull(cloudPlayer.getField("tos"));
	}
}
