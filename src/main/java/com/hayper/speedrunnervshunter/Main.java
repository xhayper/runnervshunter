package com.hayper.speedrunnervshunter;

import com.hayper.speedrunnervshunter.Commands.CommandManager;
import com.hayper.speedrunnervshunter.Events.EventManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static GameSettings GS = new GameSettings();

    @Override
    public void onEnable() {
        CommandManager.registerCommands(this);
        EventManager.registerEvents(this);
    }
}
