package io.github.hayper.rvsh.Utility;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {

    public enum deathModeEnum {
        SPECTATOR,
        SWITCH_TO_HUNTER,
        END_GAME
    }

    public static deathModeEnum deathMode = deathModeEnum.SWITCH_TO_HUNTER;
    public static HashMap<Player, Integer> compassIndex = new HashMap<>();
    public static List<Player> deadRunnerList = new ArrayList<>();
    public static List<Player> hunterList = new ArrayList<>();
    public static List<Player> runnerList = new ArrayList<>();
    public static Boolean gameStarted = false;
    public static Integer prepareTime = 10;

    public static void setDeathMode(deathModeEnum newDeathMode) {
        deathMode = newDeathMode;
    }

    public static Player getFollowedPlayer(Player player) {
        if (compassIndex.get(player) != null && runnerList.get(compassIndex.get(player)) != null) return runnerList.get(compassIndex.get(player));
        else if (compassIndex.get(player) == null && !runnerList.isEmpty() && runnerList.get(0) != null) return runnerList.get(0);
        else return player;
    }

    public static void addCompassIndex(Player player) {
        if (!runnerList.contains(player) && hunterList.contains(player)) {
            Integer currentIndex = compassIndex.get(player);
            if (currentIndex == null || currentIndex+1 >= runnerList.size()) {
                compassIndex.put(player, 0);
            } else {
                compassIndex.put(player, currentIndex+1);
            }
        }
    }

    public static void removeHunter(Player player) {
        hunterList.remove(player);
    }

    public static void removeRunner(Player player) {
        runnerList.remove(player);
    }

    public static void setPrepareTime(int time) {
        prepareTime = Math.max(0, time);
    }

    public static void addRunner(Player player) {
        hunterList.remove(player);
        runnerList.add(player);
    }

    public static void addHunter(Player player) {
        runnerList.remove(player);
        hunterList.add(player);
    }

    public static boolean allRunnerDead() {
        return deadRunnerList.size() >= runnerList.size();
    }

    public static Boolean startGame() {
        if (gameStarted && 1 > hunterList.size() || 1 > runnerList.size()) return false;
        gameStarted = true;
        return true;
    }

    public static void stopGame() {
        compassIndex.clear();
        hunterList.clear();
        runnerList.clear();
        gameStarted = false;
    }

}