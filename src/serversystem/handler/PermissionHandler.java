package serversystem.handler;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import serversystem.config.Config;
import serversystem.main.ServerSystem;

public class PermissionHandler implements Listener {
	
	private static HashMap<Player, PermissionAttachment> attachments = new HashMap<>();
	
	public static void loadPlayerPermissions(Player player) {
		resetPlayerPermissions(player);
		if(Config.getPlayerPermissions(player) != null) {
			removeConfigDisabledPermissions(player);
			for(String string : Config.getPlayerPermissions(player)) {
				if(string.endsWith("*")) {
					string = string.substring(0, string.length() - 2);
					boolean add = true;
					if(string.startsWith("-")) {
						add = false;
						string = string.substring(1);
					}
					addPermission(player, string  + ".*");
					for (Permission permission : Bukkit.getServer().getPluginManager().getPermissions()) {
						if(permission.getName().startsWith(string)) {
							if(add) {
								addPermission(player, permission.getName());
							} else if(player.hasPermission(permission.getName())) {
								removePermission(player, permission.getName());
							}
						}
					}
					for (String permission : getVanillaPermissions()) {
						if(permission.startsWith(string)) {
							if(add) {
								addPermission(player, permission);
							} else if(player.hasPermission(permission)) {
								removePermission(player, permission);
							}
						}
					}
				} else if(string.startsWith("-")) {
					string = string.substring(1);
					if(player.hasPermission(string)) {
						removePermission(player, string);
					}
				} else {
					addPermission(player, string);
				}
			}
		} else {
			removeConfigDisabledPermissions(player);
		}
		reloadPlayerPermissions(player);
	}
	
	public static void resetPlayerPermissions(Player player) {
		removeAllPermissions(player);
		reloadPlayerPermissions(player);
	}
	
	private static void removeConfigDisabledPermissions(Player player) {
		if(Config.getDisabledPermissions() != null) {
			for(String string : Config.getDisabledPermissions()) {
				removePermission(player, string);
			}
		}
	}
	
	private static void addPermission(Player player, String permission) {
		if(attachments.get(player) != null) {
			attachments.get(player).setPermission(permission, true);
		}
	}
	
	private static void removePermission(Player player, String permission) {
		if(attachments.get(player) != null) {
			attachments.get(player).setPermission(permission, false);
		}
	}
	
	private static void removeAllPermissions(Player player) {
		if(attachments.get(player) != null) {
			for (String permission : attachments.get(player).getPermissions().keySet()) {
				removePermission(player, permission);
			}
		} else {
			attachments.put(player, player.addAttachment(ServerSystem.getInstance()));
		}
	}
	
	private static void reloadPlayerPermissions(Player player) {
		if(player.isOp()) {
			player.setOp(false);
			player.setOp(true);
		} else {
			player.setOp(true);
			player.setOp(false);
		}
	}
	
	private static ArrayList<String> getVanillaPermissions() {
		ArrayList<String> permissions = new ArrayList<>();
		permissions.add("minecraft.command.advancement");
		permissions.add("minecraft.command.ban");
		permissions.add("minecraft.command.ban-ip");
		permissions.add("minecraft.command.banlist");
		permissions.add("minecraft.command.clear");
		permissions.add("minecraft.command.debug");
		permissions.add("minecraft.command.defaultgamemode");
		permissions.add("minecraft.command.deop");
		permissions.add("minecraft.command.difficulty");
		permissions.add("minecraft.command.effect");
		permissions.add("minecraft.command.enchant");
		permissions.add("minecraft.command.gamemode");
		permissions.add("minecraft.command.gamerule");
		permissions.add("minecraft.command.give");
		permissions.add("minecraft.command.help");
		permissions.add("minecraft.command.kick");
		permissions.add("minecraft.command.kill");
		permissions.add("minecraft.command.list");
		permissions.add("minecraft.command.me");
		permissions.add("minecraft.command.op");
		permissions.add("minecraft.command.pardon");
		permissions.add("minecraft.command.pardon-ip");
		permissions.add("minecraft.command.playsound");
		permissions.add("minecraft.command.save-all");
		permissions.add("minecraft.command.save-off");
		permissions.add("minecraft.command.save-on");
		permissions.add("minecraft.command.say");
		permissions.add("minecraft.command.scoreboard");
		permissions.add("minecraft.command.seed");
		permissions.add("minecraft.command.setblock");
		permissions.add("minecraft.command.fill");
		permissions.add("minecraft.command.setidletimeout");
		permissions.add("minecraft.command.setworldspawn");
		permissions.add("minecraft.command.spawnpoint");
		permissions.add("minecraft.command.spreadplayers");
		permissions.add("minecraft.command.stop");
		permissions.add("minecraft.command.summon");
		permissions.add("minecraft.command.msg");
		permissions.add("minecraft.command.tellraw");
		permissions.add("minecraft.command.testfor");
		permissions.add("minecraft.command.testforblock");
		permissions.add("minecraft.command.time");
		permissions.add("minecraft.command.toggledownfall");
		permissions.add("minecraft.command.teleport");
		permissions.add("minecraft.command.tp");
		permissions.add("minecraft.command.weather");
		permissions.add("minecraft.command.whitelist");
		permissions.add("minecraft.command.xp");
		permissions.add("minecraft.command.selector");
		permissions.add("minecraft.admin.command_feedback");
		permissions.add("minecraft.nbt.copy");
		permissions.add("minecraft.nbt.place");
		permissions.add("minecraft.autocraft");
		permissions.add("minecraft.debugstick");
		permissions.add("minecraft.debugstick.always");
		return permissions;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getBlock().getType() == Material.COMMAND || event.getBlock().getType() == Material.COMMAND_MINECART) {
			System.out.println(event.getPlayer().hasPermission("serversystem.tools.commandblock"));
			if(!event.getPlayer().hasPermission("serversystem.tools.commandblock")) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.COMMAND || event.getBlock().getType() == Material.COMMAND_MINECART) {
			if(!event.getPlayer().hasPermission("serversystem.tools.commandblock")) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.COMMAND || event.getClickedBlock().getType() == Material.COMMAND_MINECART) {
				if(!event.getPlayer().hasPermission("serversystem.tools.commandblock")) {
					event.getPlayer().closeInventory();
				}
			}
		}
	}

}
