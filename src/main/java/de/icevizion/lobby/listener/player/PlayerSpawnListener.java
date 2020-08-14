package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerSpawnListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onSpawn(PlayerSpawnLocationEvent event) {
		lobbyPlugin.getServer().getOnlinePlayers().forEach(
				player -> player.hidePlayer(event.getPlayer()));
	}
}