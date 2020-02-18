package me.alexisevelyn.paperwild;

import org.bukkit.ChatColor;
// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ProcessChunk {
	private Plugin plugin = Main.getPlugin(Main.class);
	FileConfiguration config = plugin.getConfig();
	
	public void process(Player player, Chunk chunk) {
		int x = 0, z = 0;
		
		ChunkSnapshot snap = chunk.getChunkSnapshot(true, false, false);
		
		Material block;
		boolean unsafe = false; // Do I Really Need This?
		int highest = 256;
		
		// Find Safe Block
		for(int x1 = 0; x1 < 16; x1++) {
			for(int z1 = 0; z1 < 16; z1++) {
				//highest = snap.getHighestBlockYAt(x1, z1); // This method is bugged.
				highest = getHighestBlockYAt(x1, z1, snap);
				
				if(highest == -1) {
					unsafe = true;
					continue;
				}
				
				block = snap.getBlockType(x1, highest, z1);
				
				// Add tall grass?
				if(block == Material.LAVA) {
					unsafe = true;
					continue;
				} else if(block == Material.SWEET_BERRY_BUSH) {
					unsafe = true;
					continue;
				} else if(block == Material.CACTUS) {
					unsafe = true;
					continue;
				} else {
					x = x1;
					z = z1;
					
					unsafe = false;
					break;
				}
			}
		}
		
		if(unsafe) {
			plugin.getLogger().info(ChatColor.RED + "Couldn't Find A Safe Spot to Teleport!!!");
			player.sendMessage(ChatColor.RED + "Couldn't Find A Safe Spot to Teleport!!!");
			return;
		}
		
		Location loc = new Location(player.getWorld(), x + (snap.getX() * 16), highest + 1, z + (snap.getZ() * 16));
		
		plugin.getLogger().info(ChatColor.GOLD + "Teleporting " + player.getName() + " to (" + (x + (snap.getX() * 16)) + ", " + (highest + 1) + ", " + (z + (snap.getZ() * 16)) + ")!!!");
		player.sendMessage(ChatColor.GOLD + "Teleporting to (" + (x + (snap.getX() * 16)) + ", " + (highest + 1) + ", " + (z + (snap.getZ() * 16)) + ")!!!");
		player.teleportAsync(loc, TeleportCause.PLUGIN);
		
		
		PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 255, 20 * 20, false, false, false);
		player.addPotionEffect(resistance);
		
//		chunk.getBlock(0, chunk, 0)
	}
	
	public int getHighestBlockYAt(int x, int z, ChunkSnapshot snap) {
		Material block, blockAbove, blockAboveTwo;
		boolean detectNether = config.getBoolean("debug.detectNether", true);
		
		for(int y = 253; y > 0; y--) {
			block = snap.getBlockType(x, y, z);
			blockAbove = snap.getBlockType(x, y+1, z);
			blockAboveTwo = snap.getBlockType(x, y+2, z);
			
			//plugin.getLogger().info("Block Y: " + y + " | Is Air: " + (block == Material.AIR));
			
			// TODO: Get Safe Nether Location - https://www.spigotmc.org/threads/nether-random-location.398899/#post-3572238
			// "You'd need a for loop going downwards in the y direction, you're looking then for 2 blocks air with 1 block something solid below." - md_5
			
			if(detectNether) {
				if((block != Material.BEDROCK) && (blockAbove == Material.AIR) && (blockAboveTwo == Material.AIR)) {
					plugin.getLogger().info("Block Y: " + y + " | Block ID: " + block.toString());
					plugin.getLogger().info("Block Above + 1 - Y: " + (y+1) + " | Block ID: " + blockAbove.toString());
					plugin.getLogger().info("Block Above + 2 - Y: " + (y+2) + " | Block ID: " + blockAboveTwo.toString());
					
					return y;
				}
			} else {
				if(block != Material.AIR) {
//					plugin.getLogger().info("Block Y: " + y + " | Is Air: " + (block == Material.AIR));
//					plugin.getLogger().info("Block Y: " + y + " | Block ID: " + block.toString());
					
					return y;
				}
			}
		}
		
		return -1;
	}
}
