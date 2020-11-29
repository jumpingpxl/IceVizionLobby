package de.icevizion.lobby.utils.inventories;

import de.icevizion.aves.inventory.InventoryRows;
import de.icevizion.aves.inventory.TranslatedInventory;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.LobbiesItemFactory;
import net.titan.lib.network.spigot.IClusterSpigot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LobbiesInventory extends TranslatedInventory {

	private static final int[][] LOBBY_POSITIONS = {{4}, {3, 5}, {3, 4, 5}, {2, 3, 5, 6},
			{2, 3, 4, 5, 6}};
	private final LobbyPlugin lobbyPlugin;
	private final LobbiesItemFactory itemFactory;
	private final IClusterSpigot currentLobby;
	private Map<String, Integer> itemPositions;

	public LobbiesInventory(LobbyPlugin lobbyPlugin, Locale locale, IClusterSpigot currentLobby,
	                        List<IClusterSpigot> activeLobbies) {
		super(lobbyPlugin.getLocales(), locale,
				InventoryRows.getRows(InventoryRows.TWO, LOBBY_POSITIONS.length, activeLobbies.size()),
				"inventoryLobbiesTitle");
		this.lobbyPlugin = lobbyPlugin;
		this.currentLobby = currentLobby;

		itemFactory = new LobbiesItemFactory(getTranslator(), locale);
		itemPositions = new HashMap<>();

		calculateItemPositions(activeLobbies);
	}

	@Override
	public void draw() {
		System.out.println("LOBBIES DRAW");
		if (isFirstDraw()) {
			System.out.println("LOBBIES DRAW FIRST");
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
			tempLobbyList.add(lobbyList.get(i));
			if (tempIndex < LOBBY_POSITIONS.length - 1 && i < lobbyList.size() - 1) {
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
		if (currentLobby.getID() == lobby.getID()) {
			setItem(index,
					itemFactory.getCurrentLobbyItem(lobby.getDisplayName(), lobby.getPlayerCount()));
			return;
		}

		setItem(index, itemFactory.getLobbyItem(lobby.getDisplayName(), lobby.getPlayerCount()),
				event -> event.getCloudPlayer().sendToServer(lobby));
	}
}
