package de.icevizion.lobby.utils.inventories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.InventoryBuilder;
import de.icevizion.lobby.utils.itemfactories.LobbiesItemFactory;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LobbiesInventory extends InventoryBuilder {

	private static final int[][] LOBBY_POSITIONS = {{4}, {3, 5}, {3, 4, 5}, {2, 3, 5, 6},
			{2, 3, 4, 5, 6}};
	private final LobbyPlugin lobbyPlugin;
	private final LobbiesItemFactory itemFactory;
	private Map<String, Integer> itemPositions;

	public LobbiesInventory(LobbyPlugin lobbyPlugin, Locale locale,
	                        List<IClusterSpigot> activeLobbies) {
		super(lobbyPlugin.getLocales().getString(locale, "inventoryLobbiesTitle"),
				18 + 9 * (int) Math.ceil((double) activeLobbies.size() / LOBBY_POSITIONS.length));
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new LobbiesItemFactory(lobbyPlugin, locale);
		itemPositions = new HashMap<>();
		calculateItemPositions(activeLobbies);
	}

	@Override
	public boolean isCacheable() {
		return true;
	}

	@Override
	public void draw() {
		if (isFirstDraw()) {
			setBackgroundItem(0, itemFactory.getBackgroundItem());
			setBackgroundItem(8, itemFactory.getBackgroundItem());

			setBackgroundItem(9, itemFactory.getBackgroundItem());
			setBackgroundItem(17, itemFactory.getBackgroundItem());

			setBackgroundItem(18, itemFactory.getBackgroundItem());
			setBackgroundItem(26, itemFactory.getBackgroundItem());
		}
	}

	public void calculateItemPositions(List<IClusterSpigot> lobbyList) {
		getItems().clear();

		int startPosition = 9;
		int tempIndex = 0;
		List<IClusterSpigot> tempLobbyList = new ArrayList<>();
		for (int i = 0; i < lobbyList.size(); i++) {
			if (tempIndex < LOBBY_POSITIONS.length - 1 && i < lobbyList.size() - 1) {
				tempLobbyList.add(lobbyList.get(i));
				tempIndex++;
			} else {
				int[] positions = LOBBY_POSITIONS[tempIndex];

				for (int i1 = 0; i1 < tempLobbyList.size(); i1++) {
					IClusterSpigot lobby = tempLobbyList.get(i1);
					int index = startPosition + positions[i1];
					setLobbyItem(index, lobby);
					itemPositions.put(lobby.getUuid(), index);
				}

				startPosition += 9;
				tempIndex = 0;
				tempLobbyList.clear();
			}
		}

		buildInventory();
	}

	public void updateLobby(IClusterSpigot lobby) {
		if (!itemPositions.containsKey(lobby.getUuid())) {
			return;
		}

		setLobbyItem(itemPositions.get(lobby.getUuid()), lobby);
		buildInventory();
	}

	private void setLobbyItem(int index, IClusterSpigot lobby) {
		if (lobbyPlugin.getCloud().getIdentifier().equals(String.valueOf(lobby.getID()))) {
			setItem(index,
					itemFactory.getCurrentLobbyItem(lobby.getDisplayName(), lobby.getPlayerCount()));
			return;
		}

		setItem(index, itemFactory.getLobbyItem(lobby.getDisplayName(), lobby.getPlayerCount()),
				event -> {
					CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(
							(Player) event.getWhoClicked());
					cloudPlayer.sendToServer(lobby);
				});
	}
}
