package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DailyRewardUtil {

    private static final ItemStack PLAYER_REWARD = new ItemBuilder(Material.LIME_DYE).
            setDisplayName("§7Belohnung").build();
    private static final ItemStack PREMIUM_REWARD = new ItemBuilder(Material.GOLD_NUGGET).
            setDisplayName("§6Premium Belohnung").build();;
    private static final long DAY_MILLIS = 1000*60*60*24;

    public Inventory buildInventory(CloudPlayer cloudPlayer) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Tägeliche Belohnung");
        for (int i = 0; i < 8; i++) {
          inventory.setItem(i, ItemUtil.PANE);
        }

        if (cloudPlayer.hasPermission("lobby.premiumreward")) {
            inventory.setItem(12, PLAYER_REWARD);
            inventory.setItem(14, PREMIUM_REWARD);
        } else {
            inventory.setItem(13, PLAYER_REWARD);
        }

        for (int i = 18; i < 26; i++) {
            inventory.setItem(i, ItemUtil.PANE);
        }

        return inventory;
    }

    /**
     * Checks if a player can receive his daily reward
     * @param prefix The prefix for the chat
     * @param player The player to check
     */

    public void checkDailyReward(String prefix, Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        Inventory inventory = buildInventory(cloudPlayer);
        player.openInventory(inventory);
       /* if (!cloudPlayer.extradataContains("daily")) {
            Inventory inventory = buildInventory(cloudPlayer);
            player.openInventory(inventory);
        } else {
            long timestamp = (long) cloudPlayer.extradataGet("daily");
            if (timestamp >= System.currentTimeMillis()) {
                player.sendMessage(prefix + "§cBitte komme morgen wieder um einen Reward zu erhalten");
            } else {
                giveReward(prefix, cloudPlayer);
            }
        }*/
    }

    /**
     * Give the reward to a specific player
     * @param prefix The prefix for the chat
     * @param player The player who get the reward
     */

    private void giveReward(String prefix, CloudPlayer player) {
        int coins = player.hasPermission("lobby.reward.premium") ? 150 : 100;
        int streak = getAndUpdateRewardStreak(player);
        //Add daily reward Streak
        coins = coins + 50 * streak;
        player.addCoins(coins);
        player.extradataSet("daily", System.currentTimeMillis() + getRestDayTime());
        player.sendMessage(prefix + "§7Du hast §6" + coins + " §7Coins bekommen!" + (streak > 0
                ? " " + "Du hast einen Streak von §6" + streak
                : ""));
    }

    /**
     * Get the rest time of the current day
     * @return The rest time of the day
     */

    private long getRestDayTime() {
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        LocalDate tomorrow = now.toLocalDate().plusDays(1);
        ZonedDateTime tomorrowStart = tomorrow.atStartOfDay(zoneId);
        return Duration.between(now, tomorrowStart).toMillis();
    }

    /**
     * Updates the reward streak of a specific player
     * @param cloudPlayer The player
     * @return The current streak
     */

    private int getAndUpdateRewardStreak(CloudPlayer cloudPlayer) {
        int currentStreak = cloudPlayer.extradataContains("dailyStreak")
                ? (int) cloudPlayer.extradataGet("dailyStreak") : 0;
        long timestamp = cloudPlayer.extradataContains("daily")
                ? (long) cloudPlayer.extradataGet("daily") : 0;

        //The player can't even collect his reward currently
        // so just return the current streak
        if (timestamp > System.currentTimeMillis())
            return currentStreak;

        //Check if last reward collection was over a day ago.
        //If he had never collected the reward before, timestamp is 0,
        //so it is definitely bigger than dayMillis
        if (System.currentTimeMillis() - timestamp > DAY_MILLIS) {
            cloudPlayer.offlineExtradataRemove("dailyStreak");
            cloudPlayer.extradataSet("dailyStreak", 0);
            return 0;
        }

        currentStreak++;
        cloudPlayer.extradataSet("dailyStreak", currentStreak);
        return currentStreak;
    }
}