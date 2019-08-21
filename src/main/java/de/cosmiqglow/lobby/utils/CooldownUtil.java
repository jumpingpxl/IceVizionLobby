package de.cosmiqglow.lobby.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CooldownUtil {

    private final Map<Player, Long> cooldowns;

    public CooldownUtil() {
        this.cooldowns = new HashMap<>();
    }

    public void addCooldown(Player player, long timestamp) {
        this.cooldowns.putIfAbsent(player, System.currentTimeMillis() + timestamp);
    }

    public boolean hasCooldown(Player player) {
        return this.cooldowns.containsKey(player);
    }

    public Map<Player, Long> getCooldowns() {
        return cooldowns;
    }
}