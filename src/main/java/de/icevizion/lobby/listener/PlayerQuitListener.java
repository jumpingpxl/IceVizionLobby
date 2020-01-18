package de.icevizion.lobby.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.icevizion.aves.adapter.LocationTypeAdapter;
import de.icevizion.lobby.feature.SnowService;
import de.icevizion.lobby.profile.ProfileCache;
import net.titan.spigot.Cloud;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SnowService snowService;
    private final ProfileCache profileCache;

    private final Gson gson;

    public PlayerQuitListener(SnowService snowService, ProfileCache profileCache) {
        this.snowService = snowService;
        this.profileCache = profileCache;
        this.gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationTypeAdapter()).create();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        profileCache.removeProfile(event.getPlayer());
        snowService.removePlayer(event.getPlayer());

        Cloud.getInstance().getPlayer(event.getPlayer()).extradataSet("location",
                gson.toJson(event.getPlayer().getLocation()));

    }
}