package de.icevizion.lobby.utils.inventories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.ToSItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ToSInventory extends InventoryBuilder {

	private final LobbyPlugin lobbyPlugin;
	private final ToSItemFactory itemFactory;

	public ToSInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(cloudPlayer, lobbyPlugin.getLocales().getString(cloudPlayer, "inventoryToSTitle"), 27);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new ToSItemFactory(lobbyPlugin, cloudPlayer);
	}

	@Override
	public void draw() {
		setItem(1, itemFactory.getBackgroundItem());

		setItem(9, itemFactory.getInfoItem(),
				event -> lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "tosInfoClick"));
		setItem(10, itemFactory.getBackgroundItem());
		setItem(13, itemFactory.getAcceptItem(), event -> {
			getCloudPlayer().setField("tos", System.currentTimeMillis());
			lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "tosAcceptClick");
			getCloudPlayer().getPlayer().closeInventory();
		});

		setItem(15, itemFactory.getDenyItem(),
				event -> getCloudPlayer().kick(lobbyPlugin.getLocales().getString(getCloudPlayer(), "tosDenyClick")));
		setItem(19, itemFactory.getBackgroundItem());
	}

	@Override
	public boolean onInventoryClose(InventoryCloseEvent event) {
		if (Objects.isNull(getCloudPlayer().getField("tos"))) {

			return true;
		}

		return false;
	}
}
