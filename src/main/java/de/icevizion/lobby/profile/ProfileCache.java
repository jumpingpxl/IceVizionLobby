package de.icevizion.lobby.profile;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProfileCache {

    private final Map<Player, LobbyProfile> profiles;

    public ProfileCache() {
        this.profiles = new HashMap<>();
    }

    /**
     * Add a player with a new profile to the service
     * @param player The player to add
     */

    public void addProfile(Player player) {
        this.profiles.putIfAbsent(player, new LobbyProfile());
    }

    /**
     * Remove a profile from the service
     * @param player The player to remove
     */

    public void removeProfile(Player player) {
        this.profiles.remove(player);
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
}
