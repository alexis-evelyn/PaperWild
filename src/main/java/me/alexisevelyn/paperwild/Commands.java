package me.alexisevelyn.paperwild;

// Bukkit Imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Chunk;
import org.bukkit.ChatColor;
import java.util.function.Consumer;

// Utility Library
import me.alexisevelyn.fourtytwo.Math;

// https://papermc.io/javadocs/paper/1.15/org/bukkit/Chunk.html
// https://papermc.io/javadocs/paper/1.15/org/bukkit/ChunkSnapshot.html

public class Commands implements CommandExecutor {
	private ProcessChunk processChunk = new ProcessChunk();
	
	boolean generateChunk = true;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("wild")) {
			if (sender instanceof Player) {
				
				sender.sendMessage(ChatColor.GOLD + "Looking for Safe Spot to Teleport!!!");
				
				warp((Player) sender);
			} else {
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Only Players Can Run This Command!!!");
				return true;
			}
			
			return true;
		} else if(command.getName().equalsIgnoreCase("lagme")) {
			int loopAmount = Settings.getConfig().getInt("debug.lagme.loopAmount", 100);
			
			if (sender instanceof Player) {
				if(loopAmount == 1) {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Attempting to Lag Server!!! Loading 1 Chunk!!!");
				} else {
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Attempting to Lag Server!!! Loading " + loopAmount + " Chunks!!!");
				}
				
				for(int x = 0; x < loopAmount; x++) {
					warp((Player) sender);
				}
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
		} else if(command.getName().equalsIgnoreCase("reload")) {	
			Settings.getPlugin().reloadConfig();
			
			sender.sendMessage(ChatColor.GOLD + "Finished Reloading Config!!!");
			sender.sendMessage(ChatColor.RED + "Dev Note - Reload Currently Doesn't Work!!! Restart Your Server!!!");
			
			return true;
		}
		
		return false;
	}
	
	private boolean warp(Player player) {
		Consumer<Chunk> chunk = a -> processChunk.process(player, a);
		
		String world = player.getWorld().getName();
		
		if(Settings.getConfig().get("worlds." + world) == null) {
			// Make Read From Default World Config/Nether Config
			player.getWorld().getChunkAtAsync(Math.getRandomNumberInRange(-1000, 1000), Math.getRandomNumberInRange(-1000, 1000), generateChunk, chunk);
			
			return true;
		}
		
		int minX = Settings.getConfig().getInt("worlds." + world + ".minX");
		int minZ = Settings.getConfig().getInt("worlds." + world + ".minZ");
		
		int maxX = Settings.getConfig().getInt("worlds." + world + ".maxX");
		int maxZ = Settings.getConfig().getInt("worlds." + world + ".maxZ");
		
		player.getWorld().getChunkAtAsync(Math.getRandomNumberInRange(minX, maxX), Math.getRandomNumberInRange(minZ, maxZ), generateChunk, chunk);
		
		return true;
		
	}
}
