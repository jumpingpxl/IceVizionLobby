package de.icevizion.lobby.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class HangingBreakByEntityListener implements Listener {

	@EventHandler
	public void onItemFrame(HangingBreakByEntityEvent event) {
		event.setCancelled(true);
	}
}
