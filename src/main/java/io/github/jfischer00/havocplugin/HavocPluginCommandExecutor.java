package io.github.jfischer00.havocplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HavocPluginCommandExecutor implements CommandExecutor {
	public HavocPluginCommandExecutor(HavocPlugin plugin) {
	}

	public void tellConsole(CommandSender sender, String message) {
		sender.sendMessage(message);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("havoc")) {
			if (args.length != 2) {
				tellConsole(sender, ChatColor.RED
						+ "Invalid number of arguments.");
				return false;
			}

			boolean playerFound = false;

			for (Player target : Bukkit.getServer().getOnlinePlayers()) {
				if (target.getName().equalsIgnoreCase(args[0])) {
					tellConsole(sender, ChatColor.RED + "Target obtained.");
					playerFound = true;

					if (args[1].equalsIgnoreCase("ghasts")) {
						Bukkit.getServer().dispatchCommand(
								Bukkit.getServer().getConsoleSender(),
								"spawnmob ghast,ghast 5 " + target.getName());
						target.sendMessage(ChatColor.YELLOW
								+ "Beware of fireballs!");
						return true;
					} else if (args[1].equalsIgnoreCase("creepers")) {
						Bukkit.getServer().dispatchCommand(
								Bukkit.getServer().getConsoleSender(),
								"spawnmob creeper,creeper 5 "
										+ target.getName());
						target.sendMessage(ChatColor.YELLOW
								+ "Watch out for explosions!!");
						return true;
					} else if (args[1].equalsIgnoreCase("fire")) {
						target.setFireTicks(1200);
						target.sendMessage(ChatColor.YELLOW
								+ "Ouch, Hot! Better find some water.");
						return true;
					} else if (args[1].equalsIgnoreCase("kill")) {
						target.getWorld().createExplosion(target.getLocation(),
								4F);
						target.setHealth(0);
						target.sendMessage(ChatColor.YELLOW
								+ "BOOM! What happened?");
						return true;
					} else if (args[1].equalsIgnoreCase("lightning")) {
						target.getWorld().strikeLightning(target.getLocation());
						target.sendMessage(ChatColor.YELLOW
								+ "FLASH! That's hot.");
						return true;
					} else if (args[1].equalsIgnoreCase("hide")) {
						for (Player hidden : Bukkit.getOnlinePlayers()) {
							if (!hidden.getName().equalsIgnoreCase(args[0])) {
								target.hidePlayer(hidden);
								target.sendMessage(ChatColor.YELLOW
										+ "Where did everyone go?");
								return true;
							}
						}
					} else if (args[1].equalsIgnoreCase("show")) {
						for (Player hidden : Bukkit.getOnlinePlayers()) {
							if (!hidden.getName().equalsIgnoreCase(args[0])) {
								target.showPlayer(hidden);
								target.sendMessage(ChatColor.YELLOW
										+ "Everyone has now reappeared!");
								return true;
							}
						}
					} else {
						tellConsole(sender, ChatColor.RED + args[1]
								+ " is not a type of havoc.");
						return false;
					}
				}
			}

			if (!playerFound) {
				tellConsole(sender, ChatColor.RED + args[0] + " is not online.");
				return true;
			}
		}
		return false;
	}
}
