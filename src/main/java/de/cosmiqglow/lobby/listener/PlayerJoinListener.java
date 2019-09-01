package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.aves.chat.Messages;
import de.cosmiqglow.aves.item.FireworkBuilder;
import de.cosmiqglow.aves.item.MapBuilder;
import de.cosmiqglow.aves.item.RenderMapBuilder;
import de.cosmiqglow.aves.item.util.CursorDirection;
import de.cosmiqglow.aves.item.util.CursorType;
import de.cosmiqglow.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MinecraftFont;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PlayerJoinListener implements Listener {

    private final Lobby plugin;
    private ItemStack item;

    public PlayerJoinListener(Lobby plugin) {
        this.plugin = plugin;
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL("https://site.com/image.png"));
            item = new RenderMapBuilder(MapBuilder.MapType.MAP).setRenderOnce(true).setImage(bufferedImage)
                    .addText(0, 0, MinecraftFont.Font, "Hello there")
                    .addCursor(20, 20, CursorDirection.EAST, CursorType.WHITE_DOT).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        plugin.getItemUtil().setItems(player);
        plugin.getProfileCache().addProfile(player);
        plugin.getVisibilityUtil().hideOnJoin(plugin, player);

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        player.getInventory().setItem(0, item);

        if (cloudPlayer.getFirstLogin() + 5000L >= System.currentTimeMillis()) {
            new FireworkBuilder().addEffect(FireworkEffect.builder().with(FireworkEffect.Type.STAR).
                    withColor(Color.YELLOW).build()).setPower(1).build();
            player.sendMessage(Messages.CG_HEADER.toString());
            player.sendMessage("§e» §n§7Lobby");
            player.sendMessage(" ");
            player.sendMessage("§e» §7'Das Glück ist das Einzige,");
            player.sendMessage("      §7was sich verdoppelt, wenn es teilt'");
            player.sendMessage("§e» E7Albert Schweizer");
            player.sendMessage(" ");
            player.sendMessage("§e» §7Map: §eLobby");
            player.sendMessage(Messages.CG_HEADER.toString());
        }
    }
}