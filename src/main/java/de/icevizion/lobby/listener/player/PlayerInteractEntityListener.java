package de.icevizion.lobby.listener.player;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerInteractEntityListener implements Listener {

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof ItemFrame
				|| event.getRightClicked() instanceof ArmorStand) {
			event.setCancelled(true);
		}
	}
}
