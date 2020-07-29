package de.icevizion.lobby.utils.inventories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.DailyRewardItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Sound;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class DailyRewardInventory extends InventoryBuilder {

	private final static String PREMIUM_REWARD_PERMISSION = "lobby.premiumreward";
	private final static String PREMIUM_REWARD_KEY = "daily-premium";
	private final static String NORMAL_REWARD_KEY = "daily";
	private final static String STREAK_KEY = "dailyStreak";
	private final static Integer PREMIUM_REWARD = 150;
	private final static Integer NORMAL_REWARD = 100;
	private static final Long DAY_MILLIS = (long) (60 * 60 * 24 * 1000);
	private final LobbyPlugin lobbyPlugin;
	private final DailyRewardItemFactory itemFactory;

	public DailyRewardInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(cloudPlayer, lobbyPlugin.getLocales().getString(cloudPlayer, "inventoryDailyTitle"), 27);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new DailyRewardItemFactory(lobbyPlugin, cloudPlayer);
	}

	@Override
	public void draw() {
		for (int i = 0; i < 9; i++) {
			setItem(i, itemFactory.getBackgroundItem());
		}

		boolean hasPremiumReward = getCloudPlayer().hasPermission(PREMIUM_REWARD_PERMISSION);
		if (hasPremiumReward) {
			setItem(14, hasClaimed(PREMIUM_REWARD_KEY) ? itemFactory.getClaimedPremiumRewardItem() : itemFactory.getPremiumRewardItem(), event -> {
				if (hasClaimed(PREMIUM_REWARD_KEY)) {
					lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "dailyClaimAlreadyClaimed");
					return;
				}

					claimReward(PREMIUM_REWARD_KEY, PREMIUM_REWARD);
			});
		}

		setItem(hasPremiumReward ? 12 : 13, hasClaimed(NORMAL_REWARD_KEY) ? itemFactory.getRewardItem() :
				itemFactory.getClaimedRewardItem(), event -> {
			if (hasClaimed(NORMAL_REWARD_KEY)) {
				lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "dailyClaimAlreadyClaimed");
				return;
			}

			claimReward(NORMAL_REWARD_KEY, NORMAL_REWARD);
		});

		for (int i = 18; i < 27; i++) {
			setItem(i, itemFactory.getBackgroundItem());
		}
	}

	private boolean hasClaimed(String key) {
		return getCloudPlayer().extradataContains(key) && (long) getCloudPlayer().extradataGet(key)
				> System.currentTimeMillis();
	}

	private void claimReward(String key, int claimedCoins) {
		int currentStreak = calculateCurrentStreak();
		claimedCoins += 50 * currentStreak;
		getCloudPlayer().addCoins(claimedCoins);
		getCloudPlayer().extradataSet(key, calculateRemainingTime() + System.currentTimeMillis());
		getCloudPlayer().getPlayer().playSound(getCloudPlayer().getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
		lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "dailyClaim", claimedCoins, currentStreak);
	}

	private long calculateRemainingTime() {
		ZoneId zoneId = ZoneId.of("Europe/Berlin");
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		LocalDate tomorrow = now.toLocalDate().plusDays(1);
		ZonedDateTime tomorrowStart = tomorrow.atStartOfDay(zoneId);
		return Duration.between(now, tomorrowStart).toMillis();
	}

	private int calculateCurrentStreak() {
		int currentStreak = getCloudPlayer().extradataContains(STREAK_KEY) ? (int)getCloudPlayer().extradataGet(STREAK_KEY) : 0;
		int normalTimestamp = getCloudPlayer().extradataContains(NORMAL_REWARD_KEY) ? (int)getCloudPlayer().extradataGet(NORMAL_REWARD_KEY) : 0;
		int premiumTimestamp = getCloudPlayer().extradataContains(PREMIUM_REWARD_KEY) ? (int)getCloudPlayer().extradataGet(PREMIUM_REWARD_KEY) : 0;
		if(normalTimestamp > System.currentTimeMillis() || premiumTimestamp > System.currentTimeMillis()) {
			return currentStreak;
		}

		if(System.currentTimeMillis() - normalTimestamp > DAY_MILLIS) {
			getCloudPlayer().offlineExtradataRemove(STREAK_KEY);
			getCloudPlayer().extradataSet(STREAK_KEY, 0);
			return 0;
		}

		currentStreak++;
		getCloudPlayer().extradataSet(STREAK_KEY, currentStreak);
		return currentStreak;
	}
}
