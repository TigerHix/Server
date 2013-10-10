package com.minecade.dtbserver;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Listeners implements Listener {

    private Main plugin;

    public Listeners(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent evt) {
        if (!evt.getPlayer().isOp()) {
            evt.setCancelled(true);
        } else {
            for (int line = 0; line < 4; line ++) {
                evt.setLine(line, ChatColor.translateAlternateColorCodes('&', evt.getLine(line)));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent evt) {
        evt.setCancelled(true);
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (evt.getRecipients().size() > 0)
                    for (Player player : evt.getPlayer().getWorld().getPlayers())
                        player.sendMessage(ChatColor.GRAY + evt.getPlayer().getDisplayName() + ChatColor.GRAY + ": " + evt.getMessage());
            }
        }, 1);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent evt) {
        evt.setJoinMessage(null);
        for (Player player : plugin.getServer().getWorld("Lobby").getPlayers()) {
            player.sendMessage(ChatColor.GRAY + "* " + evt.getPlayer().getDisplayName() + ChatColor.GRAY + " joined the server.");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent evt) {
        evt.setQuitMessage(null);
        for (Player player : plugin.getServer().getWorld("Lobby").getPlayers()) {
            player.sendMessage(ChatColor.GRAY + "* " + evt.getPlayer().getDisplayName() + ChatColor.GRAY + " left the server.");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent evt) {
        if (evt.getBlock().getWorld().getName().equalsIgnoreCase("Lobby") && !evt.getPlayer().isOp()) {
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent evt) {
        if (evt.getBlock().getWorld().getName().equalsIgnoreCase("Lobby") && !evt.getPlayer().isOp()) {
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onThrowItem(PlayerDropItemEvent evt) {
        if (evt.getItemDrop().getWorld().getName().equalsIgnoreCase("Lobby") && !evt.getPlayer().isOp()) {
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent evt) {
        if (evt.getItem().getWorld().getName().equalsIgnoreCase("Lobby") && !evt.getPlayer().isOp()) {
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent evt) {
        evt.setCancelled(true);
        World world = evt.getWorld();
        world.setStorm(false);
        world.setThundering(false);
        evt.getWorld().setWeatherDuration(9999999);
    }

}
