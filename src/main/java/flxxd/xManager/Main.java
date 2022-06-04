package flxxd.xManager;

import flxxd.xManager.commands.Manager;
import flxxd.xManager.helpers.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable () {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        new Manager();
    }

    @Override
    public void onDisable () {
        // Plugin shutdown logic
    }

    public static Main getInstance () {
        return instance;
    }
    public static String getValue (String value) {
        return Main.getInstance().getConfig().getString(value).replace("&", "§");
    }
}
