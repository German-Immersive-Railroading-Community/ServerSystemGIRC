package serversystem.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamHandler {
	
	public static final String TEAMVANISH = "00Vanish";
	public static final String TEAMRANKADMIN = "01RankAdmin";
	public static final String TEAMRANKMODERATOR = "02RankModerator";
	public static final String TEAMRANKDEVELOPER = "03RankDeveloper";
	public static final String TEAMRANKSUPPORTER = "04RankSupporter";
	public static final String TEAMRANKTEAM = "05RankTeam";
	public static final String TEAMRANKOPERATOR = "06RankOperator";
	public static final String TEAMRANKYOUTUBER = "07RankYouTuber";
	public static final String TEAMRANKPREMIUM = "08RankPremium";
	public static final String TEAMRANKPLAYER = "09RankPlayer";
	public static final String TEAMSPECTATOR = "100Spectator";
	
	public static void initializeTeams() {
		createTeam(TEAMVANISH,  ChatColor.GRAY + "[VANISH] ", ChatColor.GRAY);
		createTeam(TEAMRANKADMIN, ChatColor.DARK_RED + "[Admin] ", ChatColor.DARK_RED);
		createTeam(TEAMRANKMODERATOR, ChatColor.DARK_BLUE + "[Moderator] ", ChatColor.DARK_BLUE);
		createTeam(TEAMRANKDEVELOPER, ChatColor.AQUA + "[Developer] ", ChatColor.AQUA);
		createTeam(TEAMRANKSUPPORTER, ChatColor.BLUE + "[Supporter] ", ChatColor.BLUE);
		createTeam(TEAMRANKTEAM, ChatColor.RED + "[Team] " , ChatColor.RED);
		createTeam(TEAMRANKOPERATOR, ChatColor.RED + "[OP] ", ChatColor.RED);
		createTeam(TEAMRANKYOUTUBER, ChatColor.DARK_PURPLE + "[YouTube] ", ChatColor.DARK_PURPLE);
		createTeam(TEAMRANKPREMIUM, ChatColor.GOLD + "[Premium] ", ChatColor.GOLD);
		createTeam(TEAMRANKPLAYER, ChatColor.WHITE + "", ChatColor.WHITE);
		createTeam(TEAMSPECTATOR, ChatColor.GRAY + "[SPECTATOR] ", ChatColor.GRAY);
	}
	
	public static void resetTeams() {
		removeTeam(TEAMVANISH);
		removeTeam(TEAMRANKADMIN);
		removeTeam(TEAMRANKMODERATOR);
		removeTeam(TEAMRANKDEVELOPER);
		removeTeam(TEAMRANKSUPPORTER);
		removeTeam(TEAMRANKTEAM);
		removeTeam(TEAMRANKOPERATOR);
		removeTeam(TEAMRANKYOUTUBER);
		removeTeam(TEAMRANKPREMIUM);
		removeTeam(TEAMRANKPLAYER);
		removeTeam(TEAMSPECTATOR);
	}
	
	public static void createTeam(String name, String prefix, ChatColor color) {
		if (getMainScoreboard().getTeam(name) == null) {
			getMainScoreboard().registerNewTeam(name).setPrefix(prefix);
			getMainScoreboard().getTeam(name).setColor(color);
		}
	}
	
	public static void removeTeam(String team) {
		getMainScoreboard().getTeam(team).unregister();
	}
	
	public static void addPlayerToTeam(String team, String player) {
		getMainScoreboard().getTeam(team).addEntry(player);
	}
	
	public static void addPlayerToTeam(String team, Player player) {
		getMainScoreboard().getTeam(team).addEntry(player.getName());
	}
	
	public static void removePlayerFromTeam(String team, String player) {
		getMainScoreboard().getTeam(team).removeEntry(player);
	}
	
	public static void removePlayerFromTeam(Player player) {
		try {
			getMainScoreboard().getTeam(getPlayersTeamName(player)).removeEntry(player.getName());
		} catch (Exception exception) {}
	}
	
	public static Team getPlayersTeam(Player player) {
		return player.getScoreboard().getEntryTeam(player.getName());
	}
	
	public static String getPlayersTeamName(Player player) {
		return player.getScoreboard().getEntryTeam(player.getName()).getName();
	}
	
	public static void addRoleToPlayer(Player player) {
		if(player.hasPermission("serversystem.rank.admin")) {
			addPlayerToTeam(TEAMRANKADMIN, player);
		}else if(player.hasPermission("serversystem.rank.moderator")) {
			addPlayerToTeam(TEAMRANKMODERATOR, player);
		}else if(player.hasPermission("serversystem.rank.developer")) {
			addPlayerToTeam(TEAMRANKDEVELOPER, player);
		}else if(player.hasPermission("serversystem.rank.supporter")) {
			addPlayerToTeam(TEAMRANKSUPPORTER, player);
		}else if(player.hasPermission("serversystem.rank.team")) {
			addPlayerToTeam(TEAMRANKTEAM, player);
		}else if(player.hasPermission("serversystem.rank.operator") || player.isOp()) {
			addPlayerToTeam(TEAMRANKOPERATOR, player);
		}else if(player.hasPermission("serversystem.rank.youtuber")) {
			addPlayerToTeam(TEAMRANKYOUTUBER, player);
		}else if(player.hasPermission("serversystem.rank.premium")) {
			addPlayerToTeam(TEAMRANKPREMIUM, player);
		} else {
			addPlayerToTeam(TEAMRANKPLAYER, player);
		}
	}
	
	public static ChatColor getPlayerNameColor(Player player) {
		try {
			return player.getScoreboard().getEntryTeam(player.getName()).getColor();
		} catch (NullPointerException exception) {
			return ChatColor.WHITE;
		}
	}
	
	private static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

}
