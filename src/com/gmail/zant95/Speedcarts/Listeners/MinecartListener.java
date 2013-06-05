package com.gmail.zant95.Speedcarts.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleUpdateEvent;

import com.gmail.zant95.Speedcarts.Speedcarts;
import com.gmail.zant95.Speedcarts.Storage.MemStorage;

public class MinecartListener implements Listener {
	Speedcarts plugin;
	
	public MinecartListener(Speedcarts instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onVehicleUpdate(VehicleUpdateEvent event) {
		Location vehicleLoc = event.getVehicle().getLocation().getBlock().getLocation();
		if (event.getVehicle() instanceof Minecart && MemStorage.speedrails.containsKey(vehicleLoc)) {
			Minecart minecart = ((Minecart)event.getVehicle());
			if (!(minecart instanceof RideableMinecart)) {
				minecart.setSlowWhenEmpty(false);
			}
			minecart.setMaxSpeed(MemStorage.speedrails.get(vehicleLoc));
		}
	}
}