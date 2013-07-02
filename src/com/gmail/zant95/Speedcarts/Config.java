package com.gmail.zant95.Speedcarts;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.zant95.Speedcarts.Storage.MemStorage;

public class Config {
	private static FileConfiguration config = MemStorage.plugin.getConfig();
	
	public static void setup() {
		if (config.get("speedcarts.non-block-tool") == null) {
			config.set("speedcarts.non-block-tool", 284);
		}
		
		if (config.get("speedcarts.check-for-updates") == null) {
			config.set("speedcarts.check-for-updates", true);
		}
		
		if (config.get("speedcarts.plugin-metrics") == null) {
			config.set("speedcarts.plugin-metrics", true);
		}
		
		MemStorage.plugin.saveConfig();
	}
}
