package de.icevizion.lobby.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class BlockIgniteListener implements Listener {

	@EventHandler
	public void onIgnite(BlockIgniteEvent event) {
		event.setCancelled(true);
	}
}
