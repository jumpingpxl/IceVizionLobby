package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ColoredBuilder;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.aves.item.SkullBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemUtil {

    public static final ItemStack PANE = new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE)
            .setColor(DyeColor.LIGHT_BLUE).setDisplayName("§0").build();
    private final ItemStack teleporter, lobby;
    private final Map<Integer, ItemStack> settingsLayout;
    private final Map<Integer, ItemStack> friendLayout;
    private final Map<Integer, ItemStack> friendRequests;
    private final Map<Integer, ItemStack> friendActionLayout;
    private final Map<Integer, ItemStack> friendSubLayout;

    public ItemUtil() {
        this.teleporter = new ItemBuilder(Material.NETHER_STAR).setDisplayName("§bMinispiele").build();
        this.lobby = new ItemBuilder(Material.WATCH).setDisplayName("§aLobby wechseln").build();
        this.settingsLayout = loadLayout();
        this.friendLayout = loadFriendLayout();
        this.friendRequests = loadRequestLayout();
        this.friendActionLayout = loadFriendActionLayout();
        this.friendSubLayout = loadSubRequestLayout();
    }

    /**
     * Loads the layout for the settings ui.
     * @return The HashMap with the layout
     */

    private HashMap<Integer, ItemStack> loadLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(32);
        layout.put(0, new ItemBuilder(Material.BOOK_AND_QUILL).setDisplayName("§6Privatnachrichten").build());
        layout.put(1, PANE);
        layout.put(6, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§aJeder").build());
        layout.put(7, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§6Freunde").build());
        layout.put(8, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§cKeiner").build());
        layout.put(9, new ItemBuilder(Material.FIREWORK).setDisplayName("§dParty").build());
        layout.put(10, PANE);
        layout.put(15, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§aJeder").build());
        layout.put(16, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§6Freunde").build());
        layout.put(17, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§cKeiner").build());
        layout.put(18, new ItemBuilder(Material.BLAZE_ROD).setDisplayName("§bSpieler-Sichtbarkeit").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
        layout.put(19, PANE);
        layout.put(24, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§aJeder").build());
        layout.put(25, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§6Freunde").build());
        layout.put(26, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§cKeiner").build());
        layout.put(27, new ItemBuilder(Material.EYE_OF_ENDER).setDisplayName("§aNachspringen").build());
        layout.put(28, PANE);
        layout.put(34, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§6Freunde").build());
        layout.put(35, new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY).setDisplayName("§cKeiner").build());
        layout.put(37, PANE);
        for (int i = 45; i < 54; i++) {
            layout.put(i, PANE);
        }
        return layout;
    }

    /**
     * Loads the layout for the friend ui.
     * @return The HashMap with the layout
     */

    private HashMap<Integer, ItemStack> loadFriendLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(13);
        for (int i = 36; i < 45; i++) {
            layout.put(i, PANE);
        }
        layout.put(49, new ItemBuilder(Material.EMERALD).setDisplayName("Freundesanfragen").build());
        layout.put(47, new SkullBuilder()
                .setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=", "" )
                .setDisplayName("§aZurück").build());
        layout.put(51, new SkullBuilder()
                .setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19","")
                .setDisplayName("§aNächste").build());
        layout.put(53, new ItemBuilder(Material.REDSTONE_COMPARATOR).setDisplayName("§cEinstellungen").build());
        return layout;
    }

    /**
     * Loads the layout for the ui with the friend requests.
     * @return The HashMap with the layout
     */

    private HashMap<Integer, ItemStack> loadRequestLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(11);
        for (int i = 36; i < 45; i++) {
            layout.put(i, PANE);
        }
        layout.put(47, new ColoredBuilder(ColoredBuilder.DyeType.CLAY_BLOCK).setColor(DyeColor.GREEN)
                .setDisplayName("§aAlle annehmen")
                .addLore("§7Nimmt alle Freundschaftsanfragen an")
                .build());
        layout.put(51, new ColoredBuilder(ColoredBuilder.DyeType.CLAY_BLOCK).setColor(DyeColor.RED)
                .setDisplayName("§cAlle ablehnen")
                .addLore("§7Lehnt alle derzeitigen Freundschaftsanfragen ab")
                .build());
        return layout;
    }

    /**
     * Loads the layout for the ui with the friend actions.
     * @return The HashMap with the layout
     */

    private HashMap<Integer, ItemStack> loadFriendActionLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(9);
        layout.put(1, PANE);
        layout.put(10, PANE);
        layout.put(19, PANE);
        //layout.put(12, new ItemBuilder(Material.ENDER_PEARL).setDisplayName("Nach springen").build());
        //layout.put(14, new ItemBuilder(Material.CAKE).setDisplayName("In Party einladen").build());
        layout.put(14, new ItemBuilder(Material.BARRIER).setDisplayName("Freund entfernen").build());
        return layout;
    }

    /**
     * Loads the layout for sub request ui for the friends.
     * @return The HashMap with the layout
     */

    private HashMap<Integer, ItemStack> loadSubRequestLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>(9);
        layout.put(1, PANE);
        layout.put(10, PANE);
        layout.put(19, PANE);
        layout.put(13, new ColoredBuilder(ColoredBuilder.DyeType.CLAY_BLOCK).setColor(DyeColor.GREEN)
                .setDisplayName("§aAnnehmen")
                .build());
        layout.put(15, new ColoredBuilder(ColoredBuilder.DyeType.CLAY_BLOCK).setColor(DyeColor.RED)
                .setDisplayName("§cAblehnen")
                .build());
        return layout;
    }

    /**
     * Set the items into the hotbar of a specific player.
     * @param cloudPlayer The player to set the items
     */

    public void setItems(CloudPlayer cloudPlayer) {
        ItemStack skull = new SkullBuilder()
                .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                .setDisplayName("§aProfil").build();
        cloudPlayer.getPlayer().getInventory().clear();

        cloudPlayer.getPlayer().getInventory().setItem(0, teleporter);
        cloudPlayer.getPlayer().getInventory().setItem(4, lobby);
        cloudPlayer.getPlayer().getInventory().setItem(8, skull);
    }

    /**
     * Returns the HashMap for the settings layout.
     * @return The underlying map
     */

    protected Map<Integer, ItemStack> getSettingsLayout() {
        return settingsLayout;
    }

    /**
     * Returns the HashMap for the friend layout.
     * @return The underlying map
     */

    public Map<Integer, ItemStack> getFriendLayout() {
        return friendLayout;
    }

    /**
     * Returns the HashMap for the friend request layout.
     * @return The underlying map
     */

    protected Map<Integer, ItemStack> getFriendRequests() {
        return friendRequests;
    }

    /**
     * Returns the HashMap for the friend action layout.
     * @return The underlying map
     */

    protected Map<Integer, ItemStack> getFriendActionLayout() {
        return friendActionLayout;
    }

    /**
     * Returns the HashMap for the friend sub layout.
     * @return The underlying map
     */

    protected Map<Integer, ItemStack> getFriendSubLayout() {
        return friendSubLayout;
    }
}