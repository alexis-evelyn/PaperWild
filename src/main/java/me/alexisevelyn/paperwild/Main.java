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
import me.alexisevelyn.fourtytwo.Verbosity;

public class Main extends JavaPlugin implements Listener {
	private boolean debug = true;

	// Getters and Setters

	public boolean isDebug() {
		return this.debug;
	}
	
	public boolean isVerboseEnough(Verbosity superverbose) {
		// TODO: Have functions called this with a requested verbosity and 
		// return true if verbosity is that value or higher
		
		
		return isDebug();
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	// Event Listeners

	@Override
    public void onEnable() {
		// TODO: Hook Into GriefPrevention and Worldguard
		// ...
		
		// Register Bukkit Listeners (For Event Handlers)
		Bukkit.getPluginManager().registerEvents(this, this);
		
		// Register Commands
		this.getCommand("wild").setExecutor(new Commands());
		this.getCommand("lagme").setExecutor(new Commands());
		this.getCommand("loadedchunks").setExecutor(new Commands());

		// Announce Successful Start
		getLogger().info("Paper Wild has successfully started!!!");
	}

	@Override
    public void onDisable() {
		getLogger().info("Thank you for using Paper Wild!!!");
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onChunkLoad(ChunkLoadEvent event) {
		if(isVerboseEnough(Verbosity.superverbose)) {
			getLogger().info(ChatColor.BLUE + "<onChunkLoad> New Chunk: " + event.isNewChunk());
			getLogger().info(ChatColor.BLUE + "<onChunkLoad> Asynchronous: " + event.isAsynchronous());
			
			getLogger().info(ChatColor.BLUE + "<onChunkLoad> World: " + event.getWorld());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onChunkUnload(ChunkUnloadEvent event) {
		if(isVerboseEnough(Verbosity.superverbose)) {
			getLogger().info(ChatColor.GREEN + "<onChunkUnload> Is Saving Chunk: " + event.isSaveChunk());
			getLogger().info(ChatColor.GREEN + "<onChunkUnload> Asynchronous: " + event.isAsynchronous());
			
			getLogger().info(ChatColor.GREEN + "<onChunkUnload> World: " + event.getWorld());
		}
	}
}
