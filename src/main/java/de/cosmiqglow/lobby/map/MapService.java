package de.cosmiqglow.lobby.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.cosmiqglow.lobby.map.adapter.LocationTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.Optional;

public class MapService {

    private final Gson gson;
    private LobbyMap lobbyMap;

    public MapService() {
        this.gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationTypeAdapter()).create();
    }

    private void loadMap() {
        File file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), "map.json");
        Optional<LobbyMap> lobby = JsonFileLoader.load(file, LobbyMap.class, gson);

        if (lobby.isPresent()) {
            lobbyMap = lobby.get();
        } else {
            Bukkit.getConsoleSender().sendMessage("§cEs wurde keine map.json gefunden");
        }
    }

    public LobbyMap getLobbyMap() {
        return lobbyMap;
    }
}
