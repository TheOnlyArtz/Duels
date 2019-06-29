package me.theonlyartz.oitc.managers;

import me.theonlyartz.oitc.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    public File file = new File("/Plugins/OITC/", "config.yaml");
    public YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }
}
