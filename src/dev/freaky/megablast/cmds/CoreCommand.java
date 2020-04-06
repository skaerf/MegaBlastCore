package dev.freaky.megablast.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.freaky.megablast.Main;

public class CoreCommand implements CommandExecutor {
	
	FileConfiguration conf = Main.getPlugin(Main.class).getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (!(player.hasPermission("core.command"))) {
			player.sendMessage(conf.getString("prefix") + " " + conf.getString("noperm"));
		}
		else {
			if (args.length == 0) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', conf.getString("prefix") + " " + "&a&lUsage: /core reload"));
			}
			else {
				if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
					Main.getPlugin(Main.class).reloadConfig();
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', conf.getString("prefix") + " " + conf.getString("reload")));
				}
				else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', conf.getString("prefix") + " " + "&a&lUsage: /core reload"));
				}
			}
		}
		return true;
	}

}
