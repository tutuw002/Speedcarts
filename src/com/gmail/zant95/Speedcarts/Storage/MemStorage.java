package com.gmail.zant95.Speedcarts.Storage;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class MemStorage {
	public static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Speedcarts");
	public static String storageFolder = plugin.getDataFolder() + File.separator + "storage.";
	public static HashMap<Location, Double> customRails = new HashMap<Location, Double>();
	public static boolean isLoadingStorage = false;
}