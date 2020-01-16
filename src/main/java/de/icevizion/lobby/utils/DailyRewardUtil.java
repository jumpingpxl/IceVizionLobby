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

    private final String prefix;

    public DailyRewardUtil(String prefix) {
        this.prefix = prefix;
    }

    public Inventory buildInventory(CloudPlayer cloudPlayer) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Tägliche Belohnung");
        for (int i = 0; i < 9; i++) {
          inventory.setItem(i, ItemUtil.PANE);
        }

        if (cloudPlayer.hasPermission("lobby.premiumreward")) {
            inventory.setItem(12, PLAYER_REWARD);
            inventory.setItem(14, PREMIUM_REWARD);
        } else {
            inventory.setItem(13, PLAYER_REWARD);
        }

        for (int i = 18; i < 27; i++) {
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
        if (canAccessReward(cloudPlayer)) {
            Inventory inventory = buildInventory(cloudPlayer);
            player.openInventory(inventory);
        } else {
            player.sendMessage(prefix + "§cBitte komme morgen wieder um einen Reward zu erhalten");
        }
    }

    private boolean canAccessReward(CloudPlayer cloudPlayer) {
        //Check if the player has never got his normal reward
        if (!cloudPlayer.extradataContains("daily")) {
            return true;
        }

        //Check if the player can get his normal reward again
        long timestamp = (long) cloudPlayer.extradataGet("daily");
        if (timestamp <= System.currentTimeMillis()) {
            return true;
        }

        //End here if the player doesn't have permission for premium reward
        if (!cloudPlayer.hasPermission("lobby.rewardPremium")) {
            return false;
        }

        //Check if the player has never got his premium reward
        if (!cloudPlayer.extradataContains("daily-premium")) {
            return true;
        }

        //Check if the player can get his premium reward again
        timestamp = (long) cloudPlayer.extradataGet("daily-premium");
        if (timestamp <= System.currentTimeMillis()) {
            return true;
        }

        return false;
    }

    /**
     * Give the reward to a specific player
     * @param player The player who get the reward
     */

    public void giveReward(CloudPlayer player, String itemName) {
        int coins = player.hasPermission("lobby.reward.premium") ? 150 : 100;
        int streak = (int) player.extradataGet("dailyStreak");
        long timestamp;

        switch (itemName) {
            case "Belohnung":
                if (!player.extradataContains("daily")) {
                    setValue(player, "daily", coins, streak);
                } else {
                    timestamp = (long) player.extradataGet("daily");

                    if (timestamp <= System.currentTimeMillis()) {
                        setValue(player, "daily", coins, streak);
                    } else {
                        player.sendMessage(prefix + "§cDu hast deine Belohnung schon abgeholt");
                    }
                }
                break;
            case "Premium Belohnung":
                if (!player.extradataContains("daily-premium")) {
                    setValue(player, "daily-premium", coins, streak);
                } else {
                    timestamp = (long) player.extradataGet("daily-premium");
                    if (timestamp <= System.currentTimeMillis()) {
                        setValue(player, "daily-premium", coins, streak);
                    } else {
                        player.sendMessage(prefix + "§cDu hast deine Belohnung schon abgeholt");
                    }
                }
                break;
        }
    }

    private void setValue(CloudPlayer player, String key, int coins, int streak) {
        coins = coins + 50 * streak;
        player.extradataSet(key, System.currentTimeMillis() + getRestDayTime());
        player.addCoins(coins);
        player.sendMessage(prefix + "§7Du hast §6" + coins + " §7Coins bekommen!" + (streak > 0
                ? " " + "Du hast einen Streak von §6" + (streak + 1) + "§7 Tagen!"
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