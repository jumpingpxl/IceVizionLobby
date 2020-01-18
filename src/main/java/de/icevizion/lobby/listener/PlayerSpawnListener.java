package de.icevizion.lobby.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import de.icevizion.aves.adapter.LocationTypeAdapter;
import de.icevizion.lobby.map.MapService;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnListener implements Listener {

    private final Gson gson;
    private final MapService mapService;

    public PlayerSpawnListener(MapService mapService) {
        this.mapService = mapService;
        this.gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationTypeAdapter()).create();
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(event.getPlayer());
        if (cloudPlayer.extradataContains("location")) {
            event.setSpawnLocation(gson.fromJson((String)cloudPlayer.extradataGet("location"), Location.class));
        } else{
            mapService.getLobbyMap().ifPresent(map -> {
                if (map.getSpawn() != null) {
                    event.setSpawnLocation(map.getSpawn());
                }
            });
        }
    }
}