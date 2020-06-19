package com.hayper.speedrunnervshunter.TabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetRHCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            List<String> playerNames = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(p -> playerNames.add(p.getName()));
            return playerNames;
        }
        return Collections.singletonList("");
    }
}
