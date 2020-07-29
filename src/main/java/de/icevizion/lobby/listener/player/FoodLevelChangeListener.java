package de.icevizion.lobby.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class FoodLevelChangeListener implements Listener {

	@EventHandler
	public void onFood(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
}
