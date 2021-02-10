package com.blunix.blunixpermissions.commands;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.util.Messager;

public class CommandHelp extends PermissionCommand {

	public CommandHelp() {
		setName("help");
		setHelpMessage("Displays this list.");
		setPermission("blunixpermissions.help");
		setUsageMessage("/bp help");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Messager.sendHelpMessage(sender);
	}
}
