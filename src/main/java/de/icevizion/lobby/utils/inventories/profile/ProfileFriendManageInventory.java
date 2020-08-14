package de.icevizion.lobby.utils.inventories.profile;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileFriendManageItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;

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
		super(lobbyPlugin, cloudPlayer, lobbyPlugin.getLocales()
						.getString(cloudPlayer, "inventoryFriendManageTitle",
								friendPlayer.getFullDisplayName()),
				ProfileSite.FRIEND_LIST);
		this.lobbyPlugin = lobbyPlugin;
		this.fallback = fallback;
		this.friendProfile = friendProfile;
		this.friendPlayer = friendPlayer;

		itemFactory = new ProfileFriendManageItemFactory(lobbyPlugin, cloudPlayer);

		setStaticDraw(true);
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();
			setLayout(this);

			boolean isOnline = friendPlayer.isOnline();

			if(isOnline) {
				setItem(21, itemFactory.getJumpItem(),
						event -> getCloudPlayer().dispatchCommand("friend", "jump",
								friendPlayer.getDisplayName()));

				setItem(23, itemFactory.getInviteItem(),
						event -> getCloudPlayer().dispatchCommand("party", "invite",
								friendPlayer.getDisplayName()));
			}

			setItem(isOnline ? 25 : 23, itemFactory.getRemoveItem(), event -> {
				event.getWhoClicked().closeInventory();
				ProfileFriendRemoveInventory friendRemoveInventory = new ProfileFriendRemoveInventory(
						lobbyPlugin, getCloudPlayer(), this, friendPlayer);
				lobbyPlugin.getInventoryLoader().openInventory(getCloudPlayer(), friendRemoveInventory);
			});
		}
	}

	public ProfileFriendsInventory getFallback() {
		return fallback;
	}

	public void setLayout(InventoryBuilder inventoryBuilder) {
		for (int i = 0; i < 9; i++) {
			inventoryBuilder.setItem(i, itemFactory.getCancelItem(), onCancelClick());
		}

		inventoryBuilder.setBackgroundItem(10, getProfileItemFactory().getBackgroundItem());

		inventoryBuilder.setItem(18, itemFactory.getFriendItem(friendProfile, friendPlayer));
		inventoryBuilder.setBackgroundItem(19, getProfileItemFactory().getBackgroundItem());

		inventoryBuilder.setBackgroundItem(28, getProfileItemFactory().getBackgroundItem());

		inventoryBuilder.getClickEvents().put(getCurrentSite().getIndex(), onCancelClick());
	}

	private Consumer<InventoryClickEvent> onCancelClick() {
		return event -> lobbyPlugin.getInventoryLoader().openInventory(getCloudPlayer(), fallback);
	}
}
