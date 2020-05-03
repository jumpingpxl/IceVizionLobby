package de.icevizion.lobby.map;

import de.icevizion.aves.file.JsonFileLoader;
import de.icevizion.lobby.Lobby;
import io.sentry.Sentry;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.Locale;
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
        Sentry.capture("The requested location is null. The name was " + name);
        return null;
    }

    private void loadMap() {
        this.file = new File(Bukkit.getWorlds().get(0).getWorldFolder(), "map.json");
        if (file.exists()) {
            lobbyMap = JsonFileLoader.load(file, LobbyMap.class, Lobby.GSON);
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

    public Optional<LobbyMap> getLobbyMap() {
        return lobbyMap;
    }
}