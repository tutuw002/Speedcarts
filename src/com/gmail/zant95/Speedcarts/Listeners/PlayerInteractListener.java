package com.gmail.zant95.Speedcarts.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Rails;

import com.gmail.zant95.Speedcarts.Speedcarts;
import com.gmail.zant95.Speedcarts.Storage.MemStorage;

public class PlayerInteractListener implements Listener {
	Speedcarts plugin;

	public PlayerInteractListener(Speedcarts instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("speedcarts.view")
		&& player.isSneaking()
		&& event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
		&& player.getItemInHand().getType().equals(Material.GOLD_SPADE)) {
			Block block = event.getClickedBlock();
			if (block.getState().getData() instanceof Rails) {
				Location loc = block.getLocation();
				if (MemStorage.speedrails.containsKey(loc)) {
					player.sendMessage(ChatColor.YELLOW + "The custom max speed of this rail is " + ChatColor.WHITE + MemStorage.speedrails.get(loc) + ChatColor.YELLOW + ".");
				} else {
					player.sendMessage(ChatColor.YELLOW + "This is a normal rail.");
				}
			}
		}
	}
}
