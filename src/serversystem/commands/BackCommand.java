package serversystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import serversystem.events.PlayerTeleportListener;
import serversystem.handler.ChatHandler;
import serversystem.utilities.CommandAssistant;

public class BackCommand  implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(new CommandAssistant(sender).isSenderInstanceOfPlayer()) {
			Player player = (Player)sender;
			if(PlayerTeleportListener.locations.containsKey(player)) {
				player.teleport(PlayerTeleportListener.locations.get(player));
			} else {
				ChatHandler.sendServerErrorMessage(sender, "You have no location to teleport back!");
			}
		}
		return true;
	}

}