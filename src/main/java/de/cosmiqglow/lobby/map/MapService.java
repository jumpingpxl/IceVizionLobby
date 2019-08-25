package de.cosmiqglow.lobby.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.cosmiqglow.lobby.map.adapter.LocationTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

public class MapService {

    private final Gson gson;
    private Optional<LobbyMap> lobbyMap;
    private File file;

    public MapService() {
        this.gson = new GsonBuilder().
                serializeNulls().
                setPrettyPrinting().
                registerTypeAdapter(Location.class, new LocationTypeAdapter()).
                create();

        loadMap();
    }

    public Location getLocation(String name) {
        if (lobbyMap.isPresent()) {
            switch (name.toLowerCase(Locale.ENGLISH)) {
                case "spawn":
                    return lobbyMap.get().getSpawn();
                case "knockbackffa":
                    return lobbyMap.get().getKBFFA();
                case "miniuhc":
                    return lobbyMap.get().getUHC();
                case "daily":
                    return lobbyMap.get().getDailyReward();
            }
        }
        return null;
    }

    private void loadMap() {
        this.file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), "map.json");
        if (file.exists()) {
            lobbyMap = JsonFileLoader.load(file, LobbyMap.class, gson);
            Bukkit.getConsoleSender().sendMessage("Daten wurden geladen");
        } else {
            lobbyMap = Optional.of(new LobbyMap());
            JsonFileLoader.save(file,lobbyMap.get(),gson);
        }
    }

    public boolean setValue(String type,Location location) {
        if(lobbyMap.isPresent()) {
            setData(lobbyMap.get(), type, location);
            JsonFileLoader.save(file,lobbyMap.get(),gson);
            return true;
        } else {
            lobbyMap = Optional.of(new LobbyMap());
            setData(lobbyMap.get(), type, location);
            JsonFileLoader.save(file,lobbyMap.get(),gson);
            return true;
        }
    }

    private void setData(LobbyMap lobbyMap, String type, Location location) {
        switch (type.toLowerCase(Locale.ENGLISH)) {
            case "spawn":
                lobbyMap.setSpawn(location);
                break;
            case "uhc":
                lobbyMap.setUHC(location);
                break;
            case "kbffa":
                lobbyMap.setKBFFA(location);
                break;
            case "daily":
                lobbyMap.setDailyReward(location);
                break;
        }
    }

    public Optional<LobbyMap> getLobbyMap() {
        return lobbyMap;
    }

    public Gson getGson() {
        return gson;
    }
}