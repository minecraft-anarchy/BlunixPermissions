package com.blunix.blunixpermissions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.blunixpermissions.BlunixPermissions;
import com.blunix.blunixpermissions.util.Messager;

public class CommandRunner implements CommandExecutor {
	private BlunixPermissions plugin;

	public CommandRunner(BlunixPermissions instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("blunixpermissions"))
			return true;
		if (args.length == 0) {
			Messager.sendHelpMessage(sender);
			return true;
		}
		if (!plugin.getSubcommands().containsKey(args[0].toLowerCase())) {
			Messager.sendMessage(sender, "&cUnknown command. Type &l/bp help &cto see the full command list.");
			return true;
		}

		PermissionCommand subcommand = plugin.getSubcommands().get(args[0].toLowerCase());
		if (!sender.hasPermission(subcommand.getPermission())) {
			Messager.sendNoPermissionMessage(sender);
			return true;
		}
		if (subcommand.isPlayerCommand && !(sender instanceof Player)) {
			Messager.sendMessage(sender, "&cNot available for consoles.");
			return true;
		}
		if (subcommand.isConsoleCommand && sender instanceof Player) {
			Messager.sendMessage(sender, "&cNot available for players.");
			return true;
		}
		if (args.length < subcommand.getArgumentLength()) {
			Messager.sendMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
			return true;
		}
		subcommand.execute(sender, args);
		return true;
	}
}
