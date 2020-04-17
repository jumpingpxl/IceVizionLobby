package de.icevizion.lobby.utils;

import com.mongodb.client.MongoCollection;
import de.icevizion.aves.util.LocationUtil;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 17/04/2020 17:21
 */

public class UselessChestService implements Listener {

    private final Lobby lobby;

    private ReentrantLock lock;
    private MongoCollection<Document> dataCollection;

    private long count;
    private ArmorStand armorStand;

    public UselessChestService(Lobby lobby) {
        this.lobby = lobby;

        lock = new ReentrantLock();
        dataCollection = Cloud.getInstance().getCloudMongo().getCollection("data");

        loadFromDatabase();
        startScheduler();

        Bukkit.getPluginManager().registerEvents(this, lobby);
    }

    // =======

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        lobby.getLogger().info("PlayerInteractEvent");
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }
        lobby.getLogger().info("PlayerInteractEvent with Chest!");

        //Code to check if the chest is the useless chest
        if (LocationUtil.compare(event.getClickedBlock().getLocation(),
                lobby.getMapService().getLobbyMap().get().getUselessChest(), false)) {
            //It is our chest, yay
            lobby.getLogger().info("PlayerInteractEvent with OUR Chest!");
            event.setCancelled(false);
            //Perhaps call this async since it is being synchronized?
            increase();
        }
    }

    // =======

    private void startScheduler() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(lobby, () -> {
            loadFromDatabase();
        }, 1000, 5000);
    }

    private void loadFromDatabase() {
        lock.lock();
            Document document = dataCollection.find(new Document("name", "UselessChest")).first();
            if (document == null) {
                document = new Document("name", "UselessChest");
                document.append("count", 0L);
                dataCollection.insertOne(document);
            }

            count = document.getLong("count");
            displayCurrentCount();
        try {
            Cloud.getInstance().getCloudMongo().getCollection("data");
        }finally {
            lock.unlock();
        }
    }

    private void displayCurrentCount() {
        //Update hologram here
        if (armorStand == null) {
            Location location = lobby.getMapService().getLobbyMap().get().getUselessChest();
            if (location == null)
                return;

            armorStand = (ArmorStand) location.getWorld()
                    .spawnEntity(location.clone().add(0, 0.25, 0), EntityType.ARMOR_STAND);
            armorStand.setFireTicks(0);

            armorStand.setCustomNameVisible(true);
          //  armorStand.setVisible(false);
            lobby.getLogger().log(Level.INFO, "Spawned UselessChest Armorstand at "+location);
        }

        armorStand.setCustomName("§3"+count+"x");
    }

    private void increase() {
        lock.lock();
        try {
            count++;
            dataCollection.updateOne(new Document("name", "UselessChest"), new Document("$inc", new Document("count", 1)));
            displayCurrentCount();
        }finally {
            lock.unlock();
        }
    }
}
