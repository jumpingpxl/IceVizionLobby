package de.icevizion.lobby.map;

import de.icevizion.aves.file.JsonFileLoader;
import de.icevizion.lobby.Lobby;
import net.minecraft.server.v1_8_R3.BlockIce;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class MapService {

    private File file;

    private Optional<LobbyMap> lobbyMap;

    public MapService() {
        loadMap();
    }

    public Location getLocation(String name) {
        if (lobbyMap.isPresent()) {
            switch (name.toLowerCase(Locale.ENGLISH)) {
                case "spawn":
                    return lobbyMap.get().getSpawn();
                case "knockbackffa":
                    return lobbyMap.get().getKBFFA();
                case "oneline":
                    return lobbyMap.get().getOneline();
                case "bedwars":
                    return lobbyMap.get().getBedwars();
                case "guessit":
                    return lobbyMap.get().getGuessIt();
            }
        }
        return null;
    }

    private void loadMap() {
        this.file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), "map.json");
        if (file.exists()) {
            lobbyMap = JsonFileLoader.load(file, LobbyMap.class, Lobby.GSON);
            Bukkit.getConsoleSender().sendMessage("Daten wurden geladen");
        } else {
            lobbyMap = Optional.of(new LobbyMap());
            JsonFileLoader.save(file, lobbyMap.get(), Lobby.GSON);
        }
    }

    public boolean setValue(String type,Location location) {
        if (lobbyMap.isPresent()) {
            setData(lobbyMap.get(), type, location);
            JsonFileLoader.save(file, lobbyMap.get(), Lobby.GSON);
            return true;
        } else {
            lobbyMap = Optional.of(new LobbyMap());
            setData(lobbyMap.get(), type, location);
            JsonFileLoader.save(file, lobbyMap.get(), Lobby.GSON);
            return true;
        }
    }

    private void setData(LobbyMap lobbyMap, String type, Location location) {
        switch (type.toLowerCase(Locale.ENGLISH)) {
            case "spawn":
                lobbyMap.setSpawn(location);
                break;
            case "oneline":
                lobbyMap.setOneline(location);
                break;
            case "kbffa":
                lobbyMap.setKBFFA(location);
                break;
            case "guessit":
                lobbyMap.setGuessit(location);
                break;
            case "bedwars":
                lobbyMap.setBedwars(location);
                break;
        }
    }

    /**
     * Get a location from a serialized location.
     * @param locationMap The serialized location
     * @return The converted location
     */

    public Location getLocationFromMap(HashMap<String, Object> locationMap) {
        double x = 0;
        double y = 0;
        double z = 0;
        float yaw = 0;
        float pitch = 0;

        for (Map.Entry<String, Object> entrySet : locationMap.entrySet()) {
            switch (entrySet.getKey()) {
                case "x":
                    x = (double) entrySet.getValue();
                    break;
                case "y":
                    y = (double) entrySet.getValue();
                    break;
                case "z":
                    z = (double) entrySet.getValue();
                    break;
                case "yaw":
                    yaw = (float) entrySet.getValue();
                    break;
                case "pitch":
                    pitch = (float) entrySet.getValue();
                    break;
            }
        }
        return new Location(Bukkit.getWorlds().get(0), x, y, z, yaw, pitch);
    }

    public Optional<LobbyMap> getLobbyMap() {
        return lobbyMap;
    }
}