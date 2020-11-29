package de.icevizion.lobby.listener.network;

import de.cosmiqglow.component.friendsystem.lib.UpdateAction;
import de.cosmiqglow.component.friendsystem.spigot.FriendUpdateEvent;
import de.icevizion.aves.inventory.InventoryBuilder;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventories.profile.ProfileFriendsInventory;
import de.icevizion.lobby.utils.inventories.profile.ProfileRequestsInventory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class FriendUpdateListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public FriendUpdateListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onFriendUpdate(FriendUpdateEvent event) {
		applyUpdate(event.getCloudPlayer(), event.getUpdateAction());
		applyUpdate(event.getFriendPlayer(), event.getUpdateAction());
	}

	private void applyUpdate(CloudPlayer cloudPlayer, UpdateAction updateAction) {
		if (Objects.isNull(cloudPlayer.getPlayer())) {
			return;
		}

		if(updateAction == UpdateAction.ADD || updateAction == UpdateAction.REMOVE) {
			applyFriendUpdate(cloudPlayer);
			return;
		}

		applyRequestUpdate(cloudPlayer);
	}

	private void applyFriendUpdate(CloudPlayer cloudPlayer) {
		Player player = cloudPlayer.getPlayer();
		lobbyPlugin.getLobbyScoreboard().updateFriendsTeam(cloudPlayer);

		Optional<InventoryBuilder> optionalInventoryBuilder = getInventoryBuilder(player);
		if(!optionalInventoryBuilder.isPresent()) {
			return;
		}

		InventoryBuilder inventoryBuilder = optionalInventoryBuilder.get();
		if(!(inventoryBuilder instanceof ProfileFriendsInventory)) {
			return;
		}

		ProfileFriendsInventory friendsInventory = (ProfileFriendsInventory) inventoryBuilder;
		friendsInventory.calculateFriends();
	}

	private void applyRequestUpdate(CloudPlayer cloudPlayer) {
		Player player = cloudPlayer.getPlayer();
		Optional<InventoryBuilder> optionalInventoryBuilder = getInventoryBuilder(player);
		if(!optionalInventoryBuilder.isPresent()) {
			return;
		}

		InventoryBuilder inventoryBuilder = optionalInventoryBuilder.get();
		if(!(inventoryBuilder instanceof ProfileRequestsInventory)) {
			return;
		}

		ProfileRequestsInventory requestsInventory = (ProfileRequestsInventory) inventoryBuilder;
		requestsInventory.calculateRequests();
	}

	private Optional<InventoryBuilder> getInventoryBuilder(Player player) {
		InventoryView inventoryView = player.getOpenInventory();
		if (Objects.isNull(inventoryView) || Objects.isNull(inventoryView.getTopInventory())) {
			return Optional.empty();
		}

		Inventory inventory = inventoryView.getTopInventory();
		if (inventory.getType() != InventoryType.CHEST
				&& !(inventory.getHolder() instanceof InventoryBuilder.Holder)) {
			return Optional.empty();
		}

		InventoryBuilder.Holder holder = (InventoryBuilder.Holder) inventory.getHolder();
		return Optional.of(holder.getInventoryBuilder());
	}
}
