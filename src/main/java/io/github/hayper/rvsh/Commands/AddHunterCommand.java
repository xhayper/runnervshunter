package io.github.hayper.rvsh.Commands;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class AddHunterCommand implements CommandExecutor {

    Main pl;

    public AddHunterCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Player> successfulPlayers = new ArrayList<>();
        StringBuilder successfulText = new StringBuilder();
        for (String playerName : args) {
            Player targetPlayer = pl.getServer().getPlayerExact(playerName);
            if (targetPlayer != null) {
                if (!GameManager.hunterList.contains(targetPlayer)) {
                    GameManager.addHunter(targetPlayer);
                    successfulPlayers.add(targetPlayer);
                    successfulText.append(String.format(targetPlayer.getName().equals(args[args.length - 1]) ? args.length == 1 ? "%s" : "and %s" : "%s, ", targetPlayer.getDisplayName()));
                }
            }
        }
        if (successfulPlayers.size() == 0) {
            StringBuilder errorText = new StringBuilder();
            for(String targetPlayer : args) {
                errorText.append(String.format(targetPlayer.equals(args[args.length - 1]) ? args.length == 1 ? "%s" : "and %s" : "%s, ", targetPlayer));
            }
            sender.sendMessage(String.format("Failed to add %s to hunter team", errorText.toString()));
        } else {
            sender.sendMessage(String.format("Added %s to hunter team", successfulText.toString()));
        }
        return true;
    }

    public TabCompleter getTabCompleter() {
        return (sender, command, alias, args) -> {
            List<String> onlinePlayerList = new ArrayList<>();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                onlinePlayerList.add(player.getName());
            }
            return onlinePlayerList;
        };
    }

}