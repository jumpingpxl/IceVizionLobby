package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventories.LobbiesInventory;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.lib.network.spigot.SpigotState;
import net.titan.lib.utils.SpigotComparator;

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
		return lobbyPlugin.getTitanService()
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
		getCachedLobbySwitcher().forEach(
				inventory -> ((LobbiesInventory) inventory).calculateItemPositions(activeLobbies));
	}

	private List<InventoryBuilder> getCachedLobbySwitcher() {
		return lobbyPlugin.getInventoryLoader().getCachedInventories().values().stream().filter(
				inventoryBuilder -> inventoryBuilder instanceof LobbiesInventory).collect(
				Collectors.toList());
	}
}
