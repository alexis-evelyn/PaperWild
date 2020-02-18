package me.alexisevelyn.paperwild;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {
	private static Plugin plugin = Main.getPlugin(Main.class);
	private static FileConfiguration config = plugin.getConfig();
	
	public static FileConfiguration getConfig() {
		return Settings.config;	
	}
	
	public static Plugin getPlugin() {
		return Settings.plugin;	
	}
}
