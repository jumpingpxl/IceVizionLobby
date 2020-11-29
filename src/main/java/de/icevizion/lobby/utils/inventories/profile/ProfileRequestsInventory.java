package de.icevizion.lobby.utils.inventories.profile;

import com.google.common.collect.Maps;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.events.ClickEvent;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileRequestsItemFactory;
import net.titan.spigot.player.CloudPlayer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileRequestsInventory extends ProfileInventory {

	//TODO -> Update Inventory on Denied/Accepted Request

	private static final int PAGE_SIZE = 36;
	private final LobbyPlugin lobbyPlugin;
	private final ProfileRequestsItemFactory itemFactory;
	private final FriendProfile friendProfile;
	private Map<Integer, Map<Integer, InventoryItem>> pageItems;
	private Map<Integer, Map<Integer, Consumer<ClickEvent>>> pageClickEvents;
	private Map<UUID, ProfileRequestManageInventory> cachedProfiles;
	private List<CloudPlayer> requestList;

	public ProfileRequestsInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin, cloudPlayer, ProfileSite.FRIEND_REQUESTS, "inventoryRequestsTitle");
		this.lobbyPlugin = lobbyPlugin;

		itemFactory = new ProfileRequestsItemFactory(getTranslator(), cloudPlayer);
		friendProfile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
		cachedProfiles = Maps.newHashMap();
		pageItems = Maps.newHashMap();
		pageClickEvents = Maps.newHashMap();

		setDrawOnce(true);
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();
			calculateRequests();
		}
	}

	public void calculateRequests() {
		System.out.println("calc requests");
		lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin, () -> {
			List<CloudPlayer> friendList = friendProfile.getRequests();
			if (friendList.isEmpty()) {
				setItem(22, itemFactory.getNoRequestsItem());
				drawItems();
				return;
			}

			cachedProfiles = Maps.newHashMap();
			pageItems = Maps.newHashMap();
			pageClickEvents = Maps.newHashMap();

			this.requestList = friendList;

			calculatePage(0);
			setItems(0);
		});
	}

	private void calculatePage(int page) {
		Map<Integer, InventoryItem> itemMap = Maps.newHashMap();
		Map<Integer, Consumer<ClickEvent>> eventMap = Maps.newHashMap();
		if (page != 0) {
			itemMap.put(36, itemFactory.getPreviousPageItem());
			eventMap.put(36, onPageClick(page, page - 1));
		}

		if (!(requestList.size() < PAGE_SIZE)) {
			itemMap.put(44, itemFactory.getNextPageItem());
			eventMap.put(44, onPageClick(page, page + 1));
		}

		int currentSlot = 0;
		if (!requestList.isEmpty()) {
			Iterator<CloudPlayer> requestsIterator = requestList.iterator();
			while (requestsIterator.hasNext() && currentSlot < PAGE_SIZE) {
				CloudPlayer requestPlayer = requestsIterator.next();
				if (itemMap.containsKey(currentSlot)) {
					currentSlot++;
				}

				itemMap.put(currentSlot, itemFactory.getRequestItem(friendProfile, requestPlayer));
				eventMap.put(currentSlot, onFriendClick(requestPlayer));
				requestsIterator.remove();
				currentSlot++;
			}
		}

		pageItems.put(page, itemMap);
		pageClickEvents.put(page, eventMap);
	}

	private Consumer<ClickEvent> onPageClick(int currentPage, int page) {
		return event -> {
			if (!pageItems.containsKey(page)) {
				calculatePage(page);
			}

			pageItems.get(currentPage).forEach((slot, itemBuilder) -> {
				getItems().remove(slot);
				getClickEvents().remove(slot);
			});

			setItems(page);
		};
	}

	private Consumer<ClickEvent> onFriendClick(CloudPlayer friendPlayer) {
		return event -> {
			ProfileRequestManageInventory requestManageInventory;
			if (cachedProfiles.containsKey(friendPlayer.getUniqueId())) {
				requestManageInventory = cachedProfiles.get(friendPlayer.getUniqueId());
			} else {
				requestManageInventory = new ProfileRequestManageInventory(lobbyPlugin, getCloudPlayer(),
						friendProfile, this, friendPlayer);
				cachedProfiles.put(friendPlayer.getUniqueId(), requestManageInventory);
			}

			lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(), requestManageInventory);
		};
	}

	private void setItems(int page) {
		Map<Integer, Consumer<ClickEvent>> clickEvents = pageClickEvents.get(page);
		pageItems.get(page).forEach(
				(slot, itemBuilder) -> setItem(slot, itemBuilder, clickEvents.get(slot)));
		drawItems();
	}
}