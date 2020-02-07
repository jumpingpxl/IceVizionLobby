package de.icevizion.lobby.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onCreature(CreatureSpawnEvent event) {
        if (!event.getEntityType().equals(EntityType.ARROW))
        event.setCancelled(true);
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
    public void onEntityDismount(final EntityDismountEvent event) {
        if (event.getDismounted().getType() == EntityType.ARROW) {
            event.getDismounted().remove();
        }
    }

    @EventHandler
    public void onItemFrame(HangingBreakByEntityEvent event) {
        event.setCancelled(true);
    }
}