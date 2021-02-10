package com.blunix.blunixpermissions.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandRemoveGroupPermission extends PermissionCommand {
	private PermissionManager manager;

	public CommandRemoveGroupPermission(PermissionManager manager) {
		this.manager = manager;

		setName("removegrouppermission");
		setHelpMessage("Removes the specified permission from the selected permission group.");
		setPermission("blunixpermissions.removegrouppermission");
		setUsageMessage("/bp removegrouppermission <GroupName> <Permission>");
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
		if (!permissions.contains(permission)) {
			Messager.sendMessage(sender, "&cThis group doesn't have permission &l" + permission + "&c.");
			return;
		}
		manager.removeGroupPermission(group, permission);
		Messager.sendMessage(sender,
				"&aPermission &l" + permission + " &ahas been successfully removed from &l" + group + "&a.");
	}
}
