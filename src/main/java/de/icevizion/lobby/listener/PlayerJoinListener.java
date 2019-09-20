package de.icevizion.lobby.listener;

import de.cosmiqglow.aves.item.CustomPlayerHeadBuilder;
import de.cosmiqglow.aves.item.FireworkBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
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

        plugin.getItemUtil().setItems(player);
        plugin.getProfileCache().addProfile(player);
        plugin.getVisibilityUtil().hideOnJoin(plugin, player);

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        player.getInventory().setItem(7,
                new CustomPlayerHeadBuilder().
                        setSkinOverValues(cloudPlayer.getSkinValue(), "").
                        setDisplayName("§e✦ §aFreunde").addLore("§e» §7Interagiere mit deinen Freunden§7.").build());

        if (cloudPlayer.getFirstLogin() + 5000L >= System.currentTimeMillis()) {
            new FireworkBuilder().addEffect(FireworkEffect.builder().with(FireworkEffect.Type.STAR).
                    withColor(Color.YELLOW).build()).setPower(1).build();
        }
    }
}