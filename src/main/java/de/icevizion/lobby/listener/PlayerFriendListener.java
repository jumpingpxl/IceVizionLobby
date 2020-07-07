package de.icevizion.lobby.listener;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.cosmiqglow.component.friendsystem.spigot.FriendUpdateEvent;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.event.NetworkPlayerServerSwitchedEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

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
        if (event.getCloudPlayer().getSpigot() != null &&
                event.getCloudPlayer().getSpigot().getDisplayName().startsWith("Lobby") &&
                    event.getCloudPlayer().offlineExtradataContains("profile")) {
            plugin.getFriendUtil().updateInventory(event.getCloudPlayer(),
                    (Inventory) event.getCloudPlayer().offlineExtradataGet("profile"));
        }

        if (event.getFriendPlayer().getSpigot() != null &&
                event.getFriendPlayer().getSpigot().getDisplayName().startsWith("Lobby") &&
                event.getFriendPlayer().offlineExtradataContains("profile")) {
            plugin.getFriendUtil().updateInventory(event.getFriendPlayer(),
                    (Inventory) event.getFriendPlayer().offlineExtradataGet("profile"));
        }
    }

    private void updateInventory(CloudPlayer player) {
        for (CloudPlayer cloudPlayer : Cloud.getInstance().getCurrentOnlinePlayers()) {
            if (!cloudPlayer.getDisplayName().equals(player.getDisplayName())
                        && cloudPlayer.offlineExtradataContains("profile")) {
                if (cloudPlayer.isOnline()) {
                    FriendProfile friendProfile = FriendSystem.getInstance().
                            getFriendProfile(cloudPlayer);

                    if (friendProfile.getRawFriends().containsKey(player.getUuid())) {
                        plugin.getFriendUtil().updateInventory(cloudPlayer,
                                (Inventory) cloudPlayer.offlineExtradataGet("profile"));
                    }
                }
            }
        }
    }
}