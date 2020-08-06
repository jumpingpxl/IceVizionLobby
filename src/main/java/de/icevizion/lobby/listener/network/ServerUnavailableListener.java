package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.lib.redisevent.events.ServerUnavailableEvent;

import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ServerUnavailableListener implements Consumer<ServerUnavailableEvent> {

	private final LobbyPlugin lobbyPlugin;

	public ServerUnavailableListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@Override
	public void accept(ServerUnavailableEvent event) {
		lobbyPlugin.getLobbySwitcher().updateLobbies();
	}
}
