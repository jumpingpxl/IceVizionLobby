package de.icevizion.lobby.utils;

import de.icevizion.aves.item.CustomPlayerHeadBuilder;
import de.icevizion.aves.item.ItemBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemUtil {

    private static final ItemStack PANE = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).
            setDisplayName("§0").build();

    private final ItemStack teleporter, hider, nick, lobby;
    private final Map<Integer, ItemStack> teleporterLayout;
    private final Map<Integer, ItemStack> settingsLayout;
    private final Map<Integer, ItemStack> friendLayout;
    private final Map<Integer, ItemStack> friendRequests;
    private final Map<Integer, ItemStack> friendActionLayout;
    private final Map<Integer, ItemStack> friendSubLayout;

    public ItemUtil() {
        this.teleporter = new ItemBuilder(Material.NETHER_STAR).setDisplayName("§bMinispiele").build();
        this.hider = new ItemBuilder(Material.BLAZE_ROD).setDisplayName("§aSpieler Sichtbarkeit").build();
        this.nick = new ItemBuilder(Material.NAME_TAG).setDisplayName("§5Nick").build();
        this.lobby = new ItemBuilder(Material.CLOCK).setDisplayName("§aLobby wechseln").build();
        this.teleporterLayout = loadTeleporterLayout();
        this.settingsLayout = loadLayout();
        this.friendLayout = loadFriendLayout();
        this.friendRequests = loadRequestLayout();
        this.friendActionLayout = loadFriendActionLayout();
        this.friendSubLayout = loadSubRequestLayout();
    }

    private HashMap<Integer, ItemStack> loadTeleporterLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        layout.put(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§aSpawn").build());
        layout.put(11, new ItemBuilder(Material.STICK).setDisplayName("§bKnockbackFFA").build());
        layout.put(15, new ItemBuilder(Material.SANDSTONE).setDisplayName("§eOneLine").build());
        layout.put(22, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("GuessIt").build());
        layout.put(26, new ItemBuilder(Material.IRON_PICKAXE).setDisplayName("§aBuildServer").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());

        for (int i = 27; i < 36; i++) {
            layout.put(i, PANE);
        }

        layout.put(39, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName("§6Lobby-1").build());
        layout.put(40, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName("§6Lobby-2").build());
        layout.put(41, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName("§6Lobby-3").build());

        for (int i = 45; i < 54; i++) {
            layout.put(i, PANE);
        }

        return layout;
    }

    private HashMap<Integer, ItemStack> loadLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        layout.put(0, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§6Privatnachrichten").build());
        layout.put(1, PANE);
        layout.put(6, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(7, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(8, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(9, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§dParty").build());
        layout.put(10, PANE);
        layout.put(15, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(16, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(17, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(18, new ItemBuilder(Material.GOLDEN_HELMET).setDisplayName("§bFreunde").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
        layout.put(19, PANE);
        layout.put(25, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(26, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(27, new ItemBuilder(Material.ENDER_EYE).setDisplayName("§aNachspringen").build());
        layout.put(28, PANE);
        layout.put(34, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(35, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(36, new ItemBuilder(Material.SNOWBALL).setDisplayName("§fSchnee").build());
        layout.put(37, PANE);
        layout.put(43, new ItemBuilder(Material.GRAY_DYE).setDisplayName("An").build());
        layout.put(44, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Aus").build());
        for (int i = 45; i < 54; i++) {
            layout.put(i, PANE);
        }
        return layout;
    }

    private HashMap<Integer, ItemStack> loadFriendLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        for (int i = 36; i < 45; i++) {
            layout.put(i, PANE);
        }
        layout.put(49, new ItemBuilder(Material.EMERALD).setDisplayName("Freundesanfragen").build());
        layout.put(47, new CustomPlayerHeadBuilder()
                .setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=", "" )
                .setDisplayName("§aZurück").build());
        layout.put(51, new CustomPlayerHeadBuilder()
                .setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19","")
                .setDisplayName("§aNächste").build());
        layout.put(53, new ItemBuilder(Material.COMPARATOR).setDisplayName("§cEinstellungen").build());
        return layout;
    }

    private HashMap<Integer, ItemStack> loadRequestLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        for (int i = 36; i < 45; i++) {
            layout.put(i, PANE);
        }
        layout.put(47, new ItemBuilder(Material.GREEN_TERRACOTTA)
                .setDisplayName("§aAlle annehmen")
                .addLore("§7Nimmt alle Freundschaftsanfragen an")
                .build());
        layout.put(51, new ItemBuilder(Material.RED_TERRACOTTA)
                .setDisplayName("§cAlle ablehnen")
                .addLore("§7Lehnt alle derzeitigen Freundschaftsanfragen ab")
                .build());
        return layout;
    }

    private HashMap<Integer, ItemStack> loadFriendActionLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(9);
        layout.put(1, PANE);
        layout.put(10, PANE);
        layout.put(19, PANE);
        layout.put(12, new ItemBuilder(Material.ENDER_PEARL).setDisplayName("Nach springen").build());
        layout.put(14, new ItemBuilder(Material.CAKE).setDisplayName("In Party einladen").build());
        layout.put(16, new ItemBuilder(Material.BARRIER).setDisplayName("Freund entfernen").build());
        return layout;
    }

    private HashMap<Integer, ItemStack> loadSubRequestLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(9);
        layout.put(1, PANE);
        layout.put(10, PANE);
        layout.put(19, PANE);
        layout.put(13, new ItemBuilder(Material.GREEN_TERRACOTTA)
                .setDisplayName("§aAnnehmen")
                .addLore("§7Nimmt alle Freundschaftsanfragen an")
                .build());
        layout.put(15, new ItemBuilder(Material.RED_TERRACOTTA)
                .setDisplayName("§cAblehnen")
                .addLore("§7Lehnt alle derzeitigen Freundschaftsanfragen ab")
                .build());
        return layout;
    }

    public void setItems(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        ItemStack skull = new CustomPlayerHeadBuilder().setSkinOverValues(cloudPlayer.getSkinValue(), "")
                .setDisplayName("§aProfile").build();
        player.getInventory().clear();

        player.getInventory().setItem(0, teleporter);
        player.getInventory().setItem(2, hider);
        player.getInventory().setItem(6, lobby);
        player.getInventory().setItem(8, skull);

        if (cloudPlayer.hasPermission("player.nick.auto")) {
            player.getInventory().setItem(4, nick);
        }
    }

    protected Map<Integer, ItemStack> getTeleporterLayout() { return teleporterLayout; }

    protected Map<Integer, ItemStack> getSettingsLayout() {
        return settingsLayout;
    }

    protected Map<Integer, ItemStack> getFriendLayout() {
        return friendLayout;
    }

    protected Map<Integer, ItemStack> getFriendRequests() {
        return friendRequests;
    }

    protected Map<Integer, ItemStack> getFriendActionLayout() {
        return friendActionLayout;
    }

    protected Map<Integer, ItemStack> getFriendSubLayout() {
        return friendSubLayout;
    }
}