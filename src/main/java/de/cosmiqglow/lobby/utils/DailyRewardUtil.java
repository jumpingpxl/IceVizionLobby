package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.CustomPlayerHeadBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DailyRewardUtil {

    private final ArmorStand armorStand;
    private final ItemStack head;

    public DailyRewardUtil(Location location) {
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.head = new CustomPlayerHeadBuilder().setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM2OTk5YzFmNTk2NDZmZTAxMjFhOTlkOWIwZmVmMzk5NmVkNzhjNmRjNTU1MzFkYWJjY2E3MDhjMWRjZjkxNiJ9fX0=", "").build();
        this.armorStand.setHelmet(head);
    }

    public void despawn() {
        armorStand.remove();
    }

    public void giveReward(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        int coins = 100;
        cloudPlayer.addCoins(100);
        cloudPlayer.extradataSet("daily", getRestDayTime());
        player.sendMessage("§7Du hast §e100 §7Glows bekommen");
    }

    public boolean checkDailyReward(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        if (!(cloudPlayer.extradataContains("daily"))) {
            return false;
        } else {
            long timestamp = (long) cloudPlayer.extradataGet("daily");

            if (timestamp >= System.currentTimeMillis()) {
                cloudPlayer.redisExtradataRemove("daily");
                return false;
            } else {
                return true;
            }
        }
    }

    public long getRestDayTime() {
        return 0;
    }
}