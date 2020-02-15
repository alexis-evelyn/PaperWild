package me.alexisevelyn.paperwild;

import org.bukkit.ChatColor;
// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ProcessChunk {
	private Plugin plugin = Main.getPlugin(Main.class);
	
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
			player.sendMessage(ChatColor.RED + "Couldn't Find A Safe Spot to Teleport!!!");
			return;
		}
		
		Location loc = new Location(player.getWorld(), x + (snap.getX() * 16), highest + 1, z + (snap.getZ() * 16));
		
		player.sendMessage(ChatColor.GOLD + "Teleporting to (" + (x + (snap.getX() * 16)) + ", " + (highest + 1) + ", " + (z + (snap.getZ() * 16)) + ")!!!");
		player.teleportAsync(loc, TeleportCause.PLUGIN);
		
		
		PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 255, 20 * 20, false, false, false);
		player.addPotionEffect(resistance);
		
//		chunk.getBlock(0, chunk, 0)
	}
	
	public int getHighestBlockYAt(int x, int z, ChunkSnapshot snap) {
		Material block;
		
		for(int y = 255; y > 0; y--) {
			block = snap.getBlockType(x, y, z);
			
			//plugin.getLogger().info("Block Y: " + y + " | Is Air: " + (block == Material.AIR));
			
			if(block != Material.AIR) { // Add Tallgrass?
				plugin.getLogger().info("Block Y: " + y + " | Is Air: " + (block == Material.AIR));
				plugin.getLogger().info("Block Y: " + y + " | Block ID: " + block.toString());
				
				return y;
			}
		}
		
		return -1;
	}
}