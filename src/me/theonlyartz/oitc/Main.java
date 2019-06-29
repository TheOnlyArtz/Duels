package me.theonlyartz.oitc;

import me.theonlyartz.oitc.events.Events;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(this), this);
    }
}
