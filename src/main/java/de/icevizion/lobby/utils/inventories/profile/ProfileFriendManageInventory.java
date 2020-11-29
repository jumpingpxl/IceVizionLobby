package de.icevizion.lobby.utils.inventories.profile;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.icevizion.aves.inventory.events.ClickEvent;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileFriendManageItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendManageInventory extends ProfileInventory {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileFriendManageItemFactory itemFactory;
	private final ProfileFriendsInventory fallback;
	private final FriendProfile friendProfile;
	private final CloudPlayer friendPlayer;

	public ProfileFriendManageInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer,
	                                    FriendProfile friendProfile,
	                                    ProfileFriendsInventory fallback,
	                                    CloudPlayer friendPlayer) {
		super(lobbyPlugin, cloudPlayer, ProfileSite.FRIEND_LIST, "inventoryFriendManageTitle",
				friendPlayer.getFullDisplayName());
		this.lobbyPlugin = lobbyPlugin;
		this.fallback = fallback;
		this.friendProfile = friendProfile;
		this.friendPlayer = friendPlayer;

		itemFactory = new ProfileFriendManageItemFactory(getTranslator(), cloudPlayer);

		setDrawOnce(true);
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();
			setLayout(this);

			boolean isOnline = friendPlayer.isOnline();

			if (isOnline) {
				setItem(21, itemFactory.getJumpItem(),
						event -> getCloudPlayer().dispatchCommand("friend", "jump",
								friendPlayer.getDisplayName()));

				setItem(23, itemFactory.getInviteItem(),
						event -> getCloudPlayer().dispatchCommand("party", "invite",
								friendPlayer.getDisplayName()));
			}

			setItem(isOnline ? 25 : 23, itemFactory.getRemoveItem(), event -> {
				event.getPlayer().closeInventory();
				ProfileFriendRemoveInventory friendRemoveInventory = new ProfileFriendRemoveInventory(
						lobbyPlugin, getCloudPlayer(), this, friendPlayer);
				lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(), friendRemoveInventory);
			});
		}
	}

	public ProfileFriendsInventory getFallback() {
		return fallback;
	}

	public void setLayout(ProfileInventory profileInventory) {
		for (int i = 0; i < 9; i++) {
			profileInventory.setItem(i, itemFactory.getCancelItem(), onCancelClick());
		}

		profileInventory.setBackgroundItem(10, getProfileItemFactory().getBackgroundItem());

		profileInventory.setItem(18, itemFactory.getFriendItem(friendProfile, friendPlayer));
		profileInventory.setBackgroundItem(19, getProfileItemFactory().getBackgroundItem());

		profileInventory.setBackgroundItem(28, getProfileItemFactory().getBackgroundItem());

		profileInventory.getClickEvents().put(getCurrentSite().getIndex(), onCancelClick());
	}

	private Consumer<ClickEvent> onCancelClick() {
		return event -> lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(),
				fallback);
	}
}
