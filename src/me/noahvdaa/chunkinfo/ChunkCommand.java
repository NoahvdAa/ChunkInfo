package me.noahvdaa.chunkinfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkCommand implements CommandExecutor {
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("chunk")) {
			if(sender instanceof Player && !((Player) sender).hasPermission("chunkinfo.use")) {
				sender.sendMessage(ChatColor.RED + "You don't have enough permissions to use this command.");
				return true;
			}
			World world;
			if(args.length != 3 && !(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Console Usage: /chunk <x> <z> <world>");
				return true;
			}
			if(args.length > 3){
				sender.sendMessage(ChatColor.RED + "Usage: /chunk [x] [z] [world]");
				return true;
			}else if(args.length == 3) {
				world = Bukkit.getServer().getWorld(args[2]);
				if(world == null) {
					sender.sendMessage(ChatColor.RED + "That world doesn't exist.");
					return true;
				}
			}else if(args.length == 2) {
				world = ((Player) sender).getWorld();
			}else { 
				world = ((Player) sender).getWorld();
			}
			boolean isLoaded;
			Chunk chunk;
			if(args.length == 0 || args.length == 1) {
				// Player is already in chunk.
				isLoaded = true;
				chunk = ((Player) sender).getLocation().getChunk();
			}else if(!args[0].matches("-?\\d+") || !args[1].matches("-?\\d+")) {
				// Invalid chunk coordinates.
				sender.sendMessage(ChatColor.RED + "Invalid chunk coordinates.");
				return true;
			}else {
				int x = Integer.parseInt(args[0]);
				int z = Integer.parseInt(args[1]);
				// .getChunkAt auto-loads the chunk so we check if it is loaded before using .getChunkAt.
				isLoaded = world.isChunkLoaded(x,z);
				chunk = world.getChunkAt(x, z);
			}
			
			if(chunk == null) {
				sender.sendMessage(ChatColor.RED + "Can't find that chunk. Did you use chunk coordinates?");
				return true;
			}
			
			sender.sendMessage(ChatColor.GOLD + "----- " + ChatColor.BLUE + world.getName() + ", " + chunk.getX() + ", " + chunk.getZ() + ChatColor.GOLD + " -----");
			sender.sendMessage(ChatColor.GOLD + "Entities: " + ChatColor.BLUE + chunk.getEntities().length);
			sender.sendMessage(ChatColor.GOLD + "Force Loaded: " + booleanString(chunk.isForceLoaded(), ChatColor.GREEN + "YES", ChatColor.RED + "NO"));
			sender.sendMessage(ChatColor.GOLD + "Loaded: " + booleanString(isLoaded, ChatColor.GREEN + "YES", ChatColor.RED + "NO"));
			sender.sendMessage(ChatColor.GOLD + "Slime Chunk: " + booleanString(chunk.isSlimeChunk(), ChatColor.GREEN + "YES", ChatColor.RED + "NO"));
		}
        return true;
    }

	private String booleanString(boolean check, String ifTrue, String ifFalse) {
		if(!check) return ifFalse;
		return ifTrue;
	}
}
