package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ColoredBuilder;
import de.icevizion.aves.item.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DailyRewardUtil {

    private static final ItemStack PLAYER_REWARD = new ColoredBuilder(ColoredBuilder.DyeType.DYE)
        .setColor(DyeColor.LIME).setDisplayName("§7Belohnung").build();
    private static final ItemStack PREMIUM_REWARD = new ItemBuilder(Material.GOLD_NUGGET).
            setDisplayName("§6Premium Belohnung").build();
    private static final ItemStack REWARD_CLAIMED = new ColoredBuilder(ColoredBuilder.DyeType.DYE)
            .setColor(DyeColor.GRAY).setDisplayName("§cBelohnung wurde schon abgeholt").build();
    private static final long DAY_MILLIS = 1000*60*60*24;

    /**
     * Creates the view for the daily reward.-
     * @param cloudPlayer The player for the view
     * @return The created inventory
     */

    public Inventory buildInventory(CloudPlayer cloudPlayer) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Tägliche Belohnung");
        for (int i = 0; i < 9; i++) {
          inventory.setItem(i, ItemUtil.PANE);
        }

        updateDyes(cloudPlayer, inventory);

        for (int i = 18; i < 27; i++) {
            inventory.setItem(i, ItemUtil.PANE);
        }

        cloudPlayer.offlineExtradataSet("dailyReward", inventory);

        return inventory;
    }

    /**
     * Checks if the dyes need to be updated for the reward.
     * @param cloudPlayer The given player
     * @param inventory The given inventory from the player
     */

    public void updateDyes(CloudPlayer cloudPlayer, Inventory inventory) {
        if (cloudPlayer.hasPermission("lobby.premiumreward")) {
            if (cloudPlayer.extradataContains("daily")
                    && (long) cloudPlayer.extradataGet("daily") > System.currentTimeMillis())  {
                inventory.setItem(12, REWARD_CLAIMED);
            } else {
                inventory.setItem(12, PLAYER_REWARD);
            }

            if (cloudPlayer.extradataContains("daily-premium")
                    && (long) cloudPlayer.extradataGet("daily-premium") > System.currentTimeMillis())  {
                inventory.setItem(14, REWARD_CLAIMED);
            } else {
                inventory.setItem(14, PREMIUM_REWARD);
            }
        } else {
            if (cloudPlayer.extradataContains("daily")
                    && (long) cloudPlayer.extradataGet("daily") > System.currentTimeMillis())  {
                inventory.setItem(13, REWARD_CLAIMED);
            } else {
                inventory.setItem(13, PLAYER_REWARD);
            }
        }
    }

    /**
     * Give the reward to a specific player
     * @param player The player who get the reward
     */

    public void giveReward(CloudPlayer player, Inventory inventory, String prefix, String itemName) {
        int streak = getAndUpdateRewardStreak(player);
        long timestamp;
        switch (itemName) {
            case "Belohnung":
                if (!player.extradataContains("daily")) {
                    setValue(player, "daily", prefix, 100, streak);
                } else {
                    timestamp = (long) player.extradataGet("daily");

                    if (timestamp <= System.currentTimeMillis()) {
                        setValue(player, "daily", prefix,100, streak);
                    } else {
                        player.sendMessage(prefix + "§cDu hast deine Belohnung schon abgeholt");
                    }
                }

                if (player.hasPermission("lobby.premiumreward")) {
                    inventory.setItem(12, REWARD_CLAIMED);
                } else {
                    inventory.setItem(13, REWARD_CLAIMED);
                }
                break;
            case "Premium Belohnung":
                if (!player.extradataContains("daily-premium")) {
                    setValue(player, "daily-premium", prefix,150, streak);
                } else {
                    timestamp = (long) player.extradataGet("daily-premium");
                    if (timestamp <= System.currentTimeMillis()) {
                        setValue(player, "daily-premium", prefix,150, streak);
                    } else {
                        player.sendMessage(prefix + "§cDu hast deine Belohnung schon abgeholt");
                    }
                }
                inventory.setItem(14, REWARD_CLAIMED);
                break;
        }
    }

    private void setValue(CloudPlayer player, String key, String prefix, int coins, int streak) {
        coins = coins + (50 * streak);
        player.extradataSet(key, System.currentTimeMillis() + getRestDayTime());
        player.addCoins(coins);
        player.sendMessage(prefix + "§7Du hast §6" + coins + " §7Coins bekommen!" + (streak > 0
                ? " " + "Du hast eine Streak von §6" + (streak + 1) + "§7 Tagen!"
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
