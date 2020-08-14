package de.icevizion.lobby.utils.inventories.profile;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileRequestManageItemFactory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;

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
		super(lobbyPlugin, cloudPlayer,
				lobbyPlugin.getLocales().getString(cloudPlayer, "inventoryRequestManageTitle",
						requestPlayer.getFullDisplayName()),
				ProfileInventory.ProfileSite.FRIEND_REQUESTS);
		this.lobbyPlugin = lobbyPlugin;
		this.fallback = fallback;
		this.friendProfile = friendProfile;
		this.requestPlayer = requestPlayer;

		itemFactory = new ProfileRequestManageItemFactory(lobbyPlugin, cloudPlayer, requestPlayer);

		setStaticDraw(true);
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

			getClickEvents().put(getCurrentSite().getIndex(), onCancelClick());

			setItem(21, itemFactory.getAcceptItem(), event -> {
				event.getWhoClicked().closeInventory();
				fallback.calculateRequests();
				lobbyPlugin.getInventoryLoader().openInventory(getCloudPlayer(), fallback);
				getCloudPlayer().dispatchCommand("friend", "accept", requestPlayer.getDisplayName());
			});

			setItem(23, itemFactory.getReminderItem());

			setItem(25, itemFactory.getDenyServer(), event -> {
				event.getWhoClicked().closeInventory();
				fallback.calculateRequests();
				lobbyPlugin.getInventoryLoader().openInventory(getCloudPlayer(), fallback);
				getCloudPlayer().dispatchCommand("friend", "deny", requestPlayer.getDisplayName());
			});
		}
	}

	private Consumer<InventoryClickEvent> onCancelClick() {
		return event -> lobbyPlugin.getInventoryLoader().openInventory(getCloudPlayer(), fallback);
	}
}
