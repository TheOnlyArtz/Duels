package me.theonlyartz.oitc.managers;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.enums.State;
import me.theonlyartz.oitc.models.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameInstanceManager {
    public Team teamA;
    public Team teamB;
    private Main plugin;
    public State state;

    public GameInstanceManager(Main p) {
        this.teamA = new Team(p, "A");
        this.teamB = new Team(p, "B");
        this.plugin = p;
        this.state = State.WAITING;
    }

    /*
     * This method will just add the player to a desired team
     */
    public void addPlayer(Player p) {

        this.computePlayerTeam().addPlayer(p);
    }

    public State getState() {
        return this.state;
    }
    /*
     * This method will compute what team each player should go.
     */
    private Team computePlayerTeam() {
        try {
            int teamAsize = this.teamA.getPlayers().size();
            int teamBsize = this.teamB.getPlayers().size();

            if (teamAsize > teamBsize) return this.teamB;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teamA;
    }

    public Team getPlayerTeam(Player player) {
        for (Player p : teamA.getPlayers()) {
            if (p.getName().equals(player.getName())) return teamA;
        }

        for (Player p : teamB.getPlayers()) {
            if (p.getName().equals(player.getName())) return teamB;
        }

        return null;
    }

    public boolean isReady() {
        return (teamA.getPlayers().size() + teamB.getPlayers().size()) >= 2;
    }
}
