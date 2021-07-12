package serversystem.events;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.PermissionHandler;
import serversystem.handler.PlayerPacketHandler;
import serversystem.handler.WorldGroupHandler;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(Config.isJoinMessageActiv()) {
			event.setJoinMessage(ChatHandler.getPlayerJoinMessage(event));
		} else {
			event.setJoinMessage("");
		}
		Config.addPlayer(event.getPlayer());
		PermissionHandler.loadPlayerPermissions(event.getPlayer());
		event.getPlayer().setGameMode(Config.getWorldGamemode(event.getPlayer().getWorld().getName()));
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerJoin(event.getPlayer());
		if(Config.lobbyExists() && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		if(Config.getTitle() != null && Config.getSubtitle() != null) {
			ChatHandler.sendTitle(event.getPlayer(), ChatHandler.parseColor(Config.getTitleColor()) + Config.getTitle(), ChatHandler.parseColor(Config.getSubtitleColor()) + Config.getSubtitle());
		}
		if(Config.getTitle() != null && Config.getSubtitle() != null) {
			ChatHandler.sendTitle(event.getPlayer(), ChatHandler.parseColor(Config.getTitleColor()) + Config.getTitle(), ChatHandler.parseColor(Config.getSubtitleColor()) + Config.getSubtitle());
		}
		if(Config.getTitle() != null) {PlayerPacketHandler.sendTitle(event.getPlayer(), EnumTitleAction.TITLE, Config.getTitle(), Config.getTitleColor(), 100);}
		if(Config.getSubtitle() != null) {PlayerPacketHandler.sendTitle(event.getPlayer(), EnumTitleAction.SUBTITLE, Config.getSubtitle(), Config.getSubtitleColor(), 100);}
		sendTablist(event.getPlayer(), Config.getTablistTitle(), Config.getTablistTitleColor(), Config.getTablistSubtitle(), Config.getTablistSubtitleColor());
	}
	
	public static void sendTablist(Player player, String header, String headercolor, String subtitle, String subtitlecolor) {
        IChatBaseComponent tabheader = ChatSerializer.a("{\"text\": \"" + header + "\",\"color\":\"" + headercolor + "\"}");
        IChatBaseComponent tabfooter = ChatSerializer.a("{\"text\": \"" + subtitle + "\",\"color\":\"" + subtitlecolor + "\"}");
        PacketPlayOutPlayerListHeaderFooter tablist = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field headerField = tablist.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(tablist, tabheader);
            headerField.setAccessible(!headerField.isAccessible());
            Field footerField = tablist.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(tablist, tabfooter);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception exception) {
        	exception.printStackTrace();
        } finally {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(tablist);
        }
	}

}
