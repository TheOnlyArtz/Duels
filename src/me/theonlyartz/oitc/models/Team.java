package me.theonlyartz.oitc.models;

import me.theonlyartz.oitc.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {
    public ArrayList<Player> players;
    public Location spawnPoint;
    public int score = 0;

    private Main plugin;

    public Team(Main p) {
        this.players = new ArrayList<Player>();
        this.spawnPoint = null;
        this.plugin = p;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }

    public void spawn() {
        for (Player p : this.players) {
            p.teleport(this.spawnPoint);
            p.setGameMode(GameMode.ADVENTURE);
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Location getSpawnPoint() {
        return this.spawnPoint;
    }

    public void setSpawnPoint(Location point) {
        this.spawnPoint = point;
    }

    public void broadcast(String msg) {
        for (Player p : this.players) {
            Main.sendMessage(p, msg);
        }
    }

    public void respawnPlayer(Player p) {
        p.teleport(spawnPoint);
    }
}
