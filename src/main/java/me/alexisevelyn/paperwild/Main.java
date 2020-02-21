package me.alexisevelyn.paperwild;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

// Utility Libraries
import me.alexisevelyn.fourtytwo.debug.*;

public class Main extends JavaPlugin implements Listener {	
	Debug debug = new Debug();

	// Event Listeners

	@Override
    public void onEnable() {
		// TODO: Hook Into GriefPrevention and Worldguard
		// ...
		Settings.getConfig().addDefault("worldguard_supported", false);
		Settings.getConfig().addDefault("griefprevention_supported", false);
		
		Settings.getConfig().addDefault("debug.verbosity", debug.getVerbosity().getValue());
		
		Settings.getConfig().options().copyDefaults(true);
        saveConfig();
        
        
        // Get Current Verbosity Values
        debug.setVerbosity(Settings.getConfig().getInt("debug.verbosity"));
        
		
		// Register Bukkit Listeners (For Event Handlers)
		Bukkit.getPluginManager().registerEvents(this, this);
		
		// Register Commands
		this.getCommand("wild").setExecutor(new Commands());
		this.getCommand("lagme").setExecutor(new Commands());
		this.getCommand("loadedchunks").setExecutor(new Commands());
		this.getCommand("reload").setExecutor(new Commands());

		// Announce Successful Start
		getLogger().info("Paper Wild has successfully started!!!");
	}

	@Override
    public void onDisable() {
		getLogger().info("Thank you for using Paper Wild!!!");
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onChunkLoad(ChunkLoadEvent event) {		
		if(debug.isVerboseEnough(Verbosity.superverbose)) {
			getLogger().info(ChatColor.BLUE + "<onChunkLoad> New Chunk: " + event.isNewChunk());
			getLogger().info(ChatColor.BLUE + "<onChunkLoad> Asynchronous: " + event.isAsynchronous());
			
			getLogger().info(ChatColor.BLUE + "<onChunkLoad> World: " + event.getWorld());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onChunkUnload(ChunkUnloadEvent event) {
		if(debug.isVerboseEnough(Verbosity.superverbose)) {
			getLogger().info(ChatColor.GREEN + "<onChunkUnload> Is Saving Chunk: " + event.isSaveChunk());
			getLogger().info(ChatColor.GREEN + "<onChunkUnload> Asynchronous: " + event.isAsynchronous());
			
			getLogger().info(ChatColor.GREEN + "<onChunkUnload> World: " + event.getWorld());
		}
	}
}
