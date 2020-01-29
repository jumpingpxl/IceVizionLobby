package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.feature.SnowService;
import de.icevizion.lobby.profile.ProfileCache;
import net.titan.spigot.Cloud;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SnowService snowService;
    private final ProfileCache profileCache;

    public PlayerQuitListener(SnowService snowService, ProfileCache profileCache) {
        this.snowService = snowService;
        this.profileCache = profileCache;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        profileCache.removeProfile(event.getPlayer());
        snowService.removePlayer(event.getPlayer());

        Cloud.getInstance().getPlayer(event.getPlayer()).extradataSet("location",
                Lobby.GSON.toJson(event.getPlayer().getLocation()));

    }
}