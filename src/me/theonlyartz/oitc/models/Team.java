package me.theonlyartz.oitc.models;

import me.theonlyartz.oitc.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {
    public ArrayList<Player> players;
    public Location spawnPoint;
    public int score = 0;

    private Main plugin;

    public Team(Main p) {
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
            p.sendMessage(msg);
        }
    }

    public void respawnPlayer(Player p) {
        p.teleport(spawnPoint);
    }
}
