package me.theonlyartz.oitc.managers;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.models.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameInstanceManager {
    public Team teamA;
    public Team teamB;
    private Main plugin;

    public GameInstanceManager(Main p) {
        teamA = new Team(p);
        teamB = new Team(p);
        this.plugin = p;
    }

    /*
     * This method will just add the player to a desired team
     */
    public void addPlayer(Player p) {
        this.computePlayerTeam().addPlayer(p);
    }

    /*
     * This method will compute what team each player should go.
     */
    private Team computePlayerTeam() {
        int teamAsize = this.teamA.getPlayers().size();
        int teamBsize = this.teamB.getPlayers().size();

        if (teamAsize > teamBsize) return this.teamB;

        return teamA;
    }
}
