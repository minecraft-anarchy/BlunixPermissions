package com.blunix.blunixpermissions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.blunix.blunixpermissions.BlunixPermissions;

public class PlayerQuit implements Listener {
	private BlunixPermissions plugin;

	public PlayerQuit(BlunixPermissions plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.getPermissionAttachments().remove(player.getName());
	}
}
