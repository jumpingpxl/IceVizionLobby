package de.icevizion.lobby.listener;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamageOther(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && (event.getDamager() instanceof Player)) {
            CloudPlayer player = Cloud.getInstance().getPlayer((Player) event.getDamager());
            if (player.getPlayer().getItemInHand().getType().equals(Material.SKULL_ITEM)) {
                CloudPlayer clickedPlayer = Cloud.getInstance().getPlayer((Player) event.getEntity());
                FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(player);
                FriendProfile clickedProfile = FriendSystem.getInstance().getFriendProfile(clickedPlayer);
                if (friendProfile.getFriends().contains(clickedPlayer)) return;
                if (clickedProfile.getRawRequests().containsKey(player.getUuid())) return;
                player.dispatchCommand("friend", new String[]{"add", clickedPlayer.getUuid()});
            }
        }
    }
}