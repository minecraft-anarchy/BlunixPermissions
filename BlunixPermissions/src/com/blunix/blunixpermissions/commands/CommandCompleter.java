package com.blunix.blunixpermissions.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.blunix.blunixpermissions.BlunixPermissions;

public class CommandCompleter implements TabCompleter {
	private BlunixPermissions plugin;

	public CommandCompleter(BlunixPermissions plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> arguments = new ArrayList<String>();
		if (arguments.isEmpty()) {
			for (PermissionCommand subcommand : plugin.getSubcommands().values()) {
				if (!sender.hasPermission(subcommand.getPermission()))
					continue;

				arguments.add(subcommand.getName());
			}
		}
		String arg = args[0];
		if (args.length >= 0 && args.length < 2)
			return getCompletion(arguments, args, 0);

		else if (args.length >= 1 && args.length < 3) {
			if (arg.equalsIgnoreCase("addtogroup") || arg.equalsIgnoreCase("removefromgroup")
					|| arg.equalsIgnoreCase("playergroups"))
				return null;

			else if (arg.equalsIgnoreCase("addgrouppermission") || arg.equalsIgnoreCase("deletegroup")
					|| arg.equalsIgnoreCase("removegrouppermission") || arg.equalsIgnoreCase("groupinfo"))
				return getCompletion(plugin.getPermissionManager().getGroupList(), args, 1);

		} else if (args.length >= 2 && args.length < 4) {
			if (arg.equalsIgnoreCase("addtogroup") || arg.equalsIgnoreCase("removefromgroup"))
				return getCompletion(plugin.getPermissionManager().getGroupList(), args, 2);
		}
		arguments.clear();
		return arguments;
	}

	private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
		ArrayList<String> results = new ArrayList<String>();
		for (String argument : arguments) {
			if (!argument.toLowerCase().startsWith(args[index].toLowerCase()))
				continue;

			results.add(argument);
		}
		return results;
	}
}
