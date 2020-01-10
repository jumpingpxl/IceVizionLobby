package de.icevizion.lobby.listener;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.profile.LobbyProfile;
import de.icevizion.lobby.profile.ProfileCache;
import de.icevizion.lobby.utils.FriendUtil;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class PlayerFriendListener implements Listener {

    private final ProfileCache profileCache;
    private final FriendUtil friendUtil;

    public PlayerFriendListener(ProfileCache profileCache, FriendUtil friendUtil) {
        this.profileCache = profileCache;
        this.friendUtil = friendUtil;
    }

    @EventHandler
    public void onJoin(NetworkPlayerJoinEvent event) {
        for (Map.Entry<Player, LobbyProfile> profileEntry : profileCache.getProfiles().entrySet()) {
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(profileEntry.getKey());
            FriendProfile friendProfile = FriendSystem.getInstance().
                    getFriendProfile(cloudPlayer);

            if (friendProfile.getRawFriends().containsKey(event.getCloudPlayer().getUuid())) {
                friendUtil.updateInventory(cloudPlayer, profileEntry.getValue().getFriendInventory());
            }
        }
    }

    @EventHandler
    public void onQuit(NetworkPlayerQuitEvent event) {
        for (Map.Entry<Player, LobbyProfile> profileEntry : profileCache.getProfiles().entrySet()) {
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(profileEntry.getKey());
            FriendProfile friendProfile = FriendSystem.getInstance().
                    getFriendProfile(cloudPlayer);

            if (friendProfile.getRawFriends().containsKey(event.getCloudPlayer().getUuid())) {
                friendUtil.updateInventory(cloudPlayer, profileEntry.getValue().getFriendInventory());
            }
        }
    }
}