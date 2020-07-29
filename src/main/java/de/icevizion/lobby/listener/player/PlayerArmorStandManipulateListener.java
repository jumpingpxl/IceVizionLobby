package de.icevizion.lobby.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerArmorStandManipulateListener implements Listener {

	@EventHandler
	public void onArmor(PlayerArmorStandManipulateEvent event) {
		event.setCancelled(true);
	}
}
