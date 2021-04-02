package serversystem.signs;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.handler.WorldGroupHandler;
import serversystem.utilities.ServerSign;

public class WorldSign implements ServerSign{

	@Override
	public String getLabel() {
		return "world";
	}

	@Override
	public void onAction(Player player, Sign sign, String args) {
		if(Bukkit.getWorld(args) != null) {
			WorldGroupHandler.teleportPlayer(player, Bukkit.getWorld(args));
		}
	}

	@Override
	public boolean onPlace(Player player, String args) {
		if(Bukkit.getWorld(args) != null) {
			return true;
		}
		return false;
	}

}
