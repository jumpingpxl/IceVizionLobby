package de.icevizion.lobby.listener;

import de.icevizion.lobby.utils.DailyRewardUtil;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
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

        if (!event.getPlayer().getItemInHand().getType().equals(Material.NAME_TAG)) {
            if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
                ArmorStand armorStand = (ArmorStand) event.getRightClicked();

                if (armorStand.getCustomName() != null && armorStand.getCustomName().equals("§eTägliche Belohnung")) {
                    dailyRewardUtil.checkDailyReward(event.getPlayer());
                }
            }
        }
    }
}