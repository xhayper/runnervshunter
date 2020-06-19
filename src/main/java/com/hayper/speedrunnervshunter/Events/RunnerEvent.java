package com.hayper.speedrunnervshunter.Events;

import com.hayper.speedrunnervshunter.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class RunnerEvent implements Listener {

    @EventHandler
    public void playerDied(PlayerDeathEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isRunner(e.getEntity())) return;
        Main.GS.getHunterList().forEach(hunterUUID -> {
            Player hunter = Bukkit.getPlayer(hunterUUID);
            if (hunter != null) {
                hunter.sendMessage("You win! The runner have died!");
            }
        });

        Main.GS.getRunnerList().forEach(runnerUUID -> {
            Player runner = Bukkit.getPlayer(runnerUUID);
            if (runner != null) {
                runner.sendMessage("You Lose! You died!");
            }
        });
        Main.GS.stopGame();
    }

    @EventHandler
    public void playerWalk(PlayerMoveEvent e) {
        if (!Main.GS.isGameStarted() || !Main.GS.isRunner(e.getPlayer())) return;
        Main.GS.getHunterList().forEach(hunterUUID -> {
            Player hunter = Bukkit.getPlayer(hunterUUID);
            if (hunter != null) {
                Player runner = Bukkit.getPlayer(Main.GS.getFollowing(hunter));
                if (runner != null) {
                    hunter.setCompassTarget(runner.getLocation());
                }
            }
        });
    }

    @EventHandler
    public void playerKillEntity(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        if (!Main.GS.isGameStarted() || !Main.GS.isRunner(e.getEntity().getKiller())) return;
        if (e.getEntity() instanceof EnderDragon) {
            Main.GS.getHunterList().forEach(hunterUUID -> {
                Player hunter = Bukkit.getPlayer(hunterUUID);
                if (hunter != null) {
                    hunter.sendMessage("You lose! The runner have kill the enderdrago!");
                }
            });

            Main.GS.getRunnerList().forEach(runnerUUID -> {
                Player runner = Bukkit.getPlayer(runnerUUID);
                if (runner != null) {
                    runner.sendMessage("You win! You killed the enderdragon!");
                }
            });
            Main.GS.stopGame();
        }
    }
}
