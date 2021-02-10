package com.blunix.blunixpermissions.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.models.PermissionManager;
import com.blunix.blunixpermissions.util.Messager;

public class CommandAddToGroup extends PermissionCommand {
	private PermissionManager manager;

	public CommandAddToGroup(PermissionManager manager) {
		this.manager = manager;

		setName("addtogroup");
		setHelpMessage("Adds the specified player to the selected permission group.");
		setPermission("blunixpermissions.addtogroup");
		setUsageMessage("/bp addtogroup <Player> <Group>");
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
		if (manager.getPlayerPermissionGroups(playerName).contains(group)) {
			Messager.sendMessage(sender, "&c&l" + playerName + " &calready is in &l" + group + "&c.");
			return;
		}
		manager.addPlayerToGroup(playerName, group);
		if (Bukkit.getPlayer(playerName) != null)
			manager.setPlayerPermissions(playerName);
		Messager.sendMessage(sender, "&a&l" + playerName + " &ahas been successfully added to &l" + group + "&a.");
	}
}
