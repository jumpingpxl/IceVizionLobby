package de.icevizion.lobby.listener;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.utils.DailyRewardUtil;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractListener implements Listener {

    private final DailyRewardUtil dailyRewardUtil;

    public EntityInteractListener(DailyRewardUtil dailyRewardUtil) {
        this.dailyRewardUtil = dailyRewardUtil;
    }

    @EventHandler
    public void onArmorInteract(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);

        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();

            if (armorStand.getCustomName().equalsIgnoreCase("§eTägliche Belohnung")) {
                dailyRewardUtil.checkDailyReward(event.getPlayer());
            }
        }

        if (event.getPlayer().getItemOnCursor().equals(Material.PLAYER_HEAD) & event.getRightClicked().getType().equals(EntityType.PLAYER)) {
            CloudPlayer player = Cloud.getInstance().getPlayer(event.getPlayer());
            CloudPlayer clickedPlayer = Cloud.getInstance().getPlayer((Player) event.getRightClicked());

            FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(player);

            if (friendProfile.getFriends().contains(clickedPlayer)) {
                return;
            } else {
                player.dispatchCommand("friend", new String[]{"add", clickedPlayer.getUsername()});
            }
        }
    }
}