package com.hayper.speedrunnervshunter.Commands;

import com.hayper.speedrunnervshunter.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartGame implements CommandExecutor {

    Main pl;

    public StartGame(Main pl) {
        this.pl = pl;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(Main.GS.startGame(pl)) { pl.getServer().broadcastMessage("The game has been started!"); return true; }
        commandSender.sendMessage("Error! You need to have atleast 1 hunter and 1 runner!");
        return true;
    }
}
