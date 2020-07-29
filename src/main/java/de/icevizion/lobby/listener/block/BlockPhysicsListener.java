package de.icevizion.lobby.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class BlockPhysicsListener implements Listener {

	@EventHandler
	public void onPhysics(BlockPhysicsEvent event) {
		event.setCancelled(true);
	}
}
