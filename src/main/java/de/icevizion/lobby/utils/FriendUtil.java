package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.CustomPlayerHeadBuilder;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class FriendUtil {

    /**
     * Updates the friend skulls in the given inventory
     * @param cloudPlayer The player
     * @param inventory The inventory to update
     */

    public void updateInventory(CloudPlayer cloudPlayer, Inventory inventory) {
        for (int i = 0; i < 35 && inventory.getItem(i) != null; i++) {
                inventory.remove(inventory.getItem(i));
        }

        List<CloudPlayer> sortedFriends = FriendSystem.getInstance().getFriendProfile(cloudPlayer).getFriends();
        sortedFriends.sort((cp1, cp2) -> {
            if (cp1.isOnline() && cp2.isOnline())
                return 0;
            if (cp1.isOnline())
                return -1;
            return 1;
        });

        for (CloudPlayer player : sortedFriends) {
            if (player.isOnline()) {
                inventory.addItem(new CustomPlayerHeadBuilder()
                        .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + player.getSpigot().getDisplayName())
                        .setDisplayName(cloudPlayer.getFullUsername()).build());
            } else {
                inventory.addItem(new ItemBuilder(Material.SKELETON_SKULL)
                        .setDisplayName(player.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + Lobby.DATE_FORMAT.format(player.getLastLogout())).build());
            }
        }
        cloudPlayer.getPlayer().updateInventory();
    }
}