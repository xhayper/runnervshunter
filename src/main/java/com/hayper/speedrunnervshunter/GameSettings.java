package com.hayper.speedrunnervshunter;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameSettings {

    private final ArrayList<UUID> hunterList = new ArrayList<>();
    private final ArrayList<UUID> runnerList = new ArrayList<>();
    private final HashMap<UUID, Integer> followingMap = new HashMap<>();
    private boolean prepareTimeup = false;
    private int prepareTime = 30;
    private boolean gameStarted = false;

    private void resetGame() {
        prepareTime = 30;
        gameStarted = false;
        prepareTimeup = false;
        followingMap.clear();
        hunterList.clear();
        runnerList.clear();
    }

    public void addHunter(Player player) {
        if (hunterList.isEmpty() || !hunterList.contains(player.getUniqueId())) hunterList.add(player.getUniqueId());
        if (!runnerList.isEmpty()) runnerList.remove(player.getUniqueId());
    }

    public void addRunner(Player player) {
        if (!runnerList.contains(player.getUniqueId())) runnerList.add(player.getUniqueId());
        if (!hunterList.isEmpty()) hunterList.remove(player.getUniqueId());
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public UUID getFollowing(Player player) {
        return runnerList.get(followingMap.get(player.getUniqueId()));
    }

    public UUID nextFollower(Player player) {
        followingMap.put(player.getUniqueId(), followingMap.get(player.getUniqueId())+1);
        if(followingMap.get(player.getUniqueId()) >= runnerList.size()) followingMap.put(player.getUniqueId(), 0);
        return runnerList.get(followingMap.get(player.getUniqueId()));
    }

    public boolean isPrepareTimeup() {
        return prepareTimeup;
    }

    private void startPrepareTimer(Main pl) {
        BukkitScheduler scheduler = pl.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(pl, () -> prepareTimeup = true, prepareTime*20);
    }

    public boolean startGame(Main pl) {
        if (hunterList.size() >= 1 && runnerList.size() >= 1 && !gameStarted) {
            gameStarted = true;
            hunterList.forEach(hunterUUID -> {
                Player hunter = Bukkit.getPlayer(hunterUUID);
                if (hunter != null) {
                    hunter.setGameMode(GameMode.SURVIVAL);
                    hunter.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
                    hunter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, prepareTime*20, 1));
                }
                followingMap.put(hunterUUID, 0);
            });
            runnerList.forEach(runnerUUID -> {
                Player runner = Bukkit.getPlayer(runnerUUID);
                if (runner != null) {
                    runner.setGameMode(GameMode.SURVIVAL);
                }
            });
            startPrepareTimer(pl);
        }
        return gameStarted;
    }

    public void stopGame() {
        resetGame();
    }

    public ArrayList<UUID> getHunterList() {
        return hunterList;
    }

    public ArrayList<UUID> getRunnerList() {
        return runnerList;
    }

    public boolean isHunter(Player p) {
        return hunterList.contains(p.getUniqueId());
    }

    public boolean isRunner(Player p) {
        return runnerList.contains(p.getUniqueId());
    }


    public boolean isGameStarted() {
        return gameStarted;
    }

    public int getPrepareTime() { return prepareTime; }

}
