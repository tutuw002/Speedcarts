package com.gmail.zant95.Speedcarts.Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class DiskStorage {
	public static void asyncLoadSpeedrails() {
		Bukkit.getScheduler().runTaskAsynchronously(MemStorage.plugin, new Runnable() {
			@Override
			public void run() {
				MemStorage.isLoadingStorage = true;
				List<World> worlds = Bukkit.getServer().getWorlds();
				for (World world:worlds) {
					File worldStorage = new File(MemStorage.storageFolder + world.getName() + ".dat");
					if (worldStorage.exists()) 
						try {
							BufferedReader in = new BufferedReader(new FileReader(worldStorage));
							StringTokenizer st;
							String input;
							while((input = in.readLine()) != null) {
								st = new StringTokenizer(input, ",");
								MemStorage.customRails.put(
									new Location(
										world,
										Double.parseDouble(st.nextToken()),
										Double.parseDouble(st.nextToken()),
										Double.parseDouble(st.nextToken())
									),
									Double.parseDouble(st.nextToken())
								);
							}
							in.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
				}
				MemStorage.isLoadingStorage = false;
			}
		});
	}
	
	public static void asyncSaveSpeedrails(final World world) {
		if (!MemStorage.isLoadingStorage) {
			Bukkit.getScheduler().runTaskAsynchronously(MemStorage.plugin, new Runnable() {
				@Override
				public void run() {
					try {
						File worldStorage = new File(MemStorage.storageFolder + world.getName() + ".dat");
						BufferedWriter out = new BufferedWriter(new FileWriter(worldStorage));
						Iterator<Entry<Location, Double>> it = MemStorage.customRails.entrySet().iterator();
						while(it.hasNext()) {
							Entry<Location, Double> block = it.next();
							if (block.getKey().getWorld().equals(world)) {	
								out.write((int)block.getKey().getX() + ","
									+ (int)block.getKey().getY() + ","
									+ (int)block.getKey().getZ() + ","
									+ block.getValue() + "\n");
							}
						}
						out.close();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}