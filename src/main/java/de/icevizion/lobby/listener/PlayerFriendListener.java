package de.icevizion.lobby.listener;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.profile.LobbyProfile;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.event.NetworkPlayerServerSwitchedEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            updateInventory(event.getCloudPlayer());
        });
    }

    private void updateInventory(CloudPlayer player) {
        for (Map.Entry<Player, LobbyProfile> profileEntry : plugin.getProfileCache().getProfiles().entrySet()) {
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(profileEntry.getKey());
            FriendProfile friendProfile = FriendSystem.getInstance().
                    getFriendProfile(cloudPlayer);

            if (friendProfile.getRawFriends().containsKey(player)) {
                plugin.getFriendUtil().updateInventory(cloudPlayer, profileEntry.getValue().getFriendInventory());
            }
        }
    }
}