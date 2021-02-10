package com.blunix.blunixpermissions.commands;

import java.util.Iterator;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandPlayerGroups extends PermissionCommand {
	private PermissionManager manager;

	public CommandPlayerGroups(PermissionManager manager) {
		this.manager = manager;

		setName("playergroups");
		setHelpMessage("Displays the groups the specified player belongs to.");
		setPermission("blunixpermissions.playergroups");
		setUsageMessage("/bp playergroups <Player>");
		setArgumentLength(2);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String playerName = args[1];
		if (manager.getPlayerPermissionGroups(playerName).isEmpty()) {
			Messager.sendMessage(sender, "&cThis player doesn't belong to any group yet.");
			return;
		}
		String finalMessage = "&3&l" + playerName + "'s Permission Groups:&9\n";
		Iterator<String> groupIterator = manager.getPlayerPermissionGroups(playerName).iterator();
		while (groupIterator.hasNext()) {
			String group = groupIterator.next();
			finalMessage += group;
			
			if (groupIterator.hasNext())
				finalMessage += "\n";
		}
		Messager.sendMessage(sender, finalMessage);
	}
}
