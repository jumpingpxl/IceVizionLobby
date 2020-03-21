package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.CustomPlayerHeadBuilder;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.lobby.Lobby;
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

        List<CloudPlayer> sortedFriends = sortPlayers(cloudPlayer);
        for (int i = 0; i < 35 && i < sortedFriends.size(); i++) {
            CloudPlayer player = sortedFriends.get(i);
            if (player.isOnline()) {
                inventory.setItem(i,new CustomPlayerHeadBuilder()
                        .setSkullType(CustomPlayerHeadBuilder.SkullType.PLAYER)
                        .setSkinOverValues(player.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + player.getSpigot().getDisplayName())
                        .setDisplayName(player.getFullUsername()).build());
            } else {
                inventory.setItem(i, new CustomPlayerHeadBuilder().setSkullType(CustomPlayerHeadBuilder.SkullType.SKELETON)
                        .setDisplayName(player.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + Lobby.DATE_FORMAT.format(player.getLastLogout())).build());
            }
        }
        cloudPlayer.getPlayer().updateInventory();
    }

    public List<CloudPlayer> sortPlayers(CloudPlayer cloudPlayer) {
        List<CloudPlayer> sortedFriends = FriendSystem.getInstance().getFriendProfile(cloudPlayer).getFriends();
        if (sortedFriends.size() > 1) {
            sortedFriends.sort((cp1, cp2) -> {
                int online1 = cp1.isOnline() ? 1 : 0;
                int online2 = cp2.isOnline() ? 1 : 0;
                return online2 - online1;
            });
        }
        return sortedFriends;
    }
 }