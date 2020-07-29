package de.icevizion.lobby.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LeavesDecayListener implements Listener {

	@EventHandler
	public void onLeaves(LeavesDecayEvent event) {
		event.setCancelled(true);
	}
}
