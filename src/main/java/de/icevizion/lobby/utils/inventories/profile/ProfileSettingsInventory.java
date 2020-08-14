package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Setting;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileSettingsItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;

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

	private final LobbyPlugin lobbyPlugin;
	private final ProfileSettingsItemFactory itemFactory;
	private Setting currentSetting;
	private int currentSlot;
	private boolean cooldown;

	public ProfileSettingsInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin, cloudPlayer,
				lobbyPlugin.getLocales().getString(cloudPlayer, "inventorySettingsTitle"),
				ProfileSite.SETTINGS);
		this.lobbyPlugin = lobbyPlugin;

		itemFactory = new ProfileSettingsItemFactory(lobbyPlugin, cloudPlayer);
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
			setItem(i, null);
		}
		setSettingItems();
	}

	private Consumer<InventoryClickEvent> onCategoryClick(Setting setting) {
		return event -> {
			currentSetting = setting;
			currentSlot = event.getRawSlot();
			buildInventory();
		};
	}

	private Consumer<InventoryClickEvent> onSettingClick(Setting setting, int i) {
		return event -> {
			if (cooldown) {
				lobbyPlugin.getLocales().sendMessage(getCloudPlayer(), "settingCooldown");
				return;
			}

			cooldown = true;
			lobbyPlugin.getServer().getScheduler().runTaskLater(lobbyPlugin, () -> cooldown = false,
					20L);
			getCloudPlayer().setSetting(setting.getId(), i);
			if (setting == Setting.PLAYER_VISIBILITY) {
				lobbyPlugin.getVisibilityTool().changeVisibility(getCloudPlayer(), i);
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
