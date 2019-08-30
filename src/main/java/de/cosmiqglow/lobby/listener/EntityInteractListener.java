package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.utils.DailyRewardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class EntityInteractListener implements Listener {

    private final DailyRewardUtil dailyRewardUtil;

    public EntityInteractListener(DailyRewardUtil dailyRewardUtil) {
        this.dailyRewardUtil = dailyRewardUtil;
    }

    @EventHandler
    public void onArmorInteract(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
        if (!event.getRightClicked().getCustomName().equals("Daily-Reward")) return;
        dailyRewardUtil.giveReward(event.getPlayer());
    }
}
