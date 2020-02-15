package me.alexisevelyn.paperwild;

// Bukkit Imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.ChatColor;

// Java Imports
import java.util.Random;
import java.util.function.Consumer;

// https://papermc.io/javadocs/paper/1.15/org/bukkit/Chunk.html
// https://papermc.io/javadocs/paper/1.15/org/bukkit/ChunkSnapshot.html

public class Commands implements CommandExecutor {
	private ProcessChunk processChunk = new ProcessChunk();
	Random rand = new Random();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("wild")) {
			if (sender instanceof Player) {
				boolean generateChunk = true;
				sender.sendMessage(ChatColor.GOLD + "Looking for Safe Spot to Teleport!!!");
				
				Consumer<Chunk> chunk = a -> processChunk.process((Player) sender, a);
				
				((Player) sender).getWorld().getChunkAtAsync(rand.nextInt(100), rand.nextInt(100), generateChunk, chunk);
			} else {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Only Players Can Run This Command!!!");
				return true;
			}
			
			return true;
		} else if(command.getName().equalsIgnoreCase("lagme")) {
			if (sender instanceof Player) {
				boolean generateChunk = true;
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Attempting to Lag Server!!!");
				
				Consumer<Chunk> chunk = a -> processChunk.process((Player) sender, a);
				
				for(int x = 0; x < 100; x++) {
					((Player) sender).getWorld().getChunkAtAsync(rand.nextInt(100), rand.nextInt(100), generateChunk, chunk);
				}
				
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Finished Loading 100 Chunks!!!");
			} else {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Only Players Can Run This Command!!!");
				return true;
			}
			
			return true;
		} else if(command.getName().equalsIgnoreCase("loadedchunks")) {			
			for(Chunk chunk : ((Player) sender).getWorld().getLoadedChunks()) {
				sender.sendMessage(ChatColor.GOLD + "Loaded Chunk at (" + chunk.getX() + ", " + chunk.getZ() + ")");
			}
			
			sender.sendMessage(ChatColor.GOLD + "Finished Listing Loaded Chunks!!!");
			return true;
		}
		
		return false;
	}
}
