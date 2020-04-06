package dev.freaky.megablast.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.freaky.megablast.Main;

public class ApplyCommand implements CommandExecutor {
	
	FileConfiguration conf = Main.getPlugin(Main.class).getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', conf.getString("prefix") + " " + conf.getString("applymessage")));
		return true;
	}

}
