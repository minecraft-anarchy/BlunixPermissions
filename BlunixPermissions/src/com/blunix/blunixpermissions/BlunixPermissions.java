package com.blunix.blunixpermissions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.blunix.blunixpermissions.commands.CommandAddGroupPermission;
import com.blunix.blunixpermissions.commands.CommandAddToGroup;
import com.blunix.blunixpermissions.commands.CommandCompleter;
import com.blunix.blunixpermissions.commands.CommandCreateGroup;
import com.blunix.blunixpermissions.commands.CommandDeleteGroup;
import com.blunix.blunixpermissions.commands.CommandGroupInfo;
import com.blunix.blunixpermissions.commands.CommandGroupList;
import com.blunix.blunixpermissions.commands.CommandHelp;
import com.blunix.blunixpermissions.commands.CommandPlayerGroups;
import com.blunix.blunixpermissions.commands.CommandRemoveFromGroup;
import com.blunix.blunixpermissions.commands.CommandRemoveGroupPermission;
import com.blunix.blunixpermissions.commands.CommandRunner;
import com.blunix.blunixpermissions.commands.PermissionCommand;
import com.blunix.blunixpermissions.events.PlayerJoin;
import com.blunix.blunixpermissions.events.PlayerQuit;
import com.blunix.blunixpermissions.files.PermissionsFile;
import com.blunix.blunixpermissions.models.PermissionManager;

public class BlunixPermissions extends JavaPlugin {
	private PermissionsFile permissionsData;
	private PermissionManager permissionManager;

	private Map<String, PermissionCommand> subcommands = new HashMap<String, PermissionCommand>();
	private Map<String, PermissionAttachment> permissionAttachments = new HashMap<String, PermissionAttachment>();

	@Override
	public void onEnable() {
		permissionsData = new PermissionsFile(this);
		permissionManager = new PermissionManager(this);

		getCommand("blunixpermissions").setExecutor(new CommandRunner(this));
		getCommand("blunixpermissions").setTabCompleter(new CommandCompleter(this));
		subcommands.put("help", new CommandHelp());
//		subcommands.put("reload", new CommandReload(this));
		subcommands.put("addgrouppermission", new CommandAddGroupPermission(permissionManager));
		subcommands.put("addtogroup", new CommandAddToGroup(permissionManager));
		subcommands.put("creategroup", new CommandCreateGroup(permissionManager));
		subcommands.put("deletegroup", new CommandDeleteGroup(permissionManager));
		subcommands.put("groupinfo", new CommandGroupInfo(permissionManager));
		subcommands.put("grouplist", new CommandGroupList(permissionManager));
		subcommands.put("playergroups", new CommandPlayerGroups(permissionManager));
		subcommands.put("removefromgroup", new CommandRemoveFromGroup(permissionManager));
		subcommands.put("removegrouppermission", new CommandRemoveGroupPermission(permissionManager));

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerQuit(this), this);

		for (Player player : Bukkit.getOnlinePlayers()) {
			permissionManager.setPermissionAttachment(player);
			permissionManager.setPlayerPermissions(player.getName());
		}

	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			PermissionAttachment attachment = getPermissionAttachments().get(player.getName());
			player.removeAttachment(attachment);
		}
		permissionAttachments.clear();
	}

	public FileConfiguration getPermissionsData() {
		return permissionsData.getConfig();
	}

	public void savePermissionsData() {
		permissionsData.saveConfig();
	}

	public Map<String, PermissionCommand> getSubcommands() {
		return subcommands;
	}

	public Map<String, PermissionAttachment> getPermissionAttachments() {
		return permissionAttachments;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}
}
