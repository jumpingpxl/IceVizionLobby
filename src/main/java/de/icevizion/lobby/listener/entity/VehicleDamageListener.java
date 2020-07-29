package de.icevizion.lobby.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class VehicleDamageListener implements Listener {

	@EventHandler
	public void onVehicle(VehicleDamageEvent event) {
		event.setCancelled(true);
	}
}
