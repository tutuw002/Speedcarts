package com.gmail.zant95.Speedcarts;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.zant95.Speedcarts.Storage.MemStorage;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CommandHandler implements CommandExecutor {
	Speedcarts plugin;
	
	public CommandHandler(Speedcarts instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("speedcarts")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (player.hasPermission("speedcarts.edit")) {
					if ((args.length >= 1 && args.length <= 3)
					&& (args[0].equalsIgnoreCase("set")
						|| args[0].equalsIgnoreCase("remove")
						|| args[0].equalsIgnoreCase("clean"))
					) {
						if (!MemStorage.isLoadingStorage) {
							WorldEditPlugin WorldEdit = plugin.getWorldEdit();
							if (WorldEdit.getSelection(player) != null) {
								Location min = WorldEdit.getSelection(player).getMinimumPoint(),
								max = WorldEdit.getSelection(player).getMaximumPoint();
								if (args[0].equalsIgnoreCase("set")) {
									if (args.length >= 2
									&& args[1].length() <= 4
									&& isDouble(args[1])
									&& Double.parseDouble(args[1]) >= 0
									&& Double.parseDouble(args[1]) <= 4) {
										if (args.length == 3 && args[2].equalsIgnoreCase("auto")) {
											RailProcessor.set(min, max, Double.parseDouble(args[1]), true);
											sender.sendMessage("\u00A7e[\u00A7fA\u00A7e] Selected rails are now customized with max speed: \u00A7f" + args[1] + "\u00A7e.");
										} else if (args.length == 2) {
											RailProcessor.set(min, max, Double.parseDouble(args[1]), false);
											sender.sendMessage("\u00A7e[\u00A7fM\u00A7e] Selected rails are now customized with max speed: \u00A7f" + args[1] + "\u00A7e.");
										} else {
											sender.sendMessage("\u00A7cOnly \"auto\" is valid as optional parameter.");
										}
									} else {
										sender.sendMessage("\u00A7cYou must specify a valid number between 0 and 4.");
									}
								} else if (args[0].equalsIgnoreCase("remove")) {
									if (args.length == 1) {
										RailProcessor.remove(min, max);
										sender.sendMessage("\u00A7eSelected rails are now normal rails.");
									} else {
										sender.sendMessage("\u00A7cYou don't need any other argument.");
									}
								} else if (args[0].equalsIgnoreCase("clean")) {
									RailProcessor.clean(min, max);
									sender.sendMessage("\u00A7eThis selection is now clean from ghost-rails.");
								}
							} else {
								sender.sendMessage("\u00A7cYou need to make a cuboid selection first.");
							}
						} else {
							sender.sendMessage("\u00A7cSpeedcarts cannot save because data disk is loading.");
						}
					} else {
						sender.sendMessage("\u00A7cUsage: /sc <\u00A7fset [max speed]\u00A7c|\u00A7fremove\u00A7c|\u00A7fclean\u00A7c>");
					}
				} else {
					sender.sendMessage("\u00A7cYou don't have permission to use this command.");
				}
			} else {
				sender.sendMessage("\u00A7cOnly a player can use this command.");
			}
		}
		return true;
	}
	
	private static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}