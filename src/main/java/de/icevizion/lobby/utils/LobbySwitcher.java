package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventories.LobbiesInventory;
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
		lobbyPlugin.getServer().getScheduler().scheduleAsyncRepeatingTask(lobbyPlugin, this::updateLobbies, 0L, 100L);
	}

	public void updateLobby(IClusterSpigot lobby) {
		lobbyPlugin.getInventoryLoader().getOpenInventories().values().stream().filter(
				inventory -> inventory instanceof LobbiesInventory).forEach(
				inventory -> ((LobbiesInventory) inventory).updateLobby(lobby));
	}

	private void updateLobbies() {
		if (lobbyPlugin.getServer().getOnlinePlayers().isEmpty()) {
			return;
		}

		List<IClusterSpigot> activeLobbies = getActiveLobbies();
		lobbyPlugin.getInventoryLoader().getOpenInventories().values().stream().filter(
				inventory -> inventory instanceof LobbiesInventory).forEach(
				inventory -> ((LobbiesInventory) inventory).calculateItemPositions(activeLobbies));
	}

	private List<IClusterSpigot> getActiveLobbies() {
		return lobbyPlugin.getCloud().getSpigots().stream().filter(clusterSpigot ->
				clusterSpigot.getServerType().equals("Lobby") && clusterSpigot.getState() == SpigotState.AVAILABLE)
				.sorted(new SpigotComparator()).collect(Collectors.toList());
	}
}
