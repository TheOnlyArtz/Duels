package me.theonlyartz.oitc.managers;

import me.theonlyartz.oitc.Main;
import org.bukkit.Location;

import java.util.ArrayList;

public class GamesManager {
    public ArrayList<ArrayList<Location>> reservedCords = new ArrayList<ArrayList<Location>>();
    public ArrayList<ArrayList<Location>> availableCords = new ArrayList<ArrayList<Location>>();
    public ArrayList<GameInstanceManager> runningGames = new ArrayList<>();
    private Main plugin;

    public GamesManager(Main plugin) {
        this.plugin = plugin;
    }
}
