package de.icevizion.lobby.listener.player.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerPickupItemListener implements Listener {

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
}
