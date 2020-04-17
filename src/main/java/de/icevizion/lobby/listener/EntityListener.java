package de.icevizion.lobby.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onArmor(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntity(EntityInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemFrame(HangingBreakByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onVehicle(VehicleDamageEvent event) { event.setCancelled(true); }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame || event.getRightClicked() instanceof ArmorStand) {
            event.setCancelled(true);
        }
    }
}