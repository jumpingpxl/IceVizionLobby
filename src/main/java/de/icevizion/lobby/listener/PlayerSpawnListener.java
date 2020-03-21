package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.map.MapService;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
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
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(event.getPlayer());
        if (cloudPlayer.extradataContains("location")) {
            event.setSpawnLocation(
                    Lobby.GSON.fromJson((String)cloudPlayer.extradataGet("location"), Location.class));
        } else{
            mapService.getLobbyMap().ifPresent(map -> {
                if (map.getSpawn() != null) {
                    event.setSpawnLocation(map.getSpawn());
                }
            });
        }
    }
}