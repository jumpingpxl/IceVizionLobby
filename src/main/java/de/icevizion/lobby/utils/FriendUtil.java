package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.SkullBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class FriendUtil {

    /**
     * Updates the friend skulls in the given inventory.
     * @param cloudPlayer The player
     * @param inventory The inventory to update
     */

    public void updateInventory(CloudPlayer cloudPlayer, Inventory inventory) {
        clearFriendSlots(inventory);
        List<CloudPlayer> sortedFriends = sortPlayers(cloudPlayer);
        for (int i = 0; i < 36 && i < sortedFriends.size(); i++) {
            CloudPlayer player = sortedFriends.get(i);
            if (player.isOnline()) {
                inventory.setItem(i, new SkullBuilder()
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
     * Removes a part of the itemStacks in a inventory.
     * @param inventory The inventory from a player
     */

    private void clearFriendSlots(Inventory inventory) {
        int i = 0;
        while (i < 36 && inventory.getItem(i).getType() != Material.AIR) {
            inventory.remove(inventory.getItem(i));
            i++;
        }
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