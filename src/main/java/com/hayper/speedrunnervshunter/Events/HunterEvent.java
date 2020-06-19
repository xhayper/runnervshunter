package com.hayper.speedrunnervshunter.Events;

import com.hayper.speedrunnervshunter.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class HunterEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (Main.GS.isGameStarted() && Main.GS.isHunter(e.getPlayer()) && !Main.GS.isPrepareTimeup()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isHunter(e.getPlayer())) return;
        e.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS, 1));
    }

    @EventHandler
    public void onRightClickItem(PlayerInteractEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isHunter(e.getPlayer())) return;
        if (e.getMaterial() == Material.COMPASS) {
            Player newFollower = Bukkit.getPlayer(Main.GS.nextFollower(e.getPlayer()));
            if (newFollower != null) {
                e.getPlayer().sendActionBar(String.format("Now following %s!", newFollower.getName()));
                e.getPlayer().setCompassTarget(newFollower.getLocation());
            }
        }
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isHunter(e.getPlayer())) return;
        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            Player follower = Bukkit.getPlayer(Main.GS.getFollowing(e.getPlayer()));
            if (follower != null) {
                e.getPlayer().sendActionBar(String.format("[%sm] Following %s! [%sm]", Math.round(e.getPlayer().getLocation().distance(follower.getLocation())), follower.getName(), Math.round(e.getPlayer().getLocation().distance(follower.getLocation()))));
            }
        }
    }

    @EventHandler
    public void onDied(PlayerDeathEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isHunter(e.getEntity())) return;
        e.getDrops().forEach(itemStack -> {
            if (itemStack.getType() == Material.COMPASS) e.getDrops().remove(itemStack);
        });
    }

    @EventHandler
    public void playerWalk(PlayerMoveEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isHunter(e.getPlayer())) return;
        Player runner = Bukkit.getPlayer(Main.GS.getFollowing(e.getPlayer()));
        if (runner != null) {
            e.getPlayer().setCompassTarget(runner.getLocation());
        }
    }

    @EventHandler
    public void playerKillEntity(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        if (!Main.GS.isGameStarted() || !Main.GS.isHunter(e.getEntity().getKiller())) return;
        if (e.getEntity() instanceof EnderDragon) {
            Main.GS.getRunnerList().forEach(runnerUUID -> {
                Player runner = Bukkit.getPlayer(runnerUUID);
                if (runner != null) {
                    runner.sendMessage("You lose! The hunter have kill the enderdrago!");
                }
            });

            Main.GS.getHunterList().forEach(hunterUUID -> {
                Player hunter = Bukkit.getPlayer(hunterUUID);
                if (hunter != null) {
                    hunter.sendMessage("You win! You killed the enderdragon!");
                }
            });
            Main.GS.stopGame();
        }
    }

}
