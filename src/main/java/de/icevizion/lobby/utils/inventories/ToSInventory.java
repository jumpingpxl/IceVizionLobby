package de.icevizion.lobby.utils.inventories;

import de.icevizion.aves.inventory.InventoryRows;
import de.icevizion.aves.inventory.TranslatedInventory;
import de.icevizion.aves.inventory.events.CloseEvent;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.ToSItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ToSInventory extends TranslatedInventory {

	private final ToSItemFactory itemFactory;

	public ToSInventory(LobbyPlugin lobbyPlugin, Locale locale) {
		super(lobbyPlugin.getLocales(), locale, InventoryRows.THREE, "inventoryToSTitle");

		itemFactory = new ToSItemFactory(getTranslator(), locale);
	}

	@Override
	public void draw() {
		setItem(1, itemFactory.getBackgroundItem());

		setItem(9, itemFactory.getInfoItem(),
				event -> getTranslator().sendMessage(event.getCloudPlayer(), "tosInfoClick"));

		setItem(10, itemFactory.getBackgroundItem());
		setItem(13, itemFactory.getAcceptItem(), event -> {
			CloudPlayer cloudPlayer = event.getCloudPlayer();
			cloudPlayer.setField("tos", System.currentTimeMillis());
			getTranslator().sendMessage(cloudPlayer, "tosAcceptClick");
			event.getPlayer().closeInventory();
		});

		setItem(15, itemFactory.getDenyItem(), event -> {
			CloudPlayer cloudPlayer = event.getCloudPlayer();
			cloudPlayer.kick(getTranslator().getString(getLocale(), "tosDenyClick"));
		});
		setItem(19, itemFactory.getBackgroundItem());
	}

	@Override
	public void onInventoryClose(CloseEvent event) {
		event.setCancelled(Objects.isNull(event.getCloudPlayer().getField("tos")));
	}
}
