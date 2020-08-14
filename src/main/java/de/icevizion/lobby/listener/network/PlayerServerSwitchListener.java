package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.lib.redisevent.events.PlayerServerSwitchEvent;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerServerSwitchListener implements Consumer<PlayerServerSwitchEvent> {

	private final LobbyPlugin lobbyPlugin;

	public PlayerServerSwitchListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@Override
	public void accept(PlayerServerSwitchEvent event) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(lobbyPlugin, () -> {
			IClusterSpigot from = lobbyPlugin.getTitanService().getGameServerByIdentifier(
					event.getFrom());
			IClusterSpigot to = lobbyPlugin.getTitanService().getGameServerByIdentifier(event.getTo());
			if (from.getServerType().equals("Lobby")) {
				lobbyPlugin.getLobbySwitcher().updateLobby(from);
			}

			if (to.getServerType().equals("Lobby")) {
				lobbyPlugin.getLobbySwitcher().updateLobby(to);
			}
		}, 5L);
	}
}
