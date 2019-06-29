package me.theonlyartz.oitc.commands;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.enums.State;
import me.theonlyartz.oitc.managers.GameInstanceManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class OitcCommand implements CommandExecutor {
    private Main plugin;

    public OitcCommand(Main p) {
        this.plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You are not allowed to do that!");
            return true;
        }

        Player player = (Player) sender;

        try {
            Method method = this.getClass().getMethod(args[0], Player.class, String[].class);
            method.invoke(this, player, args);
        } catch (Exception e) {
            String msg = "&CThis option does not exist!";
            Main.sendMessage(player, msg);
        }

        return true;
    }

    public void create(Player player, String[] args) {
        if (!this.plugin.gamesManager.areThereAvailableCords()) {
            Main.sendMessage(player, "&CNo more arenas are available, there's already a running game!");
            return;
        }

        try {
            GameInstanceManager game = this.plugin.gamesManager.createGame();
            game.addPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void join(Player player, String[] args) {
        if (!this.plugin.gamesManager.areThereRunningGames()) {
            Main.sendMessage(player, "&CNo games are running at this moment");
            return;
        }

        if (this.plugin.gamesManager.lookUpPlayer(player.getName()) != null) {
            Main.sendMessage(player, "&CYou are already in a game");
            return;
        }

        GameInstanceManager availableGame = this.plugin.gamesManager.runningGames.get(0);
        availableGame.addPlayer(player);

        if (availableGame.getState() == State.WAITING) {
            this.plugin.gamesManager.tryToStartGame(availableGame);
        } else if (availableGame.getState() == State.STARTED) {
            availableGame.getPlayerTeam(player).respawnPlayer(player);
            plugin.gamesManager.applyOITCKit(player);
        }

        Main.sendMessage(player, "&A&OYou've joined the game successfully");
        return;
    }
}
