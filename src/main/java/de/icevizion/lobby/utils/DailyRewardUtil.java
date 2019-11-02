package de.icevizion.lobby.utils;

import de.icevizion.aves.item.CustomPlayerHeadBuilder;
import de.icevizion.aves.util.LocationUtil;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DailyRewardUtil {
    private static final long dayMillis = 1000*60*60*24;

    private final ArmorStand armorStand;
    private final ItemStack head;

    public DailyRewardUtil(Location location) {
        this.armorStand = (ArmorStand) LocationUtil.getCenter(location.getBlock()).
                add(0,0.2,0).getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.head = new CustomPlayerHeadBuilder().setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM2OTk5YzFmNTk2NDZmZTAxMjFhOTlkOWIwZmVmMzk5NmVkNzhjNmRjNTU1MzFkYWJjY2E3MDhjMWRjZjkxNiJ9fX0=", "").build();
        this.armorStand.setHelmet(head);
        this.armorStand.setCustomName("§eTägliche Belohnung");
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);
        this.armorStand.setCustomNameVisible(true);
    }

    public void despawn() {
        armorStand.remove();
    }

    public void checkDailyReward(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        if (!cloudPlayer.extradataContains("daily")) {
            giveReward(player);
        } else {
            long timestamp = (long) cloudPlayer.extradataGet("daily");
            if (timestamp >= System.currentTimeMillis()) {
                player.sendMessage("§cBitte komme morgen wieder um einen Reward zu erhalten");
            } else {
                giveReward(player);
            }
        }
    }

    private void giveReward(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        int coins = cloudPlayer.hasPermission("lobby.reward.premium") ? 150 : 100;
        int streak = getAndUpdateRewardStreak(cloudPlayer);
        //Add daily reward Streak
        coins += 50 * streak;
        cloudPlayer.addCoins(coins);
        cloudPlayer.extradataSet("daily", System.currentTimeMillis() + getRestDayTime());
        player.sendMessage("§7Du hast §e" + coins + " §7Coins bekommen!" + (streak > 0 ? "Du hast einen Streak von "+(streak+1) : ""));
    }

    private long getRestDayTime() {
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        LocalDate tomorrow = now.toLocalDate().plusDays(1);
        ZonedDateTime tomorrowStart = tomorrow.atStartOfDay(zoneId);
        return Duration.between(now, tomorrowStart).toMillis();
    }

    private int getAndUpdateRewardStreak(CloudPlayer cloudPlayer) {
        int currentStreak = cloudPlayer.extradataContains("dailyStreak")
                ? (int) cloudPlayer.extradataGet("dailyStreak") : 0;
        long timestamp = (long) cloudPlayer.extradataGet("daily");

        //The player can't even collect his reward currently
        // so just return the current streak
        if (timestamp > System.currentTimeMillis())
            return currentStreak;

        //Check if last reward collection was over a day ago.
        //If he had never collected the reward before, timestamp is 0,
        //so it is definitely bigger than dayMillis
        if (System.currentTimeMillis() - timestamp > dayMillis) {
            cloudPlayer.offlineExtradataRemove("dailyStreak");
            return 0;
        }

        currentStreak++;
        cloudPlayer.extradataSet("dailyStreak", currentStreak);
        return currentStreak;
    }
}