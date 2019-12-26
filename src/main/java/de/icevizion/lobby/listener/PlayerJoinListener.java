package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.feature.SnowService;
import net.titan.spigot.Cloud;
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

        player.setGameMode(GameMode.ADVENTURE);

        plugin.getItemUtil().setItems(player);
        plugin.getProfileCache().addProfile(player);
        plugin.getVisibilityUtil().hideOnJoin(plugin, player);

        if (Cloud.getInstance().getPlayer(player).getSetting(199) == 1) {
            plugin.getSnowService().addPlayer(player);
        }
    }
}