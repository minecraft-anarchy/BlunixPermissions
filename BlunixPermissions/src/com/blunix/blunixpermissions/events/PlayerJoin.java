package com.blunix.blunixpermissions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blunix.blunixpermissions.BlunixPermissions;

public class PlayerJoin implements Listener {
	private BlunixPermissions plugin;

	public PlayerJoin(BlunixPermissions plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (!player.hasPlayedBefore())
			plugin.getPermissionManager().addPlayerToGroup(playerName, "default");

		plugin.getPermissionManager().setPermissionAttachment(player);
		plugin.getPermissionManager().setPlayerPermissions(player.getName());
	}
}
