package io.github.hayper.rvsh.Commands;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import org.bukkit.command.*;
import java.util.List;

public class SetDeathModeCommand implements CommandExecutor {

    Main pl;

    public SetDeathModeCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            GameManager.deathModeEnum.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException exception) {
            sender.sendMessage("Invalid Death Mode!");
            return true;
        }
        sender.sendMessage(String.format("Death mode has been set to %s!", args[0]));
        GameManager.setDeathMode(GameManager.deathModeEnum.valueOf(args[0].toUpperCase()));
        return true;
    }

    public TabCompleter getTabCompleter() {
        return (sender, command, alias, args) -> {
            if (args.length == 1) return List.of("SPECTATOR", "SWITCH_TO_HUNTER", "END_GAME");
            return List.of("");
        };
    }

}