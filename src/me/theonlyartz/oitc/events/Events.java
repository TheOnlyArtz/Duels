package me.theonlyartz.oitc.events;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.enums.State;
import me.theonlyartz.oitc.managers.GameInstanceManager;
import me.theonlyartz.oitc.models.Team;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

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
            String msg = String.format("&O&B%s&r &3just died by some unknown shit!", player.getName());
            game.teamB.broadcast(msg);
            game.teamA.broadcast(msg);
            game.getPlayerTeam(player).respawnPlayer(player);
            return;
        }

        Player killer = player.getKiller();
        killer.getInventory().addItem(new ItemStack(Material.ARROW, 1));
        killer.setHealth(20.0);
        String msg = String.format("&O&B%s&r &3has been killed by&r &L&6%s", player.getName(), killer.getName());
        game.teamB.broadcast(msg);
        game.teamA.broadcast(msg);

    }

    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent e) {
        GameInstanceManager game = this.plugin.gamesManager.lookUpPlayer(e.getPlayer().getName());
        Team team = game.getPlayerTeam(e.getPlayer());
        if (game != null) {
            this.plugin.gamesManager.applyOITCKit(e.getPlayer());

            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
                public void run() {
                    game.getPlayerTeam(e.getPlayer()).respawnPlayer(e.getPlayer());
                }
            }, 2L);
        }
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player whoWasHit = (Player) e.getEntity();
            Player whoHit = (Player) e.getDamager();
            GameInstanceManager game = this.plugin.gamesManager.lookUpPlayer(whoWasHit.getName());
            if (game == null) return;

            if (game.getPlayerTeam(whoWasHit).players.contains(whoHit)) {
                e.setCancelled(true);
            }
        }

        if ((e.getDamager() instanceof Projectile)) {
            if (e.getDamager().getType().equals(EntityType.ARROW)) {
                Player player = (Player) (((Projectile) e.getDamager()).getShooter());
                GameInstanceManager game = this.plugin.gamesManager.lookUpPlayer(player.getName());
                if (game != null) {
                    if ((e.getEntity() instanceof Player) && game.getPlayerTeam((Player) e.getEntity()).players.contains(player)) {
                        e.setCancelled(true);
                        return;
                    }
                    e.setDamage(1000);
                }
            }
        }
    }

//    @EventHandler
//    public void onItemSpawn(ItemSpawnEvent e)
//    {
//        e.setCancelled(true);
//    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();
        String cmd = msg.split("\\s+")[0].replace('/', Character.MIN_VALUE).substring(1);
        GameInstanceManager game = this.plugin.gamesManager.lookUpPlayer(player.getName());
        if (game != null && game.getState().equals(State.STARTED)) {
            if (plugin.getCommand(cmd) == null) {
                event.setCancelled(true);
                Main.sendMessage(player, "&CYou can't execute external commands in a duel!");
            }
        }
    }
}
