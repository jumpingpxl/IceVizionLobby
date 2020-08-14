package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.RankReloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.UUID;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class RankReloadListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public RankReloadListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onRankReload(RankReloadEvent event) {
		Set<UUID> allowedPlayers = lobbyPlugin.getDoubleJump().getAllowedPlayers();
		allowedPlayers.clear();

		lobbyPlugin.getTitanService().getOnlinePlayers().stream().filter(
				cloudPlayer -> cloudPlayer.hasPermission(
						lobbyPlugin.getDoubleJump().getDoubleJumpPermission())).forEach(
				cloudPlayer -> allowedPlayers.add(cloudPlayer.getPlayer().getUniqueId()));
	}
}
