package io.github.hayper.rvsh;

import io.github.hayper.rvsh.Commands.*;
import io.github.hayper.rvsh.Events.HunterEvent;
import io.github.hayper.rvsh.Events.RunnerEvent;
import io.github.hayper.rvsh.Utility.Logger;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URISyntaxException;

public final class Main extends JavaPlugin {

    public static String modName = "RunnerVSHunter";

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new HunterEvent(this), this);
        getServer().getPluginManager().registerEvents(new RunnerEvent(this), this);
    }

    public void registerCommands() {
        PluginCommand setPrepareTimeCommand = getCommand("setPrepareTime");
        PluginCommand setDeathModeCommand = getCommand("setDeathMode");
        PluginCommand removeHunterCommand = getCommand("removeHunter");
        PluginCommand removeRunnerCommand = getCommand("removeRunner");
        PluginCommand addHunterCommand = getCommand("addHunter");
        PluginCommand addRunnerCommand = getCommand("addRunner");
        PluginCommand startGameCommand = getCommand("startGame");
        PluginCommand stopGameCommand = getCommand("stopGame");

        if (setPrepareTimeCommand != null &&
            setDeathModeCommand != null &&
            removeHunterCommand != null &&
            removeRunnerCommand != null &&
            addHunterCommand != null &&
            addRunnerCommand != null &&
            startGameCommand != null &&
            stopGameCommand != null) {
            SetPrepareTimeCommand setPrepareTimeCommand1 = new SetPrepareTimeCommand(this);
            SetDeathModeCommand setDeathModeCommand1 = new SetDeathModeCommand(this);
            RemoveHunterCommand removeHunterCommand1 = new RemoveHunterCommand(this);
            RemoveRunnerCommand removeRunnerCommand1 = new RemoveRunnerCommand(this);
            AddHunterCommand addHunterCommand1 = new AddHunterCommand(this);
            AddRunnerCommand addRunnerCommand1 = new AddRunnerCommand(this);
            StartGameCommand startGameCommand1 = new StartGameCommand(this);
            StopGameCommand stopGameCommand1 = new StopGameCommand(this);

            setPrepareTimeCommand.setTabCompleter(setPrepareTimeCommand1.getTabCompleter());
            setPrepareTimeCommand.setExecutor(setPrepareTimeCommand1);

            setDeathModeCommand.setTabCompleter(setDeathModeCommand1.getTabCompleter());
            setDeathModeCommand.setExecutor(setDeathModeCommand1);

            removeHunterCommand.setTabCompleter(removeHunterCommand1.getTabCompleter());
            removeHunterCommand.setExecutor(removeHunterCommand1);

            removeHunterCommand.setTabCompleter(removeRunnerCommand1.getTabCompleter());
            removeRunnerCommand.setExecutor(removeRunnerCommand1);

            addHunterCommand.setTabCompleter(addHunterCommand1.getTabCompleter());
            addHunterCommand.setExecutor(addHunterCommand1);

            addRunnerCommand.setTabCompleter(addRunnerCommand1.getTabCompleter());
            addRunnerCommand.setExecutor(addRunnerCommand1);

            startGameCommand.setTabCompleter(startGameCommand1.getTabCompleter());
            startGameCommand.setExecutor(startGameCommand1);

            stopGameCommand.setTabCompleter(stopGameCommand1.getTabCompleter());
            stopGameCommand.setExecutor(stopGameCommand1);
        } else {
            Logger.error("Error while loading commands.");
        }
    }

}