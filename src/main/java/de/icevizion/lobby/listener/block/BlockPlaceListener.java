package de.icevizion.lobby.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		event.setCancelled(true);
	}
}
