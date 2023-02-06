package main;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import data.player.PlayerSessionData;

public class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdir();
        final File file = new File(this.getDataFolder().toString() + "/PlayerData/");
        if (!file.exists())
            file.mkdir();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        GUI.initGUICreator(this);
    }
    
    @Override
    public void onDisable() {
        for (final Player player : getServer().getOnlinePlayers())
            PlayerSessionData.PlayerData.get(player.getUniqueId()).savedata.saveData(player.getUniqueId());
    }
}
