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
        mapService.getLobbyMap().ifPresent(map -> {
            if (map.getSpawn() != null) {
                event.setSpawnLocation(map.getSpawn());
            }
        });
    }
}