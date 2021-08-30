package serversystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import serversystem.handler.ChatHandler;
import serversystem.utilities.CommandAssistant;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(new CommandAssistant(sender).isSenderInstanceOfPlayer()) {
			Player player = (Player)sender;
			if(player.getAllowFlight()) {
				player.setAllowFlight(false);
				ChatHandler.sendServerMessage(sender, "You can no longer fly!");
			} else {
				player.setAllowFlight(true);
				ChatHandler.sendServerMessage(sender, "You can fly now!");
			}
		}
		return true;
	}

}
