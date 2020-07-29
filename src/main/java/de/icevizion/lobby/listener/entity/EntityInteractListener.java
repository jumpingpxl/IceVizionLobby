package de.icevizion.lobby.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class EntityInteractListener implements Listener {

	@EventHandler
	public void onEntity(EntityInteractEvent event) {
		event.setCancelled(true);
	}
}
