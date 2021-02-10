package com.blunix.blunixpermissions.util;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.BlunixPermissions;
import com.blunix.blunixpermissions.commands.PermissionCommand;

public class Messager {

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void sendHelpMessage(CommandSender sender) {
		BlunixPermissions plugin = BlunixPermissions.getPlugin(BlunixPermissions.class);
		String finalMessage = "&6&lCommands\n";

		Iterator<PermissionCommand> iterator = plugin.getSubcommands().values().iterator();

		while (iterator.hasNext()) {
			PermissionCommand subcommand = iterator.next();
			if (!sender.hasPermission(subcommand.getPermission()))
				continue;

			finalMessage += "&a" + subcommand.getUsageMessage() + " &6- &3" + subcommand.getHelpMessage();
			if (iterator.hasNext())
				finalMessage += "\n";

		}

		Messager.sendMessage(sender, finalMessage);
	}

	public static void sendNoPermissionMessage(CommandSender sender) {
		sendMessage(sender, "&cYou do not have permissions to use this command!");
	}
	
	public static void sendNonExistentGroupMessage(CommandSender sender) {
		sendMessage(sender, "&cThere are no permission groups under this name.");
	}
}
