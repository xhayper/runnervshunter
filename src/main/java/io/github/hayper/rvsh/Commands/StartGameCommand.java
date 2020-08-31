package io.github.hayper.rvsh.Commands;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StartGameCommand implements CommandExecutor {

    Main pl;

    public StartGameCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (GameManager.startGame()) {
            GameManager.runnerList.forEach(runner -> {
                runner.getInventory().clear();
                runner.sendMessage("The game has started!");
            });
            GameManager.hunterList.forEach(hunter -> {
                hunter.getInventory().clear();
                hunter.getInventory().addItem(new ItemStack(Material.COMPASS));
                hunter.sendMessage("The game has started!");
                if (GameManager.prepareTime != 0) {
                    hunter.addPotionEffects(getHunterEffects());
                }
            });
        } else {
            sender.sendMessage("Need at least 1 hunter and 1 runner!");
        }
        return true;
    }

    private Collection<PotionEffect> getHunterEffects() {
        Collection<PotionEffect> potionEffects = new ArrayList<>();
        potionEffects.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, GameManager.prepareTime*20, 255, false, false));
        potionEffects.add(new PotionEffect(PotionEffectType.BLINDNESS, (GameManager.prepareTime+1)*20, 255, false, false));
        potionEffects.add(new PotionEffect(PotionEffectType.SLOW, GameManager.prepareTime*20, 255, false, false));
        potionEffects.add(new PotionEffect(PotionEffectType.JUMP, GameManager.prepareTime*20, 129, false, false));
        return potionEffects;
    }

    public TabCompleter getTabCompleter() {
        return (sender, command, alias, args) -> List.of("");
    }

}