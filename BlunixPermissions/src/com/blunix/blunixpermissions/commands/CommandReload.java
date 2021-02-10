package com.blunix.blunixpermissions.commands;

import org.bukkit.command.CommandSender;

import com.blunix.blunixpermissions.BlunixPermissions;
import com.blunix.blunixpermissions.util.Messager;

public class CommandReload extends PermissionCommand {
	private BlunixPermissions plugin;
	
	public CommandReload(BlunixPermissions plugin) {
		this.plugin = plugin;
		
		setName("reload");
		setHelpMessage("Reloads the plugin's config.");
		setPermission("blunixpermissions.reload");
		setUsageMessage("/bp reload");
		setArgumentLength(1);
		setUniversalCommand(true);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		plugin.reloadConfig();
		Messager.sendMessage(sender, "&aConfig reloaded.");
	}
}
