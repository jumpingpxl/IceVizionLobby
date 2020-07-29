package de.icevizion.lobby.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}
}
