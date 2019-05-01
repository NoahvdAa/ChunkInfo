package me.noahvdaa.chunkinfo;

import org.bukkit.plugin.java.JavaPlugin;

import me.noahvdaa.metrics.Metrics;

public class ChunkInfo extends JavaPlugin {
	
	//private FileConfiguration config;
	
	@Override
	public void onEnable() {
		// Enable bStats
		new Metrics(this);
		
		// Copy default config.
		//saveDefaultConfig();
		//config = getConfig();
		
		getCommand("chunk").setExecutor(new ChunkCommand());
	}

}
