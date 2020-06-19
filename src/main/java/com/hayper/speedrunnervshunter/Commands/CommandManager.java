package com.hayper.speedrunnervshunter.Commands;

import com.hayper.speedrunnervshunter.Main;
import com.hayper.speedrunnervshunter.TabCompleter.NoArgsCompleter;
import com.hayper.speedrunnervshunter.TabCompleter.PrepareTimeCompleter;
import com.hayper.speedrunnervshunter.TabCompleter.SetRHCompleter;
import org.bukkit.command.PluginCommand;

public class CommandManager {
    public static void registerCommands(Main pl) {
        PluginCommand setHunter = pl.getCommand("sethunter");
        PluginCommand setRunner= pl.getCommand("setrunner");
        PluginCommand setPrepareTime = pl.getCommand("setpreparetime");
        PluginCommand startGame = pl.getCommand("startgame");
        PluginCommand stopGame = pl.getCommand("stopgame");
        if (setHunter != null &&
                setRunner != null &&
                setPrepareTime != null &&
                startGame != null &&
                stopGame != null)
        {
            setHunter.setTabCompleter(new SetRHCompleter());
            setHunter.setExecutor(new SetHunter(pl));
            setRunner.setTabCompleter(new SetRHCompleter());
            setRunner.setExecutor(new SetRunner(pl));
            setPrepareTime.setTabCompleter(new PrepareTimeCompleter());
            setPrepareTime.setExecutor(new SetPrepareTime(pl));
            startGame.setTabCompleter(new NoArgsCompleter());
            startGame.setExecutor(new StartGame(pl));
            stopGame.setTabCompleter(new NoArgsCompleter());
            stopGame.setExecutor(new StopGame(pl));
        }
    }
}
