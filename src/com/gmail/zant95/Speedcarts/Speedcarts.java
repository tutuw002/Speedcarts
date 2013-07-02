package com.gmail.zant95.Speedcarts;

import java.io.File;

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
			this.getLogger().severe("WorldEdit dependency not found!");
			this.getServer().getPluginManager().disablePlugin(this);
		}
		
		//Setup plugin folder
		if (!this.getDataFolder().exists()) {
			this.getLogger().info("Creating plugin folder...");
			new File(this.getDataFolder().toString()).mkdir();	
		}
		
		//Setup config
		Config.setup();
		
		//Load storage
		DiskStorage.asyncLoadSpeedrails();
		
		//Check for updates
		UpdateChecker.check();
		
		//Implement listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new MinecartListener(this), this);
		pm.registerEvents(new PlayerInteractListener(this), this);
		
		//Implement commands
		this.getCommand("speedcarts").setExecutor(new CommandHandler(this));
		
		//Enable message
		this.getLogger().info("Speedcarts enabled!");
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Goodbye Speedcarts!");
	}
	
	public WorldEditPlugin getWorldEdit() {
		Plugin WorldEdit = this.getServer().getPluginManager().getPlugin("WorldEdit");
		if (WorldEdit == null || !(WorldEdit instanceof WorldEditPlugin)) {
			return null;
		}
		return (WorldEditPlugin)WorldEdit;
	}
}
