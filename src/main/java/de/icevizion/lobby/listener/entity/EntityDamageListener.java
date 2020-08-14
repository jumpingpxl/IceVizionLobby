package de.icevizion.lobby.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}
}
