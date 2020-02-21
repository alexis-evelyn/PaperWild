package me.alexisevelyn.paperwild;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/** Broken Implementation of Attempt to Make Reloadable Settings
 * @author Alexis Evelyn
 * @author alexisevelyn.me
 * @version 0.0.1-Snapshot
 * @since 0.0.1-Snapshot
*/
public class Settings {
	/** Instance of Main Class */
	private static Plugin plugin = Main.getPlugin(Main.class);
	
	/** Instance of Config */
	private static FileConfiguration config = plugin.getConfig();
	
	/** Retrieve Current Config
	 * @return Instance of Config
	*/
	public static FileConfiguration getConfig() {
		return Settings.config;	
	}
	
	/** Retrieve Instance of Main Class
	 * @return Instance of Main Class
	*/
	public static Plugin getPlugin() {
		return Settings.plugin;	
	}
}
