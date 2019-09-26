package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.profile.LobbyProfile;
import de.icevizion.lobby.profile.ProfileCache;
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

    public void changeVisibility(Plugin plugin, Player player) {
        LobbyProfile lobbyProfile = profileCache.getProfile(player);
        int value = lobbyProfile.getHideSettings();
        if (value == 2) {
            value = 0;
        } else {
            value++;
        }
        switch (value) {
            case 0:
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (player == online) continue;
                    if (!player.canSee(online)) {
                        player.showPlayer(plugin, online);
                    }
                }
                player.sendMessage("§7Du siehst nun §aalle §7Spieler wieder");
                break;
            case 1:
                CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
                FriendProfile profile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
                if (profile.getFriends().size() == 0) return;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == player) continue;
                    if (!profile.getRawFriends().containsKey(online.getUniqueId().toString())) continue;
                    if (!player.canSee(online)) {
                        player.showPlayer(plugin, online);
                    }
                }
                player.sendMessage("§7Du siehst nur noch deine §eFreunde");
                break;
            case 2:
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (player == online) continue;
                    if (player.canSee(online)) {
                        player.hidePlayer(plugin, online);
                    }
                }
                player.sendMessage("§7Du siehst nun §ckeine §7Spieler mehr");
                break;
        }
        lobbyProfile.setHideSettings(value);
    }

    public void hideOnJoin(Plugin plugin, Player joiningPlayer) {
        if (Bukkit.getOnlinePlayers().size() <= 2) return;
        for (Map.Entry<Player, LobbyProfile> entrySet : profileCache.getProfiles().entrySet()) {
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