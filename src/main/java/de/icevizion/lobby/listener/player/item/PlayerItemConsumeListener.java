package de.icevizion.lobby.listener.player.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerItemConsumeListener implements Listener {

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		event.setCancelled(true);
	}
}
