package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Map;

public class PlayerSpawnListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerSpawnListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onSpawn(PlayerSpawnLocationEvent event) {
		lobbyPlugin.getServer().getOnlinePlayers().forEach(
				player -> player.hidePlayer(event.getPlayer()));

		lobbyPlugin.getServer().getScheduler().runTaskLaterAsynchronously(lobbyPlugin, () -> {
			CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(event.getPlayer());
			if (!cloudPlayer.extradataContains("location") || !(cloudPlayer.extradataGet(
					"location") instanceof Map)) {
				event.setSpawnLocation(lobbyPlugin.getLocationProvider().getLocation("spawn"));
				return;
			}

			event.setSpawnLocation(
					Location.deserialize((Map<String, Object>) cloudPlayer.extradataGet("location")));
		}, 20L);
	}
}