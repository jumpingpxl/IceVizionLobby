package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileFriendRemoveItemFactory;
import net.titan.spigot.player.CloudPlayer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendRemoveInventory extends ProfileInventory {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileFriendRemoveItemFactory itemFactory;
	private final ProfileFriendManageInventory fallback;
	private final CloudPlayer friendPlayer;

	public ProfileFriendRemoveInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer,
	                                    ProfileFriendManageInventory fallback,
	                                    CloudPlayer friendPlayer) {
		super(lobbyPlugin, cloudPlayer, ProfileSite.FRIEND_LIST, "inventoryFriendManageTitle",
				friendPlayer.getFullDisplayName());
		this.lobbyPlugin = lobbyPlugin;
		this.fallback = fallback;
		this.friendPlayer = friendPlayer;

		itemFactory = new ProfileFriendRemoveItemFactory(getTranslator(), cloudPlayer, friendPlayer);
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();
			fallback.setLayout(this);

			setItem(21, itemFactory.getConfirmItem(), event -> {
				event.getPlayer().closeInventory();
				lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(), fallback.getFallback());
				getCloudPlayer().dispatchCommand("friend", "remove", friendPlayer.getDisplayName());
			});

			setItem(23, itemFactory.getReminderItem());

			setItem(25, itemFactory.getAbortItem(), event -> {
				event.getPlayer().closeInventory();
				lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(), fallback);
			});
		}
	}
}
