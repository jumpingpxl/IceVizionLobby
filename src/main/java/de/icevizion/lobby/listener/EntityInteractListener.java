package de.icevizion.lobby.listener;

import de.icevizion.lobby.utils.DailyRewardUtil;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractListener implements Listener {

    private final String prefix;
    private final DailyRewardUtil dailyRewardUtil;

    public EntityInteractListener(String prefix, DailyRewardUtil dailyRewardUtil) {
        this.prefix = prefix;
        this.dailyRewardUtil = dailyRewardUtil;
    }

    @EventHandler
    public void onArmorInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();

            if (armorStand.getCustomName() != null && armorStand.getCustomName().equals("§eTägliche Belohnung")) {
                dailyRewardUtil.checkDailyReward(prefix, event.getPlayer());
            }
        }
    }
}