package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.SettingsWrapper;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Lobby plugin;

    public PlayerJoinListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(event.getPlayer());
        cloudPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);

        plugin.getItemUtil().setItems(cloudPlayer);
        plugin.getProfileCache().addProfile(cloudPlayer.getPlayer());
        plugin.getVisibilityUtil().hideOnJoin(cloudPlayer.getPlayer());
        plugin.getVisibilityUtil().changeVisibility(
                cloudPlayer, cloudPlayer.getSetting(SettingsWrapper.PLAYER_VISIBILITY.getID()));

        if (cloudPlayer.getField("tos") == null) {
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                    cloudPlayer.getPlayer().openInventory(plugin.getInventoryUtil().getPrivacy()), 20);
        }
    }
}