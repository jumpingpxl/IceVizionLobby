package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.CustomPlayerHeadBuilder;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class FriendUtil {

    /**
     * Updates the friend skulls in the given inventory
     * @param cloudPlayer The player
     * @param inventory The inventory to update
     */

    public void updateInventory(CloudPlayer cloudPlayer, Map<Integer, ItemStack> layout, Inventory inventory) {
        inventory.clear();
        for (Map.Entry<Integer, ItemStack> entry : layout.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        List<CloudPlayer> sortedFriends = FriendSystem.getInstance().getFriendProfile(cloudPlayer).getFriends();
        sortedFriends.sort((cp1, cp2) -> {
            if (cp1.isOnline() && cp2.isOnline())
                return 0;
            if (cp1.isOnline())
                return -1;
            return 1;
        });

        for (int i = 0; i < 35 && i < sortedFriends.size(); i++) {
            CloudPlayer player = sortedFriends.get(i);
            if (player.isOnline()) {
                inventory.setItem(i,new CustomPlayerHeadBuilder()
                        .setSkinOverValues(player.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + player.getSpigot().getDisplayName())
                        .setDisplayName(player.getFullUsername()).build());
            } else {
                inventory.setItem(i,new ItemBuilder(Material.SKELETON_SKULL)
                        .setDisplayName(player.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + Lobby.DATE_FORMAT.format(player.getLastLogout())).build());
            }
        }
        cloudPlayer.getPlayer().updateInventory();
    }

    public void updateFriendInventory(CloudPlayer networkPlayer, Map<Integer, ItemStack> layout, Inventory inventory) {
        inventory.clear();
        for (Map.Entry<Integer, ItemStack> entry : layout.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        for (CloudPlayer player : Cloud.getInstance().getCurrentOnlinePlayers()) {
            FriendProfile profile = FriendSystem.getInstance().getFriendProfile(player);

            if (profile.getRawFriends().containsKey(networkPlayer.getUuid())) {
                List<CloudPlayer> sortedFriends = FriendSystem.getInstance().getFriendProfile(player).getFriends();
                sortedFriends.sort((cp1, cp2) -> {
                    if (cp1.isOnline() && cp2.isOnline())
                        return 0;
                    if (cp1.isOnline())
                        return -1;
                    return 1;
                });
                for (int i = 0; i < 35 && i < sortedFriends.size(); i++) {
                    CloudPlayer friend = sortedFriends.get(i);
                    if (player.isOnline()) {
                        inventory.setItem(i,new CustomPlayerHeadBuilder()
                                .setSkinOverValues(friend.getSkinValue(), "")
                                .addLore("§7Befindet sich auf: §e" + friend.getSpigot().getDisplayName())
                                .setDisplayName(friend.getFullUsername()).build());
                    } else {
                        inventory.setItem(i,new ItemBuilder(Material.SKELETON_SKULL)
                                .setDisplayName(friend.getFullUsername())
                                .addLore("§7Zuletzt Online: §e" + Lobby.DATE_FORMAT.format(friend.getLastLogout())).build());
                    }
                }
                player.getPlayer().updateInventory();
            }
        }
    }
}