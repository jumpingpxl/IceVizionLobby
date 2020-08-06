package de.icevizion.lobby.listener.player.inventory;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class InventoryCloseListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public InventoryCloseListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory() == event.getPlayer().getInventory()
				|| event.getInventory().getHolder() != event.getPlayer()) {
			return;
		}

		InventoryBuilder closedInventory = lobbyPlugin.getInventoryLoader().getOpenInventories().get(
				event.getPlayer());
		if (Objects.nonNull(closedInventory) && event.getInventory().getTitle().equals(
				closedInventory.getInventory().getTitle()) && closedInventory.getInventory()
				.getViewers()
				.contains(event.getPlayer())) {
			boolean cancel = closedInventory.onInventoryClose(event);
			if (cancel) {
				event.getPlayer().openInventory(closedInventory.getInventory());
				return;
			}

			lobbyPlugin.getInventoryLoader().getOpenInventories().remove(event.getPlayer());
		}
	}
}