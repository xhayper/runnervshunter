package com.hayper.speedrunnervshunter.Commands;

import com.hayper.speedrunnervshunter.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopGame implements CommandExecutor {

    Main pl;

    public StopGame(Main pl) {
        this.pl = pl;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Main.GS.stopGame();
        return true;
    }
}
