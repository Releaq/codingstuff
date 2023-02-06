package managers.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import main.Main;

public class GUIListener implements Listener {

    private static Main plugin;
    
    protected static final ArrayList<GUI> guis = new ArrayList<GUI>();
    
    @SuppressWarnings("static-access")
    public GUIListener (final Main plugin) {
        this.plugin = plugin;
    }
    
    @SuppressWarnings("incomplete-switch")
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        for (final GUI gui : guis)
            if (event.getInventory().equals(gui.inv)) {
                event.setCancelled(true);
                final GUIItem item = gui.items.get(event.getRawSlot());
                gui.handleActions(item.globalClickActions, event.getWhoClicked());
                switch (event.getClick()) {
                case LEFT:
                    gui.handleActions(item.globalLeftClickActions, event.getWhoClicked());
                    gui.handleActions(item.regularLeftClickActions, event.getWhoClicked());
                    break;
                case RIGHT:
                    gui.handleActions(item.globalRightClickActions, event.getWhoClicked());
                    gui.handleActions(item.regularRightClickActions, event.getWhoClicked());
                    break;
                case SHIFT_LEFT:
                    gui.handleActions(item.globalLeftClickActions, event.getWhoClicked());
                    gui.handleActions(item.shiftLeftClickActions, event.getWhoClicked());
                    break;
                case SHIFT_RIGHT:
                    gui.handleActions(item.globalRightClickActions, event.getWhoClicked());
                    gui.handleActions(item.shiftRightClickActions, event.getWhoClicked());
                    break;
                }
            }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        for (final GUI gui : guis)
            if (event.getInventory().equals(gui.inv))
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (gui.preventClose)
                            event.getPlayer().openInventory(event.getInventory());
                        if (event.getPlayer().getOpenInventory().getType() == InventoryType.CRAFTING)
                            gui.handleActions(gui.closeActions, event.getPlayer());
                    }
                }, 1);
    }
    
}
