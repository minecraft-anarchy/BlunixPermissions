package com.blunix.blunixpermissions.commands;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandRemoveFromGroup extends PermissionCommand {
	private PermissionManager manager;

	public CommandRemoveFromGroup(PermissionManager manager) {
		this.manager = manager;

		setName("removefromgroup");
		setHelpMessage("Removes the specified player from the selected permission group.");
		setPermission("blunixpermissions.removefromgroup");
		setUsageMessage("/bp removefromgroup <Player> <GroupName>");
		setArgumentLength(3);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String playerName = args[1];
		String group = args[2];
		if (!manager.groupExists(group)) {
			Messager.sendNonExistentGroupMessage(sender);
			return;
		}
		if (!manager.getPlayerPermissionGroups(playerName).contains(group)) {
			Messager.sendMessage(sender, "&c&l" + group + " &cdoesn't belong to &l" + group + "&c.");
			return;
		}
		manager.removePlayerFromGroup(playerName, group);
		Messager.sendMessage(sender, "&a&l" + playerName + "&a has been successfully removed from &l" + group + "&a.");
	}
}
