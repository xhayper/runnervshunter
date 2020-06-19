package com.hayper.speedrunnervshunter.Commands;

import com.hayper.speedrunnervshunter.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetPrepareTime implements CommandExecutor {

    Main pl;

    public SetPrepareTime(Main pl) {
        this.pl = pl;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            if (Integer.parseInt(strings[0]) >= 0) {
                Main.GS.setPrepareTime(Integer.parseInt(strings[0]));
                commandSender.sendMessage(String.format("Prepare time has been set to %s second(s)!", strings[0]));
                return true;
            }
        }
        return false;
    }
}
