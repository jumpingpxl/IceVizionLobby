package de.icevizion.lobby.utils;

import com.google.common.collect.Sets;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 16/11/2019 16:40
 */

public class DoubleJump implements Listener {

	//TODO -> Prevent DoubleJump while in Air

	private static final String DOUBLE_JUMP_PERMISSION = "lobby.doublejump";
	private final Set<UUID> allowedPlayers;

	public DoubleJump() {
		allowedPlayers = Sets.newHashSet();
	}

	public String getDoubleJumpPermission() {
		return DOUBLE_JUMP_PERMISSION;
	}

	public Set<UUID> getAllowedPlayers() {
		return allowedPlayers;
	}
}