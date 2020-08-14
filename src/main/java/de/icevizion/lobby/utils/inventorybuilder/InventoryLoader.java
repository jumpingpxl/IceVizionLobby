package de.icevizion.lobby.utils.inventorybuilder;

import com.google.common.collect.Maps;
import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class InventoryLoader {

	private final LobbyPlugin lobbyPlugin;
	private final Map<Locale, InventoryBuilder> cachedInventories;
	private final Map<HumanEntity, InventoryBuilder> openInventories;

	public InventoryLoader(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		cachedInventories = Maps.newHashMap();
		openInventories = Maps.newHashMap();
	}

	public Optional<InventoryBuilder> getCachedInventory(Locale locale,
	                                                     Class<? extends InventoryBuilder> inventory) {
		for (Map.Entry<Locale, InventoryBuilder> cachedInventoryEntry : cachedInventories.entrySet()) {
			if (cachedInventoryEntry.getKey() == locale
					&& cachedInventoryEntry.getValue().getClass() == inventory) {
				return Optional.of(cachedInventoryEntry.getValue());
			}
		}

		return Optional.empty();
	}

	public void openInventory(CloudPlayer cloudPlayer, InventoryBuilder inventoryBuilder) {
		Player player = cloudPlayer.getPlayer();

		inventoryBuilder.buildInventory();
		openInventories.put(player, inventoryBuilder);
		player.openInventory(inventoryBuilder.getInventory());

		if (inventoryBuilder.isCacheable()) {
			cachedInventories.put(cloudPlayer.getLocale(), inventoryBuilder);
		}
	}

	public boolean openCachedInventory(CloudPlayer cloudPlayer,
	                                   Class<? extends InventoryBuilder> inventory) {
		Optional<InventoryBuilder> optionalInventory = lobbyPlugin.getInventoryLoader()
				.getCachedInventory(cloudPlayer.getLocale(), inventory);
		if (optionalInventory.isPresent()) {
			lobbyPlugin.getInventoryLoader().openInventory(cloudPlayer, optionalInventory.get());
			return true;
		}

		return false;
	}

	public Map<HumanEntity, InventoryBuilder> getOpenInventories() {
		return openInventories;
	}

	public Map<Locale, InventoryBuilder> getCachedInventories() {
		return cachedInventories;
	}
}
