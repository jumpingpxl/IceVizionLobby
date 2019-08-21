package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.map.MapService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnListener implements Listener {

    private final MapService mapService;

    public PlayerSpawnListener(MapService mapService) {
        this.mapService = mapService;
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(event.getPlayer().getWorld().getSpawnLocation());
    }
}