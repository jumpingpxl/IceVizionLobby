package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.SettingsWrapper;
import de.icevizion.lobby.utils.inventories.ToSInventory;
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

		lobbyPlugin.getScoreboard().createScoreboard(cloudPlayer);
		lobbyPlugin.getItems().setItems(cloudPlayer);

		//TODO -> optimize
		lobbyPlugin.getVisibilityTool().hideOnJoin(cloudPlayer.getPlayer());
		lobbyPlugin.getVisibilityTool().changeVisibility(cloudPlayer,
				cloudPlayer.getSetting(SettingsWrapper.PLAYER_VISIBILITY.getID()));

		if (Objects.isNull(cloudPlayer.getField("tos"))) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(lobbyPlugin,
					() -> lobbyPlugin.getInventoryLoader().openInventory(new ToSInventory(lobbyPlugin, cloudPlayer)), 20);
		}
	}
}