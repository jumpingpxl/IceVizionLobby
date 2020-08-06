package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerToggleFlightListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerToggleFlightListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() != GameMode.ADVENTURE) {
			return;
		}

		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(player);
		if (cloudPlayer.redisExtradataContains("teamSpec")) {
			return;
		}

		if (!player.isFlying()) {
			event.setCancelled(true);
		}

		player.setAllowFlight(false);
		Vector vector = player.getLocation().getDirection().multiply(1.5D).setY(1);
		player.setVelocity(vector);
		player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 10.0F, -10.0F);
	}
}
