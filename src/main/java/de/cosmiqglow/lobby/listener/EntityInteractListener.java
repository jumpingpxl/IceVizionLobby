package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.utils.DailyRewardUtil;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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

            if (armorStand.getCustomName().equalsIgnoreCase("Daily-Reward")) {
                dailyRewardUtil.giveReward(event.getPlayer());
            }
        }

    }
}