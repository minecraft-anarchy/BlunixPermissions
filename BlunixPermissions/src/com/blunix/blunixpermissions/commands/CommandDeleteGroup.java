package com.blunix.blunixpermissions.commands;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandDeleteGroup extends PermissionCommand {
	private PermissionManager manager;
	
	public CommandDeleteGroup(PermissionManager manager) {
		this.manager = manager;
		
		setName("deletegroup");
		setHelpMessage("Deletes the specified permission group.");
		setPermission("blunixpermissions.deletegroup");
		setUsageMessage("/bp deletegroup <GroupName>");
		setArgumentLength(2);
		setUniversalCommand(true);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		String group = args[1];
		if (!manager.groupExists(group)) {
			Messager.sendMessage(sender, "&cThere isn't any permission group under this name.");
			return;
		}
		manager.deletePermissionGroup(group);
		Messager.sendMessage(sender, "&aPermission group &l" + group + " &ahas been successfully deleted.");
	}
}
