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
	private ProfileFriendsInventory friendsInventory;
	private ProfileRequestsInventory requestsInventory;
	private ProfileSettingsInventory settingsInventory;
	private final int currentSite;

	public ProfileInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer, String title,
	                        int currentSite) {
		super(title, 54);
		this.lobbyPlugin = lobbyPlugin;
		profileItemFactory = new ProfileItemFactory(lobbyPlugin, cloudPlayer);
		this.cloudPlayer = cloudPlayer;
		this.currentSite = currentSite;
	}

	@Override
	public void draw() {
		for (int i = 36; i < 45; i++) {
			setItem(i, profileItemFactory.getBackgroundItem());
		}

		setItem(45, profileItemFactory.getBackgroundItem());

		setItem(47, profileItemFactory.getFriendsItem(), event -> lobbyPlugin.getInventoryLoader()
				.openInventory(cloudPlayer, getFriendsInventory()));
		setItem(49, profileItemFactory.getRequestsItem(), event -> lobbyPlugin.getInventoryLoader()
				.openInventory(cloudPlayer, getRequestsInventory()));
		setItem(51, profileItemFactory.getSettingsItem(), event -> lobbyPlugin.getInventoryLoader()
				.openInventory(cloudPlayer, getSettingsInventory()));

		setItem(53, profileItemFactory.getBackgroundItem());

		getClickEvents().remove(currentSite);
		profileItemFactory.setCurrentItem(getItem(currentSite));
	}

	public final ProfileItemFactory getProfileItemFactory() {
		return profileItemFactory;
	}

	public CloudPlayer getCloudPlayer() {
		return cloudPlayer;
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
}
