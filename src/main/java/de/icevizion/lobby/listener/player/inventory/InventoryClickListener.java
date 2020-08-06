package de.icevizion.lobby.listener.player.inventory;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class InventoryClickListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public InventoryClickListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory() == null || event.getClickedInventory() == event.getWhoClicked()
				.getInventory() || event.getClickedInventory().getHolder() != event.getWhoClicked()) {
			event.setCancelled(true);
			return;
		}

		InventoryBuilder clickedInventory = lobbyPlugin.getInventoryLoader().getOpenInventories().get(
				event.getWhoClicked());
		if (Objects.nonNull(clickedInventory) && event.getClickedInventory().getTitle().equals(
				clickedInventory.getInventory().getTitle()) && clickedInventory.getInventory()
				.getViewers()
				.contains(event.getWhoClicked())) {
			event.setCancelled(true);

			Consumer<InventoryClickEvent> clickEvent = clickedInventory.getClickEvents().get(
					event.getRawSlot());
			if (clickEvent != null) {
				clickEvent.accept(event);
			}
		}
	}
}
