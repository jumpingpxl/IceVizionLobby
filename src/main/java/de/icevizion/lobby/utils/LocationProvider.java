package de.icevizion.lobby.utils;

import com.google.gson.*;
import de.icevizion.lobby.LobbyPlugin;
import org.bukkit.Location;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LocationProvider {

	private final Gson gson;
	private final LobbyPlugin lobbyPlugin;
	private final File file;
	private final Map<String, Location> locations;

	public LocationProvider(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		gson = new GsonBuilder().setPrettyPrinting().create();
		file = new File(lobbyPlugin.getServer().getWorlds().get(0).getWorldFolder(), "map.json");
		locations = new HashMap<>();
		load();
	}

	public Location getLocation(String key) {
		return locations.get(key);
	}

	private void load() {
		JsonArray jsonArray = getJsonArrayFromFile();
		if(Objects.isNull(jsonArray)){
			return;
		}

		jsonArray.forEach(jsonElement -> {
			JsonObject jsonObject = (JsonObject) jsonElement;
			String key = jsonObject.get("key").getAsString();
			String worldName = jsonObject.get("world").getAsString();
			double x = jsonObject.get("x").getAsDouble();
			double y = jsonObject.get("y").getAsDouble();
			double z = jsonObject.get("z").getAsDouble();
			float yaw = jsonObject.get("yaw").getAsFloat();
			float pitch = jsonObject.get("pitch").getAsFloat();

			Location location = new Location(lobbyPlugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);
			locations.put(key, location);
		});
	}

	private void save() {
		JsonArray jsonArray = new JsonArray();
		locations.forEach((key, location) -> {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("key", key);
			jsonObject.add("world", new JsonPrimitive(location.getWorld().getName()));
			jsonObject.add("x", new JsonPrimitive(location.getX()));
			jsonObject.add("y", new JsonPrimitive(location.getY()));
			jsonObject.add("z", new JsonPrimitive(location.getZ()));
			jsonObject.add("yaw", new JsonPrimitive(location.getYaw()));
			jsonObject.add("pitch", new JsonPrimitive(location.getPitch()));
			jsonArray.add(jsonObject);
		});

		saveJsonArrayToFile(jsonArray);
	}

	private JsonArray getJsonArrayFromFile() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			return (JsonArray) new JsonParser().parse(inputStreamReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void saveJsonArrayToFile(JsonArray jsonArray) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8);
			Writer writer = new BufferedWriter(outputStreamWriter);
			writer.write(gson.toJson(jsonArray));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
