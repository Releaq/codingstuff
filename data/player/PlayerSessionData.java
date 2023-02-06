package data.player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSessionData {
    
    public static HashMap<UUID, PlayerSessionData> PlayerData = new HashMap<>();
    
    public PlayerSaveData savedata = new PlayerSaveData();
    
    // Put variables that should be deallocated on disconnect here
    
    public PlayerSessionData(final PlayerSaveData data) {
        this.savedata = data;
    }

    public PlayerSessionData() {}
    
}
