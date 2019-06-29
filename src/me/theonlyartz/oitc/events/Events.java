package me.theonlyartz.oitc.events;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.managers.GameInstanceManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Events implements Listener {
    private Main plugin;

    public Events(Main p) {
        this.plugin = p;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        GameInstanceManager game = this.plugin.gamesManager.lookUpPlayer(player.getName());
        if (game == null) return;

        if (!(player.getKiller() instanceof Player)) {
            game.getPlayerTeam(player).respawnPlayer(player);
            String msg = String.format("&O&B%s&r &3just died by some unknown shit!", player.getName());
            game.teamB.broadcast(msg);
            game.teamA.broadcast(msg);
            return;
        }

        Player killer = player.getKiller();

        String msg = String.format("&O&B%s&r &3has been killed by&r &L&6%s", player.getName(), killer.getName());
        game.teamB.broadcast(msg);
        game.teamA.broadcast(msg);
    }
}
