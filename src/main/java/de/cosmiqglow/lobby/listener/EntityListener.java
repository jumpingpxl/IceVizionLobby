package de.cosmiqglow.lobby.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onCreature(EntitySpawnEvent event) {
        if (!event.getEntityType().equals(EntityType.FIREWORK))
        event.setCancelled(true);
    }

    @EventHandler
    public void onArmor(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemFrame(HangingBreakByEntityEvent event) {
        event.setCancelled(true);
    }
}