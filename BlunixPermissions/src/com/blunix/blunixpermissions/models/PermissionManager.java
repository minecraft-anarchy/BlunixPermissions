package com.blunix.blunixpermissions.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.blunix.blunixpermissions.BlunixPermissions;

public class PermissionManager {
	private BlunixPermissions plugin;

	public PermissionManager(BlunixPermissions plugin) {
		this.plugin = plugin;
	}

	public void setPermissionAttachment(Player player) {
		PermissionAttachment attachment = player.addAttachment(plugin);

		plugin.getPermissionAttachments().put(player.getName(), attachment);
	}

	public void setPlayerPermissions(String playerName) {
		if (!plugin.getPermissionAttachments().containsKey(playerName))
			return;

		FileConfiguration permissionsData = plugin.getPermissionsData();
		PermissionAttachment attachment = plugin.getPermissionAttachments().get(playerName);
		ConfigurationSection section = permissionsData.getConfigurationSection("groups");
		if (section == null) {
			Bukkit.getLogger().info("[BlunixPermissions] There was a problem reading the config.yml for permissions.");
			return;
		}
		for (String permissionGroup : getPlayerPermissionGroups(playerName)) {
			List<String> permissions = permissionsData.getStringList("groups." + permissionGroup + ".permissions");
			for (String permission : permissions)
				attachment.setPermission(permission, true);
		}
	}

	public void createPermissionGroup(String name) {
		plugin.getPermissionsData().set("groups." + name + ".permissions", "");
		plugin.savePermissionsData();
	}

	public void deletePermissionGroup(String group) {
		plugin.getPermissionsData().set("groups." + group, null);
		plugin.getPermissionsData().set("player-groups." + group, null);
		for (Player player : Bukkit.getOnlinePlayers())
			updatePlayerPermissions(player.getName());
		
		plugin.savePermissionsData();
	}

	public void addGroupPermission(String group, String permission) {
		List<String> permissions = getGroupPermissions(group);

		permissions.add(permission);
		saveGroupPermissions(group, permissions);
		updateGroupPermissions(group);
	}

	public void removeGroupPermission(String group, String permission) {
		List<String> permissions = getGroupPermissions(group);

		permissions.remove(permission);
		saveGroupPermissions(group, permissions);
		updateGroupPermissions(group);
	}

	private void saveGroupPermissions(String group, List<String> permissions) {
		plugin.getPermissionsData().set("groups." + group + ".permissions", permissions);
		plugin.savePermissionsData();
	}

	public List<String> getGroupPermissions(String group) {
		FileConfiguration permissionData = plugin.getPermissionsData();
		List<String> permissions = permissionData.getStringList("groups." + group + ".permissions");
		return permissions;
	}

	public boolean groupExists(String group) {
		ConfigurationSection section = plugin.getPermissionsData().getConfigurationSection("groups");
		if (section == null) {
			Bukkit.getLogger().info("[BlunixPermissions] There was a problem reading the config.yml for permissions.");
			return false;
		}
		if (section.contains(group))
			return true;

		return false;
	}

	public void addPlayerToGroup(String playerName, String group) {
		List<String> groupPlayers = getPlayersFromGroup(group);
		groupPlayers.add(playerName);
		
		plugin.getPermissionsData().set("player-groups." + group, groupPlayers);
		plugin.savePermissionsData();
		updatePlayerPermissions(playerName);
	}

	public void removePlayerFromGroup(String playerName, String group) {
		List<String> groupPlayers = getPlayersFromGroup(group);
		groupPlayers.remove(playerName);
		
		plugin.getPermissionsData().set("player-groups." + group, groupPlayers);
		plugin.savePermissionsData();
		updatePlayerPermissions(playerName);
	}
	
	public void updatePlayerPermissions(String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		if (player == null)
			return;
		if (plugin.getPermissionAttachments().containsKey(playerName)) {
			PermissionAttachment attachment = plugin.getPermissionAttachments().get(playerName);
			player.removeAttachment(attachment);
			plugin.getPermissionAttachments().remove(playerName);
		}
		setPermissionAttachment(player);
		setPlayerPermissions(playerName);
	}

	public void updateGroupPermissions(String group) {
		for (String playerName : getPlayersFromGroup(group))
			updatePlayerPermissions(playerName);
		
	}
	
	public ArrayList<String> getGroupList() {
		ConfigurationSection section = plugin.getPermissionsData().getConfigurationSection("groups");
		if (section == null)
			return null;
		
		ArrayList<String> groups = new ArrayList<String>();
		section.getKeys(false).forEach(group -> {
			groups.add(group);
		});
		
		return groups;
	}

	public List<String> getPlayersFromGroup(String group) {
		return plugin.getPermissionsData().getStringList("player-groups." + group);
	}

	public List<String> getPlayerPermissionGroups(String playerName) {
		FileConfiguration permissionData = plugin.getPermissionsData();
		ConfigurationSection section = permissionData.getConfigurationSection("player-groups");
		if (section == null) {
			Bukkit.getLogger().info("[BlunixPermissions] There was a problem reading the config.yml for permissions.");
			return null;
		}
		List<String> permissionGroups = new ArrayList<String>();
		section.getKeys(false).forEach(group -> {
			List<String> groupPlayers = permissionData.getStringList("player-groups." + group);
			if (!groupPlayers.contains(playerName))
				return;

			permissionGroups.add(group);
		});
		return permissionGroups;
	}
}
