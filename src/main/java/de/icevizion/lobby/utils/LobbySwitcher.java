package de.icevizion.lobby.utils;

import de.icevizion.aves.inventory.TranslatedInventory;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventories.LobbiesInventory;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.lib.utils.SpigotComparator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LobbySwitcher {

	private final LobbyPlugin lobbyPlugin;

	public LobbySwitcher(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		lobbyPlugin.getServer().getScheduler().scheduleAsyncRepeatingTask(lobbyPlugin,
				this::updateLobbies, 0L, 100L);
	}

	public List<IClusterSpigot> getActiveLobbies() {
		return lobbyPlugin.getCloudService()
				.getGameServers()
				.stream()
				.filter(clusterSpigot -> clusterSpigot.getServerType().equals("Lobby")
						&& clusterSpigot.getState() == SpigotState.AVAILABLE)
				.sorted(new SpigotComparator())
				.collect(Collectors.toList());
	}

	public void updateLobby(IClusterSpigot lobby) {
		getCachedLobbySwitcher().forEach(
				inventory -> ((LobbiesInventory) inventory).updateLobby(lobby));
	}

	public void updateLobbies() {
		if (lobbyPlugin.getServer().getOnlinePlayers().isEmpty()) {
			return;
		}

		List<IClusterSpigot> activeLobbies = getActiveLobbies();
		getCachedLobbySwitcher().stream()
				.filter(inventory -> !inventory.getViewers().isEmpty())
				.forEach(inventory -> ((LobbiesInventory) inventory).calculateItemPositions(activeLobbies));
	}

	private Collection<TranslatedInventory> getCachedLobbySwitcher() {
		return lobbyPlugin.getInventoryService().getCachedInventories(LobbiesInventory.class).values();
	}
}
