package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileInventory extends InventoryBuilder {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileItemFactory profileItemFactory;
	private final CloudPlayer cloudPlayer;
	private final ProfileSite currentSite;
	private ProfileFriendsInventory friendsInventory;
	private ProfileRequestsInventory requestsInventory;
	private ProfileSettingsInventory settingsInventory;

	public ProfileInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer, String title,
	                        ProfileSite currentSite) {
		super(title, 54);
		this.lobbyPlugin = lobbyPlugin;
		this.cloudPlayer = cloudPlayer;
		this.currentSite = currentSite;

		profileItemFactory = new ProfileItemFactory(lobbyPlugin, cloudPlayer);
	}

	@Override
	public void draw() {
		for (int i = 36; i < 45; i++) {
			setBackgroundItem(i, profileItemFactory.getBackgroundItem());
		}

		setBackgroundItem(45, profileItemFactory.getBackgroundItem());

		setItem(ProfileSite.FRIEND_LIST.getIndex(), profileItemFactory.getFriendsItem(),
				event -> lobbyPlugin.getInventoryLoader()
						.openInventory(cloudPlayer, getFriendsInventory()));
		setItem(ProfileSite.FRIEND_REQUESTS.getIndex(), profileItemFactory.getRequestsItem(),
				event -> lobbyPlugin.getInventoryLoader()
						.openInventory(cloudPlayer, getRequestsInventory()));
		setItem(ProfileSite.SETTINGS.getIndex(), profileItemFactory.getSettingsItem(),
				event -> lobbyPlugin.getInventoryLoader()
						.openInventory(cloudPlayer, getSettingsInventory()));

		setBackgroundItem(53, profileItemFactory.getBackgroundItem());

		getClickEvents().remove(currentSite.getIndex());
		profileItemFactory.setCurrentItem(getItem(currentSite.getIndex()));
	}

	public final ProfileItemFactory getProfileItemFactory() {
		return profileItemFactory;
	}

	public CloudPlayer getCloudPlayer() {
		return cloudPlayer;
	}

	public ProfileSite getCurrentSite() {
		return currentSite;
	}

	public final ProfileFriendsInventory getFriendsInventory() {
		if (Objects.isNull(friendsInventory)) {
			friendsInventory = new ProfileFriendsInventory(lobbyPlugin, cloudPlayer);
		}

		return friendsInventory;
	}

	public final ProfileRequestsInventory getRequestsInventory() {
		if (Objects.isNull(requestsInventory)) {
			requestsInventory = new ProfileRequestsInventory(lobbyPlugin, cloudPlayer);
		}

		return requestsInventory;
	}

	public final ProfileSettingsInventory getSettingsInventory() {
		if (Objects.isNull(settingsInventory)) {
			settingsInventory = new ProfileSettingsInventory(lobbyPlugin, cloudPlayer);
		}

		return settingsInventory;
	}

	public enum ProfileSite {
		FRIEND_LIST(47),
		FRIEND_REQUESTS(49),
		SETTINGS(51);

		private final int index;

		ProfileSite(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}
}
