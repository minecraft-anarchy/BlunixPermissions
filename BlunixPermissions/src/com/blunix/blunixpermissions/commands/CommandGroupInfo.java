package com.blunix.blunixpermissions.commands;

import java.util.Iterator;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandGroupInfo extends PermissionCommand {
	private PermissionManager manager;
	
	public CommandGroupInfo(PermissionManager manager) {
		this.manager = manager;
		
		setName("groupinfo");
		setHelpMessage("Displays the specified group's info.");
		setPermission("blunixpermissions.groupinfo");
		setUsageMessage("/bp groupinfo <GroupName>");
		setArgumentLength(2);
		setUniversalCommand(true);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		String group = args[1];
		if (!manager.groupExists(group)) {
			Messager.sendNonExistentGroupMessage(sender);
			return;
		}
		String finalMessage = "&3&lPermissions: &9\n";
		Iterator<String> permissionIterator = manager.getGroupPermissions(group).iterator();
		while(permissionIterator.hasNext()) {
			String permission = permissionIterator.next();
			finalMessage += permission;
			
			if (permissionIterator.hasNext())
				finalMessage += "\n";
		}
		Messager.sendMessage(sender, finalMessage);
	}
}
