package me.theonlyartz.oitc.managers;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.enums.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GamesManager {
    public ArrayList<ArrayList<Location>> reservedCords = new ArrayList<ArrayList<Location>>();
    public ArrayList<ArrayList<Location>> availableCords = new ArrayList<ArrayList<Location>>();
    public ArrayList<GameInstanceManager> runningGames = new ArrayList<>();
    private Main plugin;

    public GamesManager(Main plugin) {
        this.plugin = plugin;

        Location one = new Location(Bukkit.getWorld("world"), -10.5, 80, 32.5);
        Location two = new Location(Bukkit.getWorld("world"), -8.5, 80, -20.5);
        availableCords.add(new ArrayList<Location>(Arrays.asList(one, two)));
    }

    public GameInstanceManager createGame() {
        GameInstanceManager newGame = new GameInstanceManager(this.plugin);
        HashMap<String, Location> locations = reserveCords();

        newGame.teamA.setSpawnPoint(locations.get("A"));
        newGame.teamB.setSpawnPoint(locations.get("B"));

        runningGames.add(newGame);
        return newGame;
    }

    public void tryToStartGame(GameInstanceManager game) {
        if (!game.isReady()) return;

        game.teamA.spawn();
        game.teamB.spawn();
        game.state = State.STARTED;
    }

    public boolean areThereAvailableCords() {
        return this.availableCords.size() != 0;
    }

    public boolean areThereRunningGames() {
        return this.runningGames.size() != 0;
    }

    public HashMap<String, Location> reserveCords() {
        ArrayList<Location> toAdd = availableCords.get(0);
        HashMap<String, Location> locations = new HashMap<>();

        locations.put("A", toAdd.get(0));
        locations.put("B", toAdd.get(1));

        reservedCords.add(toAdd);
        availableCords.remove(toAdd);

        return locations;
    }

    public void unreserveCords(ArrayList<Location> locations) {
        availableCords.add(locations);
        reservedCords.remove(locations);
        return;
    }

    public ArrayList<Location> hashToListOfLocations(HashMap<String, Location> map) {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(map.get("A"));
        locations.add(map.get("B"));

        return locations;
    }

    public Player lookUpPlayer(String name) {
        for (GameInstanceManager game : runningGames) {
            for (Player p : game.teamA.getPlayers()) {
                if (p.getName().equals(name)) return p;
            }

            for (Player p : game.teamB.getPlayers()) {
                if (p.getName().equals(name)) return p;
            }
        }

        return null;
    }
}
