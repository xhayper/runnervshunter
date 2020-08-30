package io.github.hayper.rvsh.Commands;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import org.bukkit.command.*;

import java.util.List;

public class StopGameCommand implements CommandExecutor {

    Main pl;

    public StopGameCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!GameManager.gameStarted) {
            sender.sendMessage("Game haven't been started!");
            return true;
        }
        GameManager.runnerList.forEach(runner -> {
            runner.sendMessage(String.format("The game has been stopped by %s!", sender.getName()));
        });
        GameManager.hunterList.forEach(hunter -> {
            hunter.sendMessage(String.format("The game has been stopped by %s!", sender.getName()));
        });
        GameManager.stopGame();
        return true;
    }

    public TabCompleter getTabCompleter() {
        return (sender, command, alias, args) -> List.of("");
    }

}