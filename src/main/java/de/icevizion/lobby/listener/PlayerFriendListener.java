package de.icevizion.lobby.listener;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.cosmiqglow.component.friendsystem.spigot.FriendUpdateEvent;
import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.profile.LobbyProfile;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.event.NetworkPlayerServerSwitchedEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class PlayerFriendListener implements Listener {

    private final Lobby plugin;

    public PlayerFriendListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(NetworkPlayerJoinEvent event) {
        updateInventory(event.getCloudPlayer());
    }

    @EventHandler
    public void onQuit(NetworkPlayerQuitEvent event) {
        updateInventory(event.getCloudPlayer());
    }

    @EventHandler
    public void onSwitch(NetworkPlayerServerSwitchedEvent event) {
        plugin.getExecutorService().execute(() -> updateInventory(event.getCloudPlayer()));
    }

    @EventHandler
    public void onFriendUpdate(FriendUpdateEvent event) {
        if (event.getCloudPlayer().getSpigot().getDisplayName().startsWith("Lobby")
                || event.getFriendPlayer().getSpigot().getDisplayName().startsWith("Lobby")) {
            LobbyProfile playerProfile = plugin.getProfileCache().getProfile(event.getCloudPlayer().getPlayer());
            LobbyProfile friendProfile = plugin.getProfileCache().getProfile(event.getFriendPlayer().getPlayer());
            
            if (playerProfile.getFriendInventory() != null) {
                plugin.getFriendUtil().updateInventory(event.getCloudPlayer(), plugin.getItemUtil().getFriendLayout(),
                        playerProfile.getFriendInventory());
            }
            
            if (friendProfile.getFriendInventory() != null) {
                plugin.getFriendUtil().updateInventory(event.getFriendPlayer(), plugin.getItemUtil().getFriendLayout(),
                        playerProfile.getFriendInventory());
            }
        }
        updateInventory(event.getFriendPlayer());
    }

    private void updateInventory(CloudPlayer player) {
        plugin.getProfileCache().getLock().lock();
        try {
            for (Map.Entry<Player, LobbyProfile> profileEntry : plugin.getProfileCache().getProfiles().entrySet()) {
                if (profileEntry.getValue().getFriendInventory() != null) {
                    CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(profileEntry.getKey());

                    if (cloudPlayer.isOnline()) {
                        FriendProfile friendProfile = FriendSystem.getInstance().
                                getFriendProfile(cloudPlayer);

                        if (friendProfile.getRawFriends().containsKey(player.getUuid())) {
                            plugin.getFriendUtil().updateInventory(cloudPlayer, plugin.getItemUtil().getFriendLayout(),
                                    profileEntry.getValue().getFriendInventory());
                        }
                    }
                }
            }
        } finally {
            plugin.getProfileCache().getLock().unlock();
        }
    }
}