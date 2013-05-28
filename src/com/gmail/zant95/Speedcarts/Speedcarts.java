package com.gmail.zant95.Speedcarts;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.zant95.Speedcarts.Listeners.MinecartListener;
import com.gmail.zant95.Speedcarts.Listeners.PlayerInteractListener;
import com.gmail.zant95.Speedcarts.Storage.DiskStorage;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Speedcarts extends JavaPlugin {	
	@Override
	public void onEnable() {	
		//Setup WorldEdit
		if (getWorldEdit() == null) {
			Bukkit.getLogger().info("WorldEdit dependency not found!");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		//Load storage
		if (!this.getDataFolder().exists()) {
			new File(this.getDataFolder().toString()).mkdir();	
		}
		DiskStorage.asyncLoadSpeedrails();
		
		//Implement listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new MinecartListener(this), this);
		pm.registerEvents(new PlayerInteractListener(this), this);
		
		//Implement commands
		getCommand("speedcarts").setExecutor(new CommandHandler(this));
		
		Bukkit.getLogger().info("Speedcarts enabled!");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("Goodbye Speedcarts!");
	}
	
	public WorldEditPlugin getWorldEdit() {
	    Plugin WorldEdit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	    if (WorldEdit == null || !(WorldEdit instanceof WorldEditPlugin)) {
	        return null;
	    }
	    return (WorldEditPlugin)WorldEdit;
	}
}
