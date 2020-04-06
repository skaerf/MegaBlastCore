package dev.freaky.megablast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import dev.freaky.megablast.cmds.ApplyCommand;
import dev.freaky.megablast.cmds.CoreCommand;
import dev.freaky.megablast.cmds.HubCommand;

public class Main extends JavaPlugin implements Listener {
	
	// STATICS //
	public static Inventory worldsel = Bukkit.createInventory(null, 9, "Game Selection Menu");
	
	// ITEMS FOR INV //
	static {
		// KITPVP //
		ItemStack KITPVP = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta mkp = KITPVP.getItemMeta();
		mkp.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lKit&4&lPvP"));
		KITPVP.setItemMeta(mkp);
		worldsel.setItem(4, KITPVP);
		// FACTIONS //
		ItemStack factions = new ItemStack(Material.IRON_SWORD);
		ItemMeta mf = factions.getItemMeta();
		mf.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lFactions"));
		factions.setItemMeta(mf);
		worldsel.setItem(0, factions);
		// PLOTS //
		ItemStack plots = new ItemStack(Material.SANDSTONE);
		ItemMeta mp = plots.getItemMeta();
		mp.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lPlots"));
		plots.setItemMeta(mp);
		worldsel.setItem(8, plots);
				
	}
	
	@Override
	public void onEnable() {
		System.out.println("[MegaBlastCORE] Starting");
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("apply").setExecutor(new ApplyCommand());
		this.getCommand("hub").setExecutor(new HubCommand());
		this.getCommand("core").setExecutor(new CoreCommand());
	}
	// EVENTS //
	@EventHandler
	public void invClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(worldsel.getName())) {
			if (clicked.getType() == Material.DIAMOND_SWORD) {
					event.setCancelled(true);
					String sending = this.getConfig().getString("sending");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix") + " " + sending.replaceAll("%game%", "KitPvP")));
					Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "mw move " + player.getName() + " kitpvp");
					if (!(player.hasPermission("core.staff"))) {
						player.setGameMode(GameMode.SURVIVAL);
					}
			}
			else if (clicked.getType() == Material.IRON_SWORD) {
				event.setCancelled(true);
				String sending = this.getConfig().getString("sending");
				sending.replace("%game%", "Factions");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix") + " " + sending.replaceAll("%game%", "Factions")));
				Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "mw move " + player.getName() + " factions");
				if (!(player.hasPermission("core.staff"))) {
					player.setGameMode(GameMode.SURVIVAL);
				}
		}
			else if (clicked.getType() == Material.SANDSTONE) {
				event.setCancelled(true);
				String sending = this.getConfig().getString("sending");
				sending.replace("%game%", "Plots");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix") + " " + sending.replaceAll("%game%", "Plots")));
				Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(), "mw move " + player.getName() + " plotsworld");
				player.setGameMode(GameMode.CREATIVE);
		}
	}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void rightClick(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Game Selection Menu") && player.getItemInHand().getType().equals(Material.NETHER_STAR)) {
				player.openInventory(worldsel);
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.performCommand("spawn");
		player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&b&lWelcome, " + player.getDisplayName()), ChatColor.GREEN + "Enjoy your time here on MegaBlast");
		if (player.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if (!(player.getInventory().contains(Material.NETHER_STAR))) {
				ItemStack compass = new ItemStack(Material.NETHER_STAR, 1);
				ItemMeta cmeta = compass.getItemMeta();
				cmeta.setDisplayName(ChatColor.RED + "Game Selection Menu");
				compass.setItemMeta(cmeta);
				player.getInventory().setItem(4, compass);
			}
		}
		
		
	}
	@EventHandler
	public void worldSwitch(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().contains(Material.NETHER_STAR)) {
			player.getInventory().remove(Material.NETHER_STAR);
		}
		
	}

}
