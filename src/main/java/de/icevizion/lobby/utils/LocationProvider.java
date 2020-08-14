package de.icevizion.lobby.utils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.icevizion.lobby.LobbyPlugin;
import org.bukkit.Location;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
		locations = Maps.newHashMap();

		load();
	}

	public Location getLocation(String key) {
		return locations.get(key);
	}

	public boolean matches(Location location, String key) {
		return matches(location, getLocation(key));
	}

	public boolean matches(Location location, Location compareTo) {
		return location.getBlockX() == compareTo.getBlockX()
				&& location.getBlockY() == compareTo.getBlockY()
				&& location.getBlockZ() == compareTo.getBlockZ();
	}

	public void addLocation(String key, Location location) {
		locations.put(key, location);
		save();
	}

	private void load() {
		JsonArray jsonArray = getJsonArrayFromFile();
		if (Objects.isNull(jsonArray)) {
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

			Location location = new Location(lobbyPlugin.getServer().getWorld(worldName), x, y, z, yaw,
					pitch);
			locations.put(key, location);
		});
	}

	private void save() {
		JsonArray jsonArray = new JsonArray();
		locations.forEach((key, location) -> {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("key", key);
			jsonObject.addProperty("world", location.getWorld().getName());
			jsonObject.addProperty("x", location.getX());
			jsonObject.addProperty("y", location.getY());
			jsonObject.addProperty("z", location.getZ());
			jsonObject.addProperty("yaw", location.getYaw());
			jsonObject.addProperty("pitch", location.getPitch());
			jsonArray.add(jsonObject);
		});

		saveJsonArrayToFile(jsonArray);
	}

	private JsonArray getJsonArrayFromFile() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),
					StandardCharsets.UTF_8);
			JsonArray jsonArray = (JsonArray) new JsonParser().parse(inputStreamReader);
			inputStreamReader.close();
			return jsonArray;
		} catch (IOException e) {
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
			outputStreamWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
