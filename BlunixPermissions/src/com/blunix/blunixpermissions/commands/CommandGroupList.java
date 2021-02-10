package com.blunix.blunixpermissions.commands;

import java.util.Iterator;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandGroupList extends PermissionCommand {
	private PermissionManager manager;

	public CommandGroupList(PermissionManager manager) {
		this.manager = manager;

		setName("grouplist");
		setHelpMessage("Displays the full list of permissions groups.");
		setPermission("blunixpermissions.grouplist");
		setUsageMessage("/bp grouplist");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String finalMessage = "&3&lPermission Groups:&9\n";
		Iterator<String> groupIterator = manager.getGroupList().iterator();
		while (groupIterator.hasNext()) {
			String group = groupIterator.next();
			finalMessage += group;
			
			if (groupIterator.hasNext())
				finalMessage += "\n";
		}
		Messager.sendMessage(sender, finalMessage);
	}

}
