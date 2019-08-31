package de.cosmiqglow.lobby.utils;

import com.google.common.base.Preconditions;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.cosmiqglow.component.partysystem.spigot.Party;
import de.cosmiqglow.component.partysystem.spigot.PartySystem;
import de.cosmiqglow.lobby.profile.LobbyProfile;
import de.cosmiqglow.lobby.profile.ProfileCache;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class VisibilityUtil {

    private final ProfileCache profileCache;

    public VisibilityUtil(ProfileCache profileCache) {
        this.profileCache = profileCache;
    }

    public void changeVisibility(Plugin plugin, int value, Player player) {
        Preconditions.checkArgument(value >= 0, "The value can not be negative");
        switch (value) {
            case 0:
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (player == online) continue;
                    if (!player.canSee(online)) {
                        player.showPlayer(plugin, online);
                    }
                }
                break;
            case 1:
                CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
                FriendProfile profile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
                Party party = PartySystem.getInstance().getParty(cloudPlayer);
                if (profile.getFriends().size() == 0 ^ party == null) return;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == player) continue;
                    if (!profile.getRawFriends().containsKey(online.getUniqueId().toString()) ^
                            !party.getMemberUUIDs().contains(online.getUniqueId().toString())) continue;
                    if (!player.canSee(online)) {
                        player.showPlayer(plugin, online);
                    }
                }
                break;
            case 2:
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (player == online) continue;
                        if (player.canSee(online)) {
                            player.hidePlayer(plugin, online);
                        }
                    }
                }, 80L);
                break;
        }
        profileCache.getProfiles().get(player).setHideSettings(value);
    }

    public void hideOnJoin(Plugin plugin, Player joiningPlayer) {
        if (Bukkit.getOnlinePlayers().size() <= 2) return;
        for (Map.Entry<Player, LobbyProfile> entrySet : profileCache.getProfiles().entrySet()) {
            Bukkit.broadcastMessage(entrySet.getKey().getDisplayName() + " Setting: " +  entrySet.getValue().getHideSettings());
            switch (entrySet.getValue().getHideSettings()) {
                case 2:
                    entrySet.getKey().hidePlayer(plugin, joiningPlayer);
                    break;
                case 1:
                    CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(entrySet.getKey());
                    FriendProfile profile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
                    if (!profile.getRawFriends().containsKey(joiningPlayer.getUniqueId().toString())) {
                        entrySet.getKey().hidePlayer(plugin, joiningPlayer);
                    }
                    break;
            }
        }
    }
}