package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.utils.DailyRewardUtil;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteractListener implements Listener {

    private final DailyRewardUtil dailyRewardUtil;

    public EntityInteractListener(DailyRewardUtil dailyRewardUtil) {
        this.dailyRewardUtil = dailyRewardUtil;
    }

    @EventHandler
    public void onArmorInteract(PlayerInteractEntityEvent event) {
        event.setCancelled(true);

        if (!event.getRightClicked().equals(EntityType.ARMOR_STAND)) return;

        ArmorStand armorStand = (ArmorStand) event.getRightClicked();

        if (armorStand.getCustomName().equals("Daily-Reward")) {
            dailyRewardUtil.giveReward(event.getPlayer());
        }
    }
}