package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.SettingsUtil;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
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

        Player player = event.getPlayer();
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);

        plugin.getItemUtil().setItems(player);
        plugin.getProfileCache().addProfile(player);
        plugin.getVisibilityUtil().hideOnJoin(plugin, player);
        plugin.getVisibilityUtil().changeVisibility(plugin,
                cloudPlayer, cloudPlayer.getSetting(SettingsUtil.PLAYER_VISIBILITY));

        if (cloudPlayer.getField("tos") == null) {
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                    player.openInventory(plugin.getInventoryUtil().getPrivacy()), 20);
        }

        if (cloudPlayer.getSetting(199) == 1) {
            cloudPlayer.setSetting(199, 0);
        }
    }
}