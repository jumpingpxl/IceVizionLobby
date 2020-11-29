package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.aves.inventory.events.ClickEvent;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Setting;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileSettingsItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileSettingsInventory extends ProfileInventory {

	private static final int[][] CATEGORY_POSITIONS = {{}, {13}, {11, 15}, {11, 13, 15},
			{10, 12, 14, 16}, {10, 11, 13, 15, 16}, {10, 11, 12, 14, 15, 16},
			{10, 11, 12, 13, 14, 15, 16}};
	private static final int[][] SETTING_POSITIONS = {{}, {31}, {30, 32}, {30, 31, 32},
			{29, 30, 32, 33}, {29, 30, 31, 32, 33}};

	private final ProfileSettingsItemFactory itemFactory;
	private Setting currentSetting;
	private int currentSlot;
	private boolean cooldown;

	public ProfileSettingsInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin, cloudPlayer, ProfileSite.SETTINGS, "inventorySettingsTitle");

		itemFactory = new ProfileSettingsItemFactory(getTranslator(), cloudPlayer);
		currentSlot = 0;
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();

			for (int i = 0; i < 10; i++) {
				setBackgroundItem(i, getProfileItemFactory().getBackgroundItem());
			}

			for (int i = 17; i < 27; i++) {
				setBackgroundItem(i, getProfileItemFactory().getBackgroundItem());
			}

			setBackgroundItem(27, itemFactory.getSecondaryBackgroundItem());
			setBackgroundItem(28, getProfileItemFactory().getBackgroundItem());
			setBackgroundItem(34, getProfileItemFactory().getBackgroundItem());
			setBackgroundItem(35, itemFactory.getSecondaryBackgroundItem());
		}

		for (int i : SETTING_POSITIONS[5]) {
			removeItem(i);
		}
		setSettingItems();
	}

	private Consumer<ClickEvent> onCategoryClick(Setting setting) {
		return event -> {
			currentSetting = setting;
			currentSlot = event.getClickedSlot();
			buildInventory();
		};
	}

	private Consumer<ClickEvent> onSettingClick(Setting setting, int i) {
		return event -> {
			if (cooldown) {
				getTranslator().sendMessage(getCloudPlayer(), "settingCooldown");
				return;
			}

			cooldown = true;
			getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> cooldown = false,
					20L);
			getCloudPlayer().setSetting(setting.getId(), i);
			if (setting == Setting.PLAYER_VISIBILITY) {
				getPlugin().getVisibilityTool().changeVisibility(getCloudPlayer(), i);
			}

			buildInventory();
		};
	}

	private void setSettingItems() {
		Setting[] settings = Setting.values();
		for (int i = 0; i < settings.length; i++) {
			setItem(CATEGORY_POSITIONS[settings.length][i], itemFactory.getCategoryItem(settings[i]),
					onCategoryClick(settings[i]));
		}

		if (Objects.isNull(currentSetting)) {
			setItem(SETTING_POSITIONS[1][0], itemFactory.getNoneSelectedItem());
			return;
		}

		getClickEvents().remove(currentSlot);
		itemFactory.setSelectedCategoryItem(getItem(currentSlot));

		setCurrentSettingItems(currentSetting);
	}

	private void setCurrentSettingItems(Setting setting) {
		short[] colors = setting.getColors();
		int currentValue = getCloudPlayer().getSetting(setting.getId());
		for (int i = 0; i < colors.length; i++) {
			if (currentValue == i) {
				setItem(SETTING_POSITIONS[colors.length][i],
						itemFactory.getSelectedSettingItem(setting, i));
			} else {
				setItem(SETTING_POSITIONS[colors.length][i], itemFactory.getSettingItem(setting, i),
						onSettingClick(setting, i));
			}
		}
	}
}
