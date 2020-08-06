package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.lib.redisevent.events.ServerAvailableEvent;

import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ServerAvailableListener implements Consumer<ServerAvailableEvent> {

	private final LobbyPlugin lobbyPlugin;

	public ServerAvailableListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@Override
	public void accept(ServerAvailableEvent event) {
		lobbyPlugin.getLobbySwitcher().updateLobbies();
	}
}