package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.SkullBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class FriendUtil {

    /**
     * Updates the friend skulls in the given inventory.
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
                inventory.setItem(i,new SkullBuilder()
                        .setSkinOverValues(player.getSkinValue(), "")
                        .addLore(player.getSpigot() == null ? "§cFehler" :
                                "§7Befindet sich auf: §e" + player.getSpigot().getDisplayName())
                        .setDisplayName(player.getFullUsername()).build());
            } else {
                inventory.setItem(i, new SkullBuilder(SkullBuilder.SkullType.SKELETON)
                        .setDisplayName(player.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + Lobby.DATE_FORMAT.format(player.getLastLogout())).build());
            }
        }
        cloudPlayer.getPlayer().updateInventory();
    }

    /**
     * Sort a player's list of friends by the players who are online.
     * @param cloudPlayer The given player
     * @return The sorted list
     */

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