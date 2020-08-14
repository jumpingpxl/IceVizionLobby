package de.icevizion.lobby.listener;

import org.bukkit.event.Listener;

public class PlayerFriendListener implements Listener {

/*    private final LobbyPlugin plugin;

    public PlayerFriendListener(LobbyPlugin plugin) {
        this.plugin = plugin;
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

    private void updateInventory(CloudPlayer friendPlayer) {
        for (CloudPlayer cloudPlayer : Cloud.getInstance().getCurrentOnlinePlayers()) {
            if (!cloudPlayer.getDisplayName().equals(friendPlayer.getDisplayName())
                        && cloudPlayer.offlineExtradataContains("profile")) {
                if (cloudPlayer.isOnline()) {
                    FriendProfile friendProfile = FriendSystem.getInstance().
                            getFriendProfile(cloudPlayer);
                    if (friendProfile.getRawFriends().containsKey(friendPlayer.getUuid())) {
                        plugin.getFriendUtil().updateInventory(cloudPlayer,
                                (Inventory) cloudPlayer.offlineExtradataGet("profile"));
                    }
                }
            }
        }
    } */
}