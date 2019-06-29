package me.theonlyartz.oitc;

import me.theonlyartz.oitc.commands.OitcCommand;
import me.theonlyartz.oitc.events.Events;
import me.theonlyartz.oitc.managers.GamesManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public GamesManager gamesManager;

    public Main() {
        this.gamesManager = new GamesManager(this);
    }

    public static void sendMessage(Player p, String msg) {
        String toSend = ChatColor.translateAlternateColorCodes('&', "&2&O[OneInTheChamber]&2&O " + msg);
        p.sendMessage(toSend);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(this), this);
        this.getCommand("oitc").setExecutor(new OitcCommand(this));
    }
}
