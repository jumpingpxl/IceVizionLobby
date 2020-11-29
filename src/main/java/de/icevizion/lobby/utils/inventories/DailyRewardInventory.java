package de.icevizion.lobby.utils.inventories;

import de.icevizion.aves.inventory.InventoryRows;
import de.icevizion.aves.inventory.PersonalInventory;
import de.icevizion.lobby.LobbyPlugin;
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

public class DailyRewardInventory extends PersonalInventory {

	private final static String PREMIUM_REWARD_PERMISSION = "lobby.premiumreward";
	private final static String PREMIUM_REWARD_KEY = "daily-premium";
	private final static String NORMAL_REWARD_KEY = "daily";
	private final static String STREAK_KEY = "dailyStreak";
	private final static int PREMIUM_REWARD = 150;
	private final static int NORMAL_REWARD = 100;
	private static final long DAY_MILLIS = 60 * 60 * 24 * 1000;
	private final LobbyPlugin lobbyPlugin;
	private final DailyRewardItemFactory itemFactory;

	public DailyRewardInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin.getLocales(), cloudPlayer, InventoryRows.THREE, "inventoryDailyTitle");
		this.lobbyPlugin = lobbyPlugin;

		itemFactory = new DailyRewardItemFactory(getTranslator(), cloudPlayer);
	}

	@Override
	public void draw() {
		System.out.println("DAILY DRAW");
		if (isFirstDraw()) {
			System.out.println("DAILY DRAW FIRST");
			setBackgroundItems(0, 8, itemFactory.getBackgroundItem());

			setBackgroundItems(18, 26, itemFactory.getBackgroundItem());
		}

		boolean hasPremiumReward = getCloudPlayer().hasPermission(PREMIUM_REWARD_PERMISSION);
		if (hasPremiumReward) {
			setItem(14, hasClaimed(PREMIUM_REWARD_KEY) ? itemFactory.getClaimedPremiumRewardItem()
					: itemFactory.getPremiumRewardItem(), event -> {
				if (hasClaimed(PREMIUM_REWARD_KEY)) {
					lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "dailyClaimAlreadyClaimed");
					return;
				}

				claimReward(PREMIUM_REWARD_KEY, PREMIUM_REWARD);
				buildInventory();
			});
		}

		setItem(hasPremiumReward ? 12 : 13,
				hasClaimed(NORMAL_REWARD_KEY) ? itemFactory.getClaimedRewardItem()
						: itemFactory.getRewardItem(), event -> {
					if (hasClaimed(NORMAL_REWARD_KEY)) {
						lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "dailyClaimAlreadyClaimed");
						return;
					}

					claimReward(NORMAL_REWARD_KEY, NORMAL_REWARD);
					buildInventory();
				});
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
		getCloudPlayer().getPlayer().playSound(getCloudPlayer().getPlayer().getLocation(),
				Sound.LEVEL_UP, 1F, 1F);
		lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "dailyClaim", claimedCoins,
				currentStreak);
	}

	private long calculateRemainingTime() {
		ZoneId zoneId = ZoneId.of("Europe/Berlin");
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		LocalDate tomorrow = now.toLocalDate().plusDays(1);
		ZonedDateTime tomorrowStart = tomorrow.atStartOfDay(zoneId);
		return Duration.between(now, tomorrowStart).toMillis();
	}

	private int calculateCurrentStreak() {
		int currentStreak = getCloudPlayer().extradataContains(STREAK_KEY)
				? (int) getCloudPlayer().extradataGet(STREAK_KEY) : 0;
		long normalTimestamp = getCloudPlayer().extradataContains(NORMAL_REWARD_KEY)
				? (long) getCloudPlayer().extradataGet(NORMAL_REWARD_KEY) : 0;
		long premiumTimestamp = getCloudPlayer().extradataContains(PREMIUM_REWARD_KEY)
				? (long) getCloudPlayer().extradataGet(PREMIUM_REWARD_KEY) : 0;
		if (normalTimestamp > System.currentTimeMillis()
				|| premiumTimestamp > System.currentTimeMillis()) {
			return currentStreak;
		}

		if (System.currentTimeMillis() - normalTimestamp > DAY_MILLIS) {
			getCloudPlayer().offlineExtradataRemove(STREAK_KEY);
			getCloudPlayer().extradataSet(STREAK_KEY, 0);
			return 0;
		}

		currentStreak++;
		getCloudPlayer().extradataSet(STREAK_KEY, currentStreak);
		return currentStreak;
	}
}
