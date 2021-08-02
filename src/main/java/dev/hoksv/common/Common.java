package dev.hoksv.common;

import org.bukkit.plugin.java.JavaPlugin;

public class Common {
    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        try {
         if(plugin == null)
             throw new IllegalAccessException("You're using an API that requires a Bukkit Plugin to be initialized. \nMake sure to call Common.setPlugin(<Plugin>); in your onEnable().");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        Common.plugin = plugin;
    }
}


