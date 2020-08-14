package de.icevizion.lobby.listener.entity;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class EntityDamageByEntityListener implements Listener {

	private final LobbyPlugin lobbyPlugin;
	private final FriendSystem friendSystem;

	public EntityDamageByEntityListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;

		//TODO -> Remove Singleton Pattern
		friendSystem = FriendSystem.getInstance();
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getDamager();
		ItemStack itemStack = player.getItemInHand();
		if (Objects.isNull(itemStack) || !itemStack.hasItemMeta() || !itemStack.getItemMeta()
				.hasDisplayName()) {
			return;
		}

		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(player);
		ItemMeta itemMeta = itemStack.getItemMeta();
		String itemDisplayName = itemMeta.getDisplayName();
		String itemProfile = lobbyPlugin.getLocales().getString(cloudPlayer, "itemProfileName");
		if (!itemDisplayName.equals(itemProfile)) {
			return;
		}

		Player target = (Player) event.getEntity();
		CloudPlayer targetPlayer = lobbyPlugin.getCloud().getPlayer(target);
		FriendProfile playerProfile = friendSystem.getFriendProfile(cloudPlayer);
		if (playerProfile.getRawRequests().containsKey(targetPlayer.getUuid())) {
			cloudPlayer.dispatchCommand("friend", "accept", targetPlayer.getUuid());
			return;
		}

		cloudPlayer.dispatchCommand("friend", "add", targetPlayer.getUuid());
	}

/*		if(playerProfile.getRawFriends().containsKey(targetPlayer.getUuid())) {
			cloudPlayer.dispatchCommand("friend", "add", targetPlayer.getUuid());
			return;
		}

		FriendProfile targetProfile = friendSystem.getFriendProfile(targetPlayer);
		if(targetProfile.getRawRequests().containsKey(cloudPlayer.getUuid())) {
			cloudPlayer.dispatchCommand("friend", "add", targetPlayer.getUuid());
			return;
		}

		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			CloudPlayer player = Cloud.getInstance().getPlayer((Player) event.getDamager());
			if (player.getPlayer().getItemInHand().getType().equals(Material.SKULL_ITEM)) {
				CloudPlayer clickedPlayer = Cloud.getInstance().getPlayer((Player) event.getEntity());
				FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(player);
				FriendProfile clickedProfile = FriendSystem.getInstance().getFriendProfile(clickedPlayer);
				if (friendProfile.getFriends().contains(clickedPlayer)) return;
				if (clickedProfile.getRawRequests().containsKey(player.getUuid())) return;
				player.dispatchCommand("friend", new String[]{"add", clickedPlayer.getUuid()});
			}
		}
	}*/
}
