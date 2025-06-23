package htt;

import htt.commands.DoppelgangerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HTTdoppelganger extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("httdgg").setExecutor(new DoppelgangerCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
