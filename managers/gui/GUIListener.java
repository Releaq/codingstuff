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
    public void onInventoryClick(final InventoryClickEvent e) {
        for (final GUI gui : guis)
            if (e.getInventory().equals(gui.inv)) {
                e.setCancelled(true);
                final GUIItem item = gui.items.get(e.getRawSlot());
                gui.handleActions(item.globalClickActions, e.getWhoClicked());
                switch (e.getClick()) {
                case LEFT:
                    gui.handleActions(item.globalLeftClickActions, e.getWhoClicked());
                    gui.handleActions(item.regularLeftClickActions, e.getWhoClicked());
                    break;
                case RIGHT:
                    gui.handleActions(item.globalRightClickActions, e.getWhoClicked());
                    gui.handleActions(item.regularRightClickActions, e.getWhoClicked());
                    break;
                case SHIFT_LEFT:
                    gui.handleActions(item.globalLeftClickActions, e.getWhoClicked());
                    gui.handleActions(item.shiftLeftClickActions, e.getWhoClicked());
                    break;
                case SHIFT_RIGHT:
                    gui.handleActions(item.globalRightClickActions, e.getWhoClicked());
                    gui.handleActions(item.shiftRightClickActions, e.getWhoClicked());
                    break;
                }
            }
    }
    
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        for (final GUI gui : guis)
            if (e.getInventory().equals(gui.inv))
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (gui.preventClose)
                            e.getPlayer().openInventory(e.getInventory());
                        if (e.getPlayer().getOpenInventory().getType() == InventoryType.CRAFTING)
                            gui.handleActions(gui.closeActions, e.getPlayer());
                    }
                }, 1);
    }
    
}
