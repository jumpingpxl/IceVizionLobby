package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.CustomPlayerHeadBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DailyRewardUtil {

    private final ArmorStand armorStand;
    private final ItemStack head;

    public DailyRewardUtil(Location location) {
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.head = new CustomPlayerHeadBuilder().setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM2OTk5YzFmNTk2NDZmZTAxMjFhOTlkOWIwZmVmMzk5NmVkNzhjNmRjNTU1MzFkYWJjY2E3MDhjMWRjZjkxNiJ9fX0=", "").build();
        this.armorStand.setHelmet(head);
        this.armorStand.setCustomName("§eTägliche Belohnung");
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);
        this.armorStand.setCustomNameVisible(true);
    }

    public void despawn() {
        armorStand.remove();
    }

    public void checkDailyReward(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        if (!cloudPlayer.extradataContains("daily")) {
            giveReward(player);
        } else {
            long timestamp = (long) cloudPlayer.extradataGet("daily");

            if (timestamp >= System.currentTimeMillis()) {
                player.sendMessage("§cBitte komme morgen wieder um einen Reward zu erhalten");
            } else {
                giveReward(player);
            }
        }
    }

    private void giveReward(Player player) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        cloudPlayer.addCoins(100);
        cloudPlayer.extradataSet("daily", System.currentTimeMillis() + getRestDayTime());
        player.sendMessage("§7Du hast §e100 §7Glows bekommen");
    }

    private long getRestDayTime() {
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        LocalDate tomorrow = now.toLocalDate().plusDays(1);
        ZonedDateTime tomorrowStart = tomorrow.atStartOfDay(zoneId);
        return Duration.between(now, tomorrowStart).toMillis();
    }
}