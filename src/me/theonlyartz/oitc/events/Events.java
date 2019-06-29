package me.theonlyartz.oitc.events;

import me.theonlyartz.oitc.Main;
import me.theonlyartz.oitc.managers.GameInstanceManager;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
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
            game.getPlayerTeam(player).respawnPlayer(player);
            String msg = String.format("&O&B%s&r &3just died by some unknown shit!", player.getName());
            game.teamB.broadcast(msg);
            game.teamA.broadcast(msg);
            game.getPlayerTeam(player).respawnPlayer(player);
            return;
        }

        Player killer = player.getKiller();
        killer.getInventory().addItem(new ItemStack(Material.ARROW, 1));
        String msg = String.format("&O&B%s&r &3has been killed by&r &L&6%s", player.getName(), killer.getName());
        game.teamB.broadcast(msg);
        game.teamA.broadcast(msg);
        game.getPlayerTeam(player).respawnPlayer(player);
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
}
