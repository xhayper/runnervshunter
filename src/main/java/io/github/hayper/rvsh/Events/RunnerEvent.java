package io.github.hayper.rvsh.Events;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class RunnerEvent implements Listener {

    Main pl;

    public RunnerEvent(Main pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onRunnerKillEntity(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ENDER_DRAGON) return;
        if (!GameManager.runnerList.isEmpty() && !GameManager.runnerList.contains(event.getEntity().getKiller()) || !GameManager.gameStarted) return;
        GameManager.runnerList.forEach(runner -> runner.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You win! You killed the Enderdragon!")));
        GameManager.hunterList.forEach(hunter -> hunter.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You lost! The runner has killed the Enderdragon!")));
        GameManager.stopGame();
    }

    @EventHandler
    public void onRunnerDied(PlayerDeathEvent event) {
        if (!GameManager.runnerList.isEmpty() && !GameManager.runnerList.contains(event.getEntity()) || !GameManager.gameStarted) return;
        if (!GameManager.deadRunnerList.contains(event.getEntity())) GameManager.deadRunnerList.add(event.getEntity());
        if (GameManager.allRunnerDead() || GameManager.deathMode == GameManager.deathModeEnum.END_GAME) {
            GameManager.runnerList.forEach(runner -> runner.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You lost!")));
            GameManager.hunterList.forEach(hunter -> hunter.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You win!")));
            GameManager.stopGame();
        } else if (GameManager.deathMode == GameManager.deathModeEnum.SWITCH_TO_HUNTER && !GameManager.allRunnerDead()) {
            GameManager.deadRunnerList.remove(event.getEntity());
            GameManager.runnerList.forEach(runner -> {
                if (runner != event.getEntity()) {
                    runner.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&4Your teammate, %s died, so he/her got changed to hunter team.", event.getEntity())));
                }
            });
            GameManager.hunterList.forEach(hunter -> hunter.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&4The runner %s died, so he/her got changed to hunter team.", event.getEntity().getName()))));
            GameManager.addHunter(event.getEntity());
        } else if (GameManager.deathMode == GameManager.deathModeEnum.SPECTATOR && !GameManager.allRunnerDead()) {
            GameManager.deadRunnerList.remove(event.getEntity());
            GameManager.removeRunner(event.getEntity());
            GameManager.runnerList.forEach(runner -> {
                if (runner != event.getEntity()) {
                    runner.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&4Your teammate, %s died, so he/her got to spectate.", event.getEntity().getName())));
                }
            });
            GameManager.hunterList.forEach(hunter -> hunter.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&4The runner %s died, so he/her got to spectate.", event.getEntity().getName()))));
            event.getEntity().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You died! you got to spectate!"));
            event.getEntity().setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onRunnerWalk(PlayerMoveEvent event) {
        if (!GameManager.runnerList.isEmpty() && !GameManager.runnerList.contains(event.getPlayer()) || !GameManager.gameStarted) return;
        GameManager.hunterList.forEach(hunter -> {
            Player followingPlayer = GameManager.getFollowedPlayer(hunter);
            if (followingPlayer != null) hunter.setCompassTarget(event.getPlayer().getLocation());
        });
    }

}