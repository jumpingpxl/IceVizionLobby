package de.icevizion.lobby.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		event.blockList().clear();
	}
}
