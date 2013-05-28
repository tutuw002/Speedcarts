package com.gmail.zant95.Speedcarts;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Rails;

import com.gmail.zant95.Speedcarts.Storage.DiskStorage;
import com.gmail.zant95.Speedcarts.Storage.MemStorage;

public class RailProcessor {
	public static void set(final Location min, final Location max, final Double vel, final boolean auto) {
		int minx = Math.min(min.getBlockX(), max.getBlockX()),
		miny = Math.min(min.getBlockY(), max.getBlockY()),
		minz = Math.min(min.getBlockZ(), max.getBlockZ()),
		maxx = Math.max(min.getBlockX(), max.getBlockX()),
		maxy = Math.max(min.getBlockY(), max.getBlockY()),
		maxz = Math.max(min.getBlockZ(), max.getBlockZ());
		for (int x = minx; x<=maxx;x++) {
			for (int y = miny; y<=maxy;y++) {
				for (int z = minz; z<=maxz;z++) {
					Block block = min.getWorld().getBlockAt(x, y, z);
					if (isRail(block)) {
						MemStorage.customRails.put(block.getLocation(), vel);
					}
				}
			}
		}
		if (auto) {
			for (int x = minx; x<=maxx;x++) {
				for (int y = miny; y<=maxy;y++) {
					for (int z = minz; z<=maxz;z++) {
						Block block = min.getWorld().getBlockAt(x, y, z),
						blockE = block.getRelative(BlockFace.EAST),
						blockN = block.getRelative(BlockFace.NORTH),
						blockS = block.getRelative(BlockFace.SOUTH),
						blockW = block.getRelative(BlockFace.WEST);
						if (isRail(block)) {
							Rails rail = (Rails)block.getState().getData();
							if (rail.isCurve() && vel >= 0.5) {
								MemStorage.customRails.put(block.getLocation(), 0.5);
								if (isRail(blockE)) MemStorage.customRails.put(blockE.getLocation(), 0.5);
								if (isRail(blockN)) MemStorage.customRails.put(blockN.getLocation(), 0.5);
								if (isRail(blockS)) MemStorage.customRails.put(blockS.getLocation(), 0.5);
								if (isRail(blockW)) MemStorage.customRails.put(blockW.getLocation(), 0.5);
							} else if (rail.isOnSlope() && vel >= 0.4) {
								MemStorage.customRails.put(block.getLocation(), 0.4);
								if (isRail(blockE)) MemStorage.customRails.put(blockE.getLocation(), 0.4);
								if (isRail(blockN)) MemStorage.customRails.put(blockN.getLocation(), 0.4);
								if (isRail(blockS)) MemStorage.customRails.put(blockS.getLocation(), 0.4);
								if (isRail(blockW)) MemStorage.customRails.put(blockW.getLocation(), 0.4);
							} else if (vel >= 0.5
							&& (!isRail(block.getRelative(rail.getDirection()))
								|| !isRail(block.getRelative(rail.getDirection().getOppositeFace()))
							)) {
								MemStorage.customRails.put(block.getLocation(), 0.5);
							}
						}
					}
				}
			}
		}
		DiskStorage.asyncSaveSpeedrails(min.getWorld());
	}
	
	public static void remove(final Location min, final Location max) {
		int minx = Math.min(min.getBlockX(), max.getBlockX()),
		miny = Math.min(min.getBlockY(), max.getBlockY()),
		minz = Math.min(min.getBlockZ(), max.getBlockZ()),
		maxx = Math.max(min.getBlockX(), max.getBlockX()),
		maxy = Math.max(min.getBlockY(), max.getBlockY()),
		maxz = Math.max(min.getBlockZ(), max.getBlockZ());	
		for (int x = minx; x<=maxx;x++) {
			for (int y = miny; y<=maxy;y++) {
				for (int z = minz; z<=maxz;z++) {
					Block block = min.getWorld().getBlockAt(x, y, z);
					MemStorage.customRails.remove(block.getLocation());
				}
			}
		}
		DiskStorage.asyncSaveSpeedrails(min.getWorld());
	}
	
	public static void clean(final Location min, final Location max) {
		int minx = Math.min(min.getBlockX(), max.getBlockX()),
		miny = Math.min(min.getBlockY(), max.getBlockY()),
		minz = Math.min(min.getBlockZ(), max.getBlockZ()),
		maxx = Math.max(min.getBlockX(), max.getBlockX()),
		maxy = Math.max(min.getBlockY(), max.getBlockY()),
		maxz = Math.max(min.getBlockZ(), max.getBlockZ());	
		for (int x = minx; x<=maxx;x++) {
			for (int y = miny; y<=maxy;y++) {
				for (int z = minz; z<=maxz;z++) {
					Block block = min.getWorld().getBlockAt(x, y, z);
					if (!isRail(block)) {
						MemStorage.customRails.remove(block.getLocation());
					}
				}
			}
		}
		DiskStorage.asyncSaveSpeedrails(min.getWorld());
	}
	
	private static boolean isRail(Block block) {
		if (block.getState().getData() instanceof Rails) {
			return true;
		} else {
			return false;
		}
	}
}