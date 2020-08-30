package io.github.hayper.rvsh.Commands;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import io.github.hayper.rvsh.Utility.Logger;
import org.bukkit.command.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SetPrepareTimeCommand implements CommandExecutor {

    Main pl;

    public SetPrepareTimeCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(String.format("Prepare time has been set to %s seconds!", Integer.parseInt(args[0])));
        GameManager.setPrepareTime(Integer.parseInt(args[0]));
        return true;
    }

    public TabCompleter getTabCompleter() {
        return (sender, command, alias, args) -> {
            if (args.length == 1) return List.of("10", "20", "30", "60", "120");
            return List.of("");
        };
    }

}