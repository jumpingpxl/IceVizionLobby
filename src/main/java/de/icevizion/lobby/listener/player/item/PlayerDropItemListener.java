package de.icevizion.lobby.listener.player.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerDropItemListener implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
}
