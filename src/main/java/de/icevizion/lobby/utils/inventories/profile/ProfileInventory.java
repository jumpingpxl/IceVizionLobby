package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.aves.inventory.InventoryRows;
import de.icevizion.aves.inventory.PersonalInventory;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileInventory extends PersonalInventory {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileItemFactory profileItemFactory;
	private final ProfileSite currentSite;
	private ProfileFriendsInventory friendsInventory;
	private ProfileRequestsInventory requestsInventory;
	private ProfileSettingsInventory settingsInventory;

	public ProfileInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer,
	                        ProfileSite currentSite, String key, Object... arguments) {
		super(lobbyPlugin.getLocales(), cloudPlayer, InventoryRows.SIX, key, arguments);
		this.lobbyPlugin = lobbyPlugin;
		this.currentSite = currentSite;

		profileItemFactory = new ProfileItemFactory(getTranslator(), cloudPlayer);
	}

	@Override
	public void draw() {
		setBackgroundItems(36, 44, profileItemFactory.getBackgroundItem());

		setBackgroundItem(45, profileItemFactory.getBackgroundItem());

		setItem(ProfileSite.FRIEND_LIST.getIndex(), profileItemFactory.getFriendsItem(),
				event -> lobbyPlugin.getInventoryService()
						.openPersonalInventory(getCloudPlayer(), getFriendsInventory()));
		setItem(ProfileSite.FRIEND_REQUESTS.getIndex(), profileItemFactory.getRequestsItem(),
				event -> lobbyPlugin.getInventoryService()
						.openPersonalInventory(getCloudPlayer(), getRequestsInventory()));
		setItem(ProfileSite.SETTINGS.getIndex(), profileItemFactory.getSettingsItem(),
				event -> lobbyPlugin.getInventoryService()
						.openPersonalInventory(getCloudPlayer(), getSettingsInventory()));

		setBackgroundItem(53, profileItemFactory.getBackgroundItem());

		getClickEvents().remove(currentSite.getIndex());
		profileItemFactory.setCurrentItem(getItem(currentSite.getIndex()));
	}

	public final ProfileItemFactory getProfileItemFactory() {
		return profileItemFactory;
	}

	public ProfileSite getCurrentSite() {
		return currentSite;
	}

	public final ProfileFriendsInventory getFriendsInventory() {
		if (Objects.isNull(friendsInventory)) {
			friendsInventory = new ProfileFriendsInventory(lobbyPlugin, getCloudPlayer());
		}

		return friendsInventory;
	}

	public final ProfileRequestsInventory getRequestsInventory() {
		if (Objects.isNull(requestsInventory)) {
			requestsInventory = new ProfileRequestsInventory(lobbyPlugin, getCloudPlayer());
		}

		return requestsInventory;
	}

	public final ProfileSettingsInventory getSettingsInventory() {
		if (Objects.isNull(settingsInventory)) {
			settingsInventory = new ProfileSettingsInventory(lobbyPlugin, getCloudPlayer());
		}

		return settingsInventory;
	}

	protected final LobbyPlugin getPlugin() {
		return lobbyPlugin;
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
