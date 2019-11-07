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

    private final ItemStack teleporter, panel, hider, builder;
    private final Map<Integer, ItemStack> teleporterLayout;
    private final Map<Integer, ItemStack> settingsLayout;
    private final Map<Integer, ItemStack> friendLayout;
    private final Map<Integer, ItemStack> friendActionLayout;

    public ItemUtil() {
        this.teleporter = new ItemBuilder(Material.NETHER_STAR).setDisplayName("§bMinispiele").build();
        this.panel = new ItemBuilder(Material.NOTE_BLOCK).setDisplayName("§eEinstellungen").build();
        this.hider = new ItemBuilder(Material.BLAZE_ROD).setDisplayName("§aSpieler Sichtbarkeit").build();
        this.builder = new ItemBuilder(Material.IRON_PICKAXE).setDisplayName("§aBauServer").build();
        this.teleporterLayout = loadTeleporterLayout();
        this.settingsLayout = loadLayout();
        this.friendLayout = loadFriendLayout();
        this.friendActionLayout = loadFriendActionLayout();
    }

    private HashMap<Integer, ItemStack> loadTeleporterLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        layout.put(4, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§aSpawn").build());
        layout.put(11, new ItemBuilder(Material.STICK).setDisplayName("§bKnockbackFFA").build());
        layout.put(15, new ItemBuilder(Material.SANDSTONE).setDisplayName("§eOneLine").build());
        layout.put(22, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("GuessIt").build());
        layout.put(26, new ItemBuilder(Material.IRON_PICKAXE).setDisplayName("§aBuildServer").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
        ItemStack pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build();

        for (int i = 27; i < 36; i++) {
            layout.put(i, pane);
        }

        layout.put(39, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName("§6Lobby-1").build());
        layout.put(40, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName("§6Lobby-2").build());
        layout.put(41, new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName("§6Lobby-3").build());

        for (int i = 45; i < 54; i++) {
            layout.put(i, pane);
        }

        return layout;
    }

    private HashMap<Integer, ItemStack> loadLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        layout.put(0, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§6Privatnachrichten").build());
        layout.put(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(6, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(7, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(8, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(9, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§dParty").build());
        layout.put(10, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(15, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(16, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(17, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(18, new ItemBuilder(Material.GOLDEN_HELMET).setDisplayName("§bFreunde").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
        layout.put(19, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(25, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(26, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(27, new ItemBuilder(Material.ENDER_EYE).setDisplayName("§aNachspringen").build());
        layout.put(28, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(34, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(35, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        ItemStack pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build();
        for (int i = 36; i < 45; i++) {
            layout.put(i, pane);
        }
        return layout;
    }

    private HashMap<Integer, ItemStack> loadFriendLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        ItemStack pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build();
        for (int i = 36; i < 45; i++) {
            layout.put(i, pane);
        }
        layout.put(49, new ItemBuilder(Material.EMERALD).setDisplayName("Freundesanfragen").build());
        layout.put(47, new CustomPlayerHeadBuilder()
                .setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=", "" )
                .setDisplayName("§aZurück").build());
        layout.put(51, new CustomPlayerHeadBuilder()
                .setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19","")
                .setDisplayName("§aNächste").build());
        return layout;
    }

    private HashMap<Integer, ItemStack> loadFriendActionLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(9);
        ItemStack pane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§0").build();
        layout.put(1, pane);
        layout.put(10, pane);
        layout.put(19, pane);
        layout.put(12, new ItemBuilder(Material.ENDER_PEARL).setDisplayName("Nach springen").build());
        layout.put(14, new ItemBuilder(Material.CAKE).setDisplayName("In Party einladen").build());
        layout.put(16, new ItemBuilder(Material.BARRIER).setDisplayName("Freund entfernen").build());
        return layout;
    }

    public void setItems(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        ItemStack skull = new CustomPlayerHeadBuilder().setSkinOverValues(cloudPlayer.getSkinValue(), "")
                .setDisplayName("§aFreunde").build();
        player.getInventory().clear();

        if (cloudPlayer.hasPermission("network.buildserver")) {
            player.getInventory().setItem(0, teleporter);
            player.getInventory().setItem(2, hider);
            player.getInventory().setItem(4, builder);
            player.getInventory().setItem(6, panel);
            player.getInventory().setItem(8, skull);
        } else {
            player.getInventory().setItem(1, teleporter);
            player.getInventory().setItem(3, hider);
            player.getInventory().setItem(5, panel);
            player.getInventory().setItem(7, skull);
        }
    }

    protected Map<Integer, ItemStack> getTeleporterLayout() { return teleporterLayout; }

    protected Map<Integer, ItemStack> getSettingsLayout() {
        return settingsLayout;
    }

    protected Map<Integer, ItemStack> getFriendLayout() {
        return friendLayout;
    }

    protected Map<Integer, ItemStack> getFriendActionLayout() {
        return friendActionLayout;
    }
}