package com.blunix.blunixpermissions.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandAddGroupPermission extends PermissionCommand {
	private PermissionManager manager;

	public CommandAddGroupPermission(PermissionManager manager) {
		this.manager = manager;

		setName("addgrouppermission");
		setHelpMessage("Adds the specified permission to the selected group.");
		setPermission("blunixpermissions.addgrouppermission");
		setUsageMessage("/bp addgrouppermission <GroupName> <Permission>");
		setArgumentLength(3);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String group = args[1];
		String permission = args[2];
		if (!manager.groupExists(group)) {
			Messager.sendNonExistentGroupMessage(sender);
			return;
		}
		List<String> permissions = manager.getGroupPermissions(group);
		if (permissions.contains(permission)) {
			Messager.sendMessage(sender, "&cThis group already has the permission &l" + permission + "&c.");
			return;
		}
		manager.addGroupPermission(group, permission);
		Messager.sendMessage(sender,
				"&aPermission &l" + permission + " &ahas been successfully added to &l" + group + "&a.");
	}
}
