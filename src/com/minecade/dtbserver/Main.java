package com.minecade.dtbserver;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        new Listeners(this);
        getServer().getWorlds().get(0).setSpawnLocation(0, 19, -20);
        for (World world : getServer().getWorlds()) {
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setTime(6000);
            for (Entity entity : world.getEntities()) {
                entity.remove();
            }
        }
    }

}
