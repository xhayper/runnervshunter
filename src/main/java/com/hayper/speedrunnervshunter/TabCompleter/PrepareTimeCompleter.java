package com.hayper.speedrunnervshunter.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrepareTimeCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("10", "30", "60");
        }
        return Collections.singletonList("");
    }
}
