package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerJoinListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(player);
		player.setGameMode(GameMode.ADVENTURE);

		lobbyPlugin.getLobbyScoreboard().createScoreboard(cloudPlayer);
		lobbyPlugin.getItems().setItems(cloudPlayer);
		lobbyPlugin.getVisibilityTool().handleJoin(cloudPlayer);
		if (cloudPlayer.hasPermission(lobbyPlugin.getDoubleJump().getDoubleJumpPermission())) {
			lobbyPlugin.getDoubleJump().getAllowedPlayers().add(player.getUniqueId());
		}

		if (Objects.isNull(cloudPlayer.getField("tos"))) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(lobbyPlugin,
					() -> lobbyPlugin.getInventories().openToSInventory(cloudPlayer), 20);
		}
	}
}