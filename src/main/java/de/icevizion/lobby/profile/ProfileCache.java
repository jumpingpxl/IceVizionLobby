package de.icevizion.lobby.profile;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ProfileCache {

    private final ReentrantLock lock;
    private final Map<Player, LobbyProfile> profiles;

    public ProfileCache() {
        this.profiles = new HashMap<>();
        lock = new ReentrantLock();
    }

    /**
     * Add a player with a new profile to the service
     * @param player The player to add
     */

    public void addProfile(Player player) {
        getLock().lock();
        try {
            this.profiles.putIfAbsent(player, new LobbyProfile());
        }finally {
            getLock().unlock();
        }
    }

    /**
     * Remove a profile from the service
     * @param player The player to remove
     */

    public void removeProfile(Player player) {
        getLock().lock();
        try {
            this.profiles.remove(player);
        }finally {
            getLock().unlock();
        }
    }

    /**
     * Get the profile from a specific player
     * @param player The player
     * @return The fetched profile
     */

    public LobbyProfile getProfile(Player player) {
        return this.profiles.get(player);
    }

    /**
     * Returns the underlying map with all profiles
     * @return The underlying map
     */

    public Map<Player, LobbyProfile> getProfiles() {
        return profiles;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
