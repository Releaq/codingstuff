package main;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import data.player.PlayerSaveData;
import data.player.PlayerSessionData;

public class EventListener implements Listener {

    // Do not access data with another PlayerJoinEvent using EventPriority.LOWEST. You will be reading null!
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent e)
    {
        final File dataFile = PlayerSaveData.getDataFile(e.getPlayer().getUniqueId());
        if (dataFile.exists()) {
            final PlayerSaveData data = PlayerSaveData.loadData(e.getPlayer().getUniqueId());
            PlayerSessionData.PlayerData.put(e.getPlayer().getUniqueId(), new PlayerSessionData(data));
        } else {
            try {
                dataFile.createNewFile();
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
            PlayerSessionData.PlayerData.put(e.getPlayer().getUniqueId(), new PlayerSessionData());
        }
    }
    
    @EventHandler
    public void onPlayerDisconnect(final PlayerQuitEvent e)
    {
        PlayerSessionData.PlayerData.get(e.getPlayer().getUniqueId()).savedata.saveData(e.getPlayer().getUniqueId());
        PlayerSessionData.PlayerData.remove(e.getPlayer().getUniqueId());
    }

}
