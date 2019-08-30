package de.cosmiqglow.lobby.utils;

import com.google.common.base.Preconditions;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class VisibilityUtil {

    private final Map<Player, Integer> hiddenPlayers;

    public VisibilityUtil() {
        this.hiddenPlayers = new HashMap<>();
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

                Bukkit.broadcastMessage("Friendsize " + profile.getFriends().size());

                if (profile.getFriends().size() == 0) return;

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (player == online) continue;
                    if (!profile.getRawFriends().containsKey(online.getUniqueId())) continue;
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

    public void addPlayer(Player paramPlayer, int value) {
        this.hiddenPlayers.putIfAbsent(paramPlayer, value);
    }

    public void removePlayer(Player paramPlayer) {
        this.hiddenPlayers.remove(paramPlayer);
    }

    public Map<Player, Integer> getHiddenPlayers() {
        return hiddenPlayers;
    }
}