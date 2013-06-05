package com.gmail.zant95.Speedcarts.Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.zant95.Speedcarts.mcstats.Metrics;
import com.gmail.zant95.Speedcarts.mcstats.Metrics.Graph;

public class DiskStorage {
	public static void asyncLoadSpeedrails() {
		Bukkit.getScheduler().runTaskAsynchronously(MemStorage.plugin, new Runnable() {
			@Override
			public void run() {
				MemStorage.isLoadingStorage = true;
				List<World> worlds = Bukkit.getServer().getWorlds();
				for (World world:worlds) {
					File worldStorage = new File(MemStorage.storageFolder + world.getName() + ".dat");
					if (worldStorage.exists()) {
						try {
							BufferedReader in = new BufferedReader(new FileReader(worldStorage));
							StringTokenizer st;
							String input;
							while((input = in.readLine()) != null) {
								st = new StringTokenizer(input, ",");
								MemStorage.speedrails.put(
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
				}
				MemStorage.isLoadingStorage = false;
				
				//Implement Metrics
				try {
					Metrics metrics = new Metrics(MemStorage.plugin);
					
				    Graph graph = metrics.createGraph("Total speedrails");
				    graph.addPlotter(new Metrics.Plotter("Speedrails") {
						@Override
						public int getValue() {
							return MemStorage.speedrails.size();
						}
				    });
					
					metrics.start();
				} catch (IOException e) {
					// Failed to submit the stats :-(
				}
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
						Iterator<Entry<Location, Double>> it = MemStorage.speedrails.entrySet().iterator();
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