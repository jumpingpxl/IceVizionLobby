package de.cosmiqglow.lobby.utils;

import com.google.common.base.Preconditions;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VisibilityUtil {

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
                if (profile.getFriends().size() == 0) return;
                if (Bukkit.getOnlinePlayers().size() == 0) return;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == player) continue;
                    if (!profile.getRawFriends().containsKey(online.getUniqueId().toString())) return;
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
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (player == online) continue;
                    if (player.canSee(online)) {
                        player.hidePlayer(plugin, online);
                    }
                }
                break;
        }
    }
}