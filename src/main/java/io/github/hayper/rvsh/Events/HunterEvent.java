package io.github.hayper.rvsh.Events;

import io.github.hayper.rvsh.Main;
import io.github.hayper.rvsh.Utility.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class HunterEvent implements Listener {

    Main pl;

    public HunterEvent(Main pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onHunterHoldItem(PlayerItemHeldEvent event) {
        if (!GameManager.hunterList.isEmpty() && !GameManager.hunterList.contains(event.getPlayer()) || !GameManager.gameStarted) return;
        BukkitRunnable compassDisplay = new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack heldItemStackMainHand = event.getPlayer().getInventory().getItemInMainHand();
                ItemStack heldItemStackOffHand = event.getPlayer().getInventory().getItemInOffHand();
                if (heldItemStackMainHand.getType() != Material.COMPASS && heldItemStackOffHand.getType() != Material.COMPASS) {
                    this.cancel();
                    return;
                }
                Player followedPlayer = GameManager.getFollowedPlayer(event.getPlayer());
                if (followedPlayer != null) {
                    if (event.getPlayer().getWorld().getEnvironment() != followedPlayer.getWorld().getEnvironment()) {
                        event.getPlayer().sendActionBar(ChatColor.translateAlternateColorCodes('&', String.format("[&4!&f] &9%s &f[&4!&f]", getEnvironmentName(followedPlayer.getWorld().getEnvironment()))));
                        resetToDefault(heldItemStackMainHand, heldItemStackOffHand);
                    } else {
                        event.getPlayer().sendActionBar(ChatColor.translateAlternateColorCodes('&', String.format("[&4%sm&f] &9Following %s &f[&4%sm&f]", Math.round(event.getPlayer().getLocation().distance(followedPlayer.getLocation())), followedPlayer.getDisplayName(), Math.round(event.getPlayer().getLocation().distance(followedPlayer.getLocation())))));
                        if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER) {
                            if (heldItemStackMainHand.getType() == Material.COMPASS) {
                                CompassMeta compassMeta = ((CompassMeta) heldItemStackMainHand.getItemMeta());
                                compassMeta.setLodestone(followedPlayer.getLocation());
                                compassMeta.setLodestoneTracked(true);
                                compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&rTracker"));
                                heldItemStackMainHand.setItemMeta(compassMeta);
                            }
                            else if (heldItemStackOffHand.getType() == Material.COMPASS) {
                                CompassMeta compassMeta = ((CompassMeta) heldItemStackOffHand.getItemMeta());
                                compassMeta.setLodestone(followedPlayer.getLocation());
                                compassMeta.setLodestoneTracked(true);
                                compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&rTracker"));
                                heldItemStackOffHand.setItemMeta(compassMeta);
                            }
                        } else {
                            resetToDefault(heldItemStackMainHand, heldItemStackOffHand);
                            event.getPlayer().setCompassTarget(followedPlayer.getLocation());
                        }
                    }
                }
            }
        };
        compassDisplay.runTaskTimerAsynchronously(pl, 0, 1);
    }

    private void resetToDefault(ItemStack heldItemStackMainHand, ItemStack heldItemStackOffHand) {
        if (heldItemStackMainHand.getType() == Material.COMPASS) {
            CompassMeta compassMeta = ((CompassMeta) heldItemStackMainHand.getItemMeta());
            compassMeta.setLodestoneTracked(false);
            compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&rTracker"));
            heldItemStackMainHand.setItemMeta(compassMeta);
        }
        else if (heldItemStackOffHand.getType() == Material.COMPASS) {
            CompassMeta compassMeta = ((CompassMeta) heldItemStackOffHand.getItemMeta());
            compassMeta.setLodestoneTracked(false);
            compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&rTracker"));
            heldItemStackOffHand.setItemMeta(compassMeta);
        }
    }

    @EventHandler
    public void onHunterDropItem(PlayerDropItemEvent event) {
        if (!GameManager.hunterList.isEmpty() && !GameManager.hunterList.contains(event.getPlayer()) || !GameManager.gameStarted) return;
        if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) event.setCancelled(true);
    }

    @EventHandler
    public void onHunterInteract(PlayerInteractEvent event) {
        if (!GameManager.hunterList.isEmpty() && !GameManager.hunterList.contains(event.getPlayer()) || !GameManager.gameStarted) return;
        if (event.getMaterial() == Material.COMPASS) {
            GameManager.addCompassIndex(event.getPlayer());
        }
    }

    @EventHandler
    public void onHunterRespawn(PlayerRespawnEvent event) {
        if (!GameManager.hunterList.isEmpty() && !GameManager.hunterList.contains(event.getPlayer()) || !GameManager.gameStarted) return;
        event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
    }

    @EventHandler
    public void onHunterDied(PlayerDeathEvent event) {
        if (!GameManager.hunterList.isEmpty() && !GameManager.hunterList.contains(event.getEntity()) || !GameManager.gameStarted) return;
        event.getDrops().forEach(itemStack -> { if (itemStack.getType() == Material.COMPASS) event.getDrops().remove(itemStack); });
    }

    @EventHandler
    public void onHunterWalk(PlayerMoveEvent event) {
        if (!GameManager.hunterList.isEmpty() && !GameManager.hunterList.contains(event.getPlayer()) || !GameManager.gameStarted) return;
        Player followedPlayer = GameManager.getFollowedPlayer(event.getPlayer());
        if (followedPlayer != null) event.getPlayer().setCompassTarget(followedPlayer.getLocation());
    }

    private String getEnvironmentName(World.Environment environment) {
        return switch (environment) {
            case NORMAL -> "Overworld";
            case NETHER -> "Nether";
            case THE_END -> "The End";
        };
    }

}