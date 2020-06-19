package com.hayper.speedrunnervshunter.Events;

import com.hayper.speedrunnervshunter.Main;

public class EventManager {
    public static void registerEvents(Main pl) {
        pl.getServer().getPluginManager().registerEvents(new HunterEvent(), pl);
        pl.getServer().getPluginManager().registerEvents(new RunnerEvent(), pl);
    }
}
