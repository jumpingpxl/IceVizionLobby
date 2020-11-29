package de.icevizion.lobby.utils.inventories.profile;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.icevizion.aves.inventory.events.ClickEvent;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileRequestManageItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileRequestManageInventory extends ProfileInventory {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileRequestManageItemFactory itemFactory;
	private final ProfileRequestsInventory fallback;
	private final FriendProfile friendProfile;
	private final CloudPlayer requestPlayer;

	public ProfileRequestManageInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer,
	                                     FriendProfile friendProfile,
	                                     ProfileRequestsInventory fallback,
	                                     CloudPlayer requestPlayer) {
		super(lobbyPlugin, cloudPlayer, ProfileSite.FRIEND_REQUESTS, "inventoryRequestManageTitle",
				requestPlayer.getFullDisplayName());
		this.lobbyPlugin = lobbyPlugin;
		this.fallback = fallback;
		this.friendProfile = friendProfile;
		this.requestPlayer = requestPlayer;

		itemFactory = new ProfileRequestManageItemFactory(getTranslator(), cloudPlayer, requestPlayer);

		setDrawOnce(true);
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();
			for (int i = 0; i < 9; i++) {
				setItem(i, itemFactory.getCancelItem(), onCancelClick());
			}

			setBackgroundItem(10, getProfileItemFactory().getBackgroundItem());

			setItem(18, itemFactory.getRequesterItem(friendProfile));
			setBackgroundItem(19, getProfileItemFactory().getBackgroundItem());

			setBackgroundItem(28, getProfileItemFactory().getBackgroundItem());

			setItem(21, itemFactory.getAcceptItem(), onFriendActionClick("accept"));
			setItem(23, itemFactory.getReminderItem());
			setItem(25, itemFactory.getDenyItem(), onFriendActionClick("deny"));

			getClickEvents().put(getCurrentSite().getIndex(), onCancelClick());
		}
	}

	private Consumer<ClickEvent> onFriendActionClick(String command) {
		return event -> {
			event.getPlayer().closeInventory();
			lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(), fallback);
			getCloudPlayer().dispatchCommand("friend", command, requestPlayer.getDisplayName());
		};
	}

	private Consumer<ClickEvent> onCancelClick() {
		return event -> lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(),
				fallback);
	}
}
