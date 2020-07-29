package de.icevizion.lobby.utils.inventorybuilder;

import de.icevizion.lobby.LobbyPlugin;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class InventoryLoader {

	private final LobbyPlugin lobbyPlugin;
	private final Map<HumanEntity, InventoryBuilder> openInventories;

	public InventoryLoader(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		openInventories = new HashMap<>();
	}

	public void openInventory(InventoryBuilder inventoryBuilder) {
		Player player = inventoryBuilder.getCloudPlayer().getPlayer();

		inventoryBuilder.buildInventory();
		openInventories.put(player, inventoryBuilder);
		player.openInventory(inventoryBuilder.getInventory());
	}

	public Map<HumanEntity, InventoryBuilder> getOpenInventories() {
		return openInventories;
	}
}
