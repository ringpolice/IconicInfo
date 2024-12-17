package com.darignio.plugin;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin implements Listener {
	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;

	private void setupAll() {
		setupChat();
		setupEconomy();
		setupPermissions();
		loadConfig();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
				.getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = (Chat) chatProvider.getProvider();
		}
		return chat != null;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}

	public void onEnable() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			ConsoleCommandSender c = Bukkit.getConsoleSender();
			c.sendMessage(color("&6cVAULT NOT FOUND"));
		} else {
			getServer().getPluginManager().registerEvents(this, this);
			getLogger().info("STARTED");
			getLogger().info("By DaRignio");
			setupAll();
		}
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void onDisable() {
		getLogger().info("Disabled!");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String cmd = command.getName();
		Player p = (Player) sender;
		if (cmd.equalsIgnoreCase("pinfo")) {
			if (p.hasPermission("ic.info") || p.isOp()) {
				String n = p.getName();
				Location l = p.getLocation();
				String c = getConfig().getString("line").replace("|", "|");
				String c1 = getConfig().getString("colon");
				String c2 = getConfig().getString("main");
				String c3 = getConfig().getString("secondary");
				if (args.length == 0) {
					p.sendMessage(color(c));
					p.sendMessage(color(c2 + "Name" + c1 + ": " + c3 + n));
					p.sendMessage(color(c2 + "Display Name" + c1 + ": " + c3 + p.getDisplayName()));
					p.sendMessage(color(c2 + "Rank" + c1 + ": " + c3
							+ chat.getGroupPrefix(p.getWorld(), chat.getPrimaryGroup(p).toString())));
					p.sendMessage(color(c2 + "UUID" + c1 + ": " + c3 + p.getUniqueId()));
					p.sendMessage(color(c2 + "IP" + c1 + ": " + c3 + p.getAddress()));
					p.sendMessage(color(c2 + "OP" + c1 + ": " + c3 + p.isOp()));
					p.sendMessage(color(c2 + "Flying" + c1 + ": " + c3 + p.isFlying()));
					p.sendMessage(color(c2 + "Whitelisted" + c1 + ": " + c3 + p.isWhitelisted()));
					p.sendMessage(color(c2 + "Gamemode" + c1 + ": " + c3 + p.getGameMode()));
					p.sendMessage(color(c2 + "Health" + c1 + ": " + c3 + p.getHealth() + "/" + p.getHealthScale()));
					p.sendMessage(color(c2 + "Hunger" + c1 + ": " + c3 + p.getFoodLevel() + "/20 (+" + p.getSaturation()
							+ " Saturation)"));
					p.sendMessage(color(c2 + "XP" + c1 + ": " + c3 + p.getExp() + " (" + p.getExpToLevel() + "L)"));
					p.sendMessage(color(c2 + "Balance" + c1 + ": " + c3 + economy.getBalance(p)));
					p.sendMessage(color(c2 + "World" + c1 + ": " + c3 + l.getWorld().getName()));
					p.sendMessage(color(c2 + "Can /pinfo" + c1 + ": " + c3 + p.hasPermission("ic.info")));
					p.sendMessage(color(c));
				} else if (args.length == 1) {
					Player p1 = Bukkit.getPlayer(args[0]);
					String n1 = p1.getPlayer().getName();
					Location l1 = p1.getPlayer().getLocation();
					p.sendMessage(color(c));
					p.sendMessage(color(c2 + "Name" + c1 + ": " + c3 + n1));
					p.sendMessage(color(c2 + "Display Name" + c1 + ": " + c3 + p1.getPlayer().getDisplayName()));
					p.sendMessage(color(c2 + "Rank" + c1 + ": " + c3
							+ chat.getGroupPrefix(p1.getWorld(), chat.getPrimaryGroup(p1))));
					p.sendMessage(color(c2 + "UUID" + c1 + ": " + c3 + p1.getUniqueId()));
					p.sendMessage(color(c2 + "IP" + c1 + ": " + c3 + p1.getPlayer().getAddress()));
					p.sendMessage(color(c2 + "OP" + c1 + ": " + c3 + p1.isOp()));
					p.sendMessage(color(c2 + "Flying" + c1 + ": " + c3 + p1.getPlayer().isFlying()));
					p.sendMessage(color(c2 + "Whitelisted" + c1 + ": " + c3 + p1.getPlayer().isWhitelisted()));
					p.sendMessage(color(c2 + "Gamemode" + c1 + ": " + c3 + p1.getPlayer().getGameMode()));
					p.sendMessage(color(c2 + "Health" + c1 + ": " + c3 + p1.getPlayer().getHealth() + "/"
							+ p1.getPlayer().getHealthScale()));
					p.sendMessage(color(c2 + "Hunger" + c1 + ": " + c3 + p1.getPlayer().getFoodLevel() + "/20 (+"
							+ p1.getPlayer().getSaturation() + " Saturation)"));
					p.sendMessage(color(c2 + "XP" + c1 + ": " + c3 + p1.getPlayer().getExp() + " ("
							+ p1.getPlayer().getExpToLevel() + "L)"));
					p.sendMessage(color(c2 + "Balance" + c1 + ": " + c3 + economy.getBalance(p1)));
					p.sendMessage(color(c2 + "World" + c1 + ": " + c3 + l1.getWorld().getName()));
					p.sendMessage(color(c2 + "Can /pinfo" + c1 + ": " + c3 + p1.getPlayer().hasPermission("ic.info")));
					p.sendMessage(color(c));
				}
			} else {
				p.sendMessage(color("&4&oNo permission"));
			}
		}
		if (cmd.equalsIgnoreCase("pinfo-reload")) {
			reloadConfig();
			saveConfig();
			sender.sendMessage(color("&cReloading &cconfig.yml"));
			sender.sendMessage(color("&aReloaded!"));
		}
		return false;
	}

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String getColor(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
