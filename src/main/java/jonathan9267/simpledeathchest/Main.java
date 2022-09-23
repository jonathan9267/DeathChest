package jonathan9267.simpledeathchest;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new DeathChest(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
