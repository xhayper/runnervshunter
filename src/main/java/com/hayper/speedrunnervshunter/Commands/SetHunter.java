package com.hayper.speedrunnervshunter.Commands;

import com.hayper.speedrunnervshunter.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetHunter implements CommandExecutor {

    Main pl;

    public SetHunter(Main pl) {
        this.pl = pl;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (Main.GS.isGameStarted()) {
            commandSender.sendMessage("Game already started!");
            return true;
        }

        if (strings.length == 0) {
            if (commandSender instanceof ConsoleCommandSender) return false;
            commandSender.sendMessage("Success! You have been added to hunter team!");
            Main.GS.addHunter((Player) commandSender);
            return true;
        } else if (strings.length == 1) {
            Player targetPlayer = Bukkit.getPlayerExact(strings[0]);
            if (targetPlayer == null) {
                commandSender.sendMessage("Error! That player is offline!");
                return true;
            } else if (!(commandSender instanceof ConsoleCommandSender) && ((Player) commandSender).getUniqueId() == targetPlayer.getUniqueId()) {
                commandSender.sendMessage("Success! You have been added to hunter team!");
                Main.GS.addHunter(targetPlayer);
                return true;
            } else {
                commandSender.sendMessage(String.format("Success! %s has been added to hunter team!", targetPlayer.getName()));
                Main.GS.addHunter(targetPlayer);
                return true;
            }
        }
        return false;
    }
}
