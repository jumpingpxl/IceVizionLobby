package de.cosmiqglow.lobby.profile;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProfileCache {

    private final Map<Player, LobbyProfile> profiles;

    public ProfileCache() {
        this.profiles = new HashMap<>();
    }

    public void addProfile(Player player) {
        this.profiles.putIfAbsent(player, new LobbyProfile(player));
    }

    public void removeProfile(Player player) {
        this.profiles.remove(player);
    }

    public Map<Player, LobbyProfile> getProfiles() {
        return profiles;
    }
}
