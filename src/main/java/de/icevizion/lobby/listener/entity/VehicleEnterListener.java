package de.icevizion.lobby.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class VehicleEnterListener implements Listener {

	@EventHandler
	public void onVehicleInteract(VehicleEnterEvent event) {
		event.setCancelled(true);
	}
}
