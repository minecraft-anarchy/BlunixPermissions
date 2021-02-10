package com.blunix.blunixpermissions.commands;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandCreateGroup extends PermissionCommand {
	private PermissionManager manager;
	
	public CommandCreateGroup(PermissionManager manager) {
		this.manager = manager;
		
		setName("creategroup");
		setHelpMessage("Creates a new permission group.");
		setPermission("blunixpermissions.creategroup");
		setUsageMessage("/bp creategroup <GroupName>");
		setArgumentLength(2);
		setUniversalCommand(true);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		String group = args[1];
		if (manager.groupExists(group)) {
			Messager.sendMessage(sender, "&cThere already is a group with this name.");
			return;
		}
		manager.createPermissionGroup(group);
		Messager.sendMessage(sender, "&aPermission group &l" + group + " &asuccessfully created.");
	}
}
