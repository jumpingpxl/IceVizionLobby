package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerQuitListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		player.getInventory().clear();

		CloudPlayer cloudPlayer = lobbyPlugin.getCloudService().getPlayer(event.getPlayer());
		cloudPlayer.extradataSet("location", player.getLocation().serialize());
		lobbyPlugin.getDoubleJump().getAllowedPlayers().remove(player.getUniqueId());
	}
}