package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VisibilityUtil {

    public void changeVisibility(Lobby plugin, CloudPlayer cloudPlayer, int value) {
        Player player = cloudPlayer.getPlayer();
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
                FriendProfile profile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
                if (profile.getFriends().size() == 0) return;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == player) continue;
                    if (!profile.getRawFriends().containsKey(online.getUniqueId().toString())) continue;
                    if (!player.canSee(online)) {
                        player.showPlayer(plugin, online);
                    }
                }
                break;
            case 2:
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (player == online) continue;
                    if (player.canSee(online)) {
                        player.hidePlayer(plugin, online);
                    }
                }
                break;
        }
    }

    public void hideOnJoin(Plugin plugin, Player joiningPlayer) {
        for (CloudPlayer cloudPlayers : Cloud.getInstance().getCurrentOnlinePlayers()) {
            switch (cloudPlayers.getSetting(SettingsUtil.PLAYER_VISIBILITY)) {
                case 2:
                    cloudPlayers.getPlayer().hidePlayer(plugin, joiningPlayer);
                    break;
                case 1:
                    FriendProfile profile = FriendSystem.getInstance().getFriendProfile(cloudPlayers);
                    if (!profile.getRawFriends().containsKey(joiningPlayer.getUniqueId().toString())) {
                        cloudPlayers.getPlayer().hidePlayer(plugin, joiningPlayer);
                    }
                    break;
            }
        }
    }
}