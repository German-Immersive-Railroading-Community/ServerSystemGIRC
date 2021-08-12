package serversystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import serversystem.handler.ChatHandler;
import serversystem.utilities.CommandAssistant;

public class PingCommand  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(new CommandAssistant(sender).isSenderInstanceOfPlayer()) {
			Player player = (Player)sender;
			int ping = ((CraftPlayer) player).getHandle().ping;
			ChatHandler.sendServerMessage(sender, "Your ping is " + ping + "ms!");
		}
		return true;
	}

}