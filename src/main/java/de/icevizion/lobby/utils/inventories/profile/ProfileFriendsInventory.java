package de.icevizion.lobby.utils.inventories.profile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.events.ClickEvent;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileFriendsItemFactory;
import net.titan.cloudcore.player.ICloudPlayer;
import net.titan.spigot.player.CloudPlayer;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendsInventory extends ProfileInventory {

	//TODO -> Update Inventory on Friend deletion

	private static final int PAGE_SIZE = 36;
	private final LobbyPlugin lobbyPlugin;
	private final ProfileFriendsItemFactory itemFactory;
	private final FriendProfile friendProfile;
	private Map<Integer, Map<Integer, InventoryItem>> pageItems;
	private Map<Integer, Map<Integer, Consumer<ClickEvent>>> pageClickEvents;
	private Map<UUID, ProfileFriendManageInventory> cachedProfiles;
	private List<CloudPlayer> onlineList;
	private List<CloudPlayer> offlineList;

	public ProfileFriendsInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin, cloudPlayer, ProfileSite.FRIEND_LIST, "inventoryFriendsTitle");
		this.lobbyPlugin = lobbyPlugin;

		itemFactory = new ProfileFriendsItemFactory(getTranslator(), cloudPlayer);
		//TODO -> Remove Singleton Pattern
		friendProfile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);

		setDrawOnce(true);
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			super.draw();
			calculateFriends();
		}
	}

	public void calculateFriends() {
		System.out.println("calc friends");
		lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin, () -> {
			List<CloudPlayer> friendList = friendProfile.getFriends();
			if (friendList.isEmpty()) {
				setItem(22, itemFactory.getNoFriendsItem());
				drawItems();
				return;
			}

			cachedProfiles = Maps.newHashMap();
			pageItems = Maps.newHashMap();
			pageClickEvents = Maps.newHashMap();

			onlineList = Lists.newArrayList();
			offlineList = Lists.newArrayList();
			for (CloudPlayer cloudPlayer : friendList) {
				if (cloudPlayer.isOnline()) {
					onlineList.add(cloudPlayer);
				} else {
					offlineList.add(cloudPlayer);
				}
			}

			onlineList.sort(Comparator.comparing(ICloudPlayer::getDisplayName));
			offlineList.sort(
					Comparator.comparingLong(one -> System.currentTimeMillis() - one.getLastLogout()));

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

		if (!(onlineList.size() + offlineList.size() < PAGE_SIZE)) {
			itemMap.put(44, itemFactory.getNextPageItem());
			eventMap.put(44, onPageClick(page, page + 1));
		}

		int currentSlot = 0;
		if (!onlineList.isEmpty()) {
			Iterator<CloudPlayer> onlineIterator = onlineList.iterator();
			while (onlineIterator.hasNext() && currentSlot < PAGE_SIZE) {
				CloudPlayer onlinePlayer = onlineIterator.next();
				if (itemMap.containsKey(currentSlot)) {
					currentSlot++;
				}

				itemMap.put(currentSlot, itemFactory.getOnlineFriendItem(onlinePlayer));
				eventMap.put(currentSlot, onFriendClick(onlinePlayer));
				onlineIterator.remove();
				currentSlot++;
			}
		}

		if (!offlineList.isEmpty()) {
			Iterator<CloudPlayer> offlineIterator = offlineList.iterator();
			while (offlineIterator.hasNext() && currentSlot < PAGE_SIZE) {
				CloudPlayer offlinePlayer = offlineIterator.next();
				if (itemMap.containsKey(currentSlot)) {
					currentSlot++;
				}

				itemMap.put(currentSlot, itemFactory.getOfflineFriendItem(offlinePlayer));
				eventMap.put(currentSlot, onFriendClick(offlinePlayer));
				offlineIterator.remove();
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
			ProfileFriendManageInventory friendManageInventory;
			if (cachedProfiles.containsKey(friendPlayer.getUniqueId())) {
				friendManageInventory = cachedProfiles.get(friendPlayer.getUniqueId());
			} else {
				friendManageInventory = new ProfileFriendManageInventory(lobbyPlugin, getCloudPlayer(),
						friendProfile, this, friendPlayer);
				cachedProfiles.put(friendPlayer.getUniqueId(), friendManageInventory);
			}

			lobbyPlugin.getInventoryService().openPersonalInventory(getCloudPlayer(),
					friendManageInventory);
		};
	}

	private void setItems(int page) {
		Map<Integer, Consumer<ClickEvent>> clickEvents = pageClickEvents.get(page);
		pageItems.get(page).forEach(
				(slot, itemBuilder) -> setItem(slot, itemBuilder, clickEvents.get(slot)));
		drawItems();
	}
}
