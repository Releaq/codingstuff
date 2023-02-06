package managers.gui;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.Main;
import net.md_5.bungee.api.chat.TextComponent;

public class GUI {
    
    protected static final GUIItem filler = new GUIItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    
    public final ArrayList<GUIItem> items = new ArrayList<GUIItem>();
    
    public final Inventory inv;
    
    public ArrayList<GUIAction> closeActions = new ArrayList<GUIAction>();
    
    public boolean preventClose = false;
    
    public static void initGUICreator(final Main plugin) {
        final ItemMeta meta = filler.item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "-");
        filler.item.setItemMeta(meta);
        
        Bukkit.getServer().getPluginManager().registerEvents(new GUIListener(plugin), plugin);
    }
    
    public GUI(final String title, final int size, final boolean preventClose) {
        this.preventClose = preventClose;
        inv = Bukkit.createInventory(null, size, title);
        for (int i = 0; i < size; i++) {
            items.add(filler);
            inv.setItem(i, filler.item);
        }
        GUIListener.guis.add(this);
    }
    
    @SuppressWarnings("unchecked")
    public void handleAction(final GUIAction action, final Player player) {
        switch (action.actionType) {
        case CLOSE:
            player.closeInventory();
            break;
        case REDIRECT:
            player.openInventory(((GUI)action.actionData[0]).inv);
            break;
        case ADD_ITEM:
            this.addItem(((GUIItem)action.actionData[0]));
            break;
        case CLOSE_FOR_ALL_PLAYERS:
            for (final HumanEntity ply : this.inv.getViewers())
                ply.closeInventory();
            break;
        case KILL_PLAYER:
            ((Player)action.actionData[0]).setHealth(0);
            break;
        case MOVE_ITEM_OVERWRITE:
        {
            this.setItem(((int)action.actionData[1]), this.items.get(((int)action.actionData[0])));
            this.setItem(((int)action.actionData[0]), filler);
            break;
        }
        case MOVE_ITEM_SWAP:
        {
            try {
                final GUIItem tempFrom = this.items.get((int)action.actionData[0]).clone();
                this.setItem((int)action.actionData[0], this.items.get((int)action.actionData[1]));
                this.setItem((int)action.actionData[1], tempFrom);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            break;
        }
        case SET_ITEM:
            this.setItem(((int)action.actionData[0]), ((GUIItem)action.actionData[1]));
            break;
        case SET_ITEM_META:
            this.items.get(((int)action.actionData[0])).item.setItemMeta(((ItemMeta)action.actionData[1]));
            break;
        case PLAY_NOTE_FOR_PLAYER:
            player.playNote(((Location)action.actionData[0]), ((Instrument)action.actionData[1]), ((Note)action.actionData[2]));
            break;
        case PLAY_SOUND_FOR_PLAYER:
            player.playSound(((Location)action.actionData[0]), ((Sound)action.actionData[1]), ((float)action.actionData[2]), ((float)action.actionData[3]));
            break;
        case UPDATE_GUI_FOR_CLIENT:
            this.updateGUIForClient();
            break;
        case SEND_TEXT_COMPONENT:
            ((Player)action.actionData[0]).sendMessage("" + ((TextComponent)action.actionData[1]));
            break;
        case SEND_MESSAGE:
            ((Player)action.actionData[0]).sendMessage(((String)action.actionData[1]));
            break;
        case EXECUTE_SQL_UPDATE_STATEMENT:
            try {
                ((PreparedStatement)action.actionData[0]).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        case EXECUTE_SQL_QUERY_STATEMENT:
            try {
                ((Pointer<ResultSet>)action.actionData[1]).value = ((PreparedStatement)action.actionData[0]).executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        case EXECUTE_FUNCTION:
            try {
                final Class<?> Class = ((Class<?>)action.actionData[0]);
                ((Pointer<Object>)action.actionData[4]).value = Class.getDeclaredMethod((String)action.actionData[1], (Class<?>[])action.actionData[2]).invoke(Class.newInstance(), (Object[])action.actionData[3]);
            } catch (final NoSuchMethodException e) {
                e.printStackTrace();
            } catch (final SecurityException e) {
                e.printStackTrace();
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
            } catch (final InstantiationException e) {
                e.printStackTrace();
            }
            break;
        }
    }
    
    public void handleActions(final ArrayList<GUIAction> actions, final Player player) {
        for (final GUIAction action : actions)
            this.handleAction(action, player);
    }
    
    public void handleAction(final GUIAction action, final HumanEntity player) {
        this.handleAction(action, (Player)player);
    }
    
    public void handleActions(final ArrayList<GUIAction> actions, final HumanEntity player) {
        this.handleActions(actions, (Player)player);
    }
    
    public void addItem(final GUIItem item) {
        this.items.set(this.items.indexOf(filler), item);
    }
    
    public void setItem(final int index, final GUIItem item) {
        this.items.set(index, item);
    }
    
    public void updateGUIForClient() {
        for (int i = 0; i < items.size(); i++)
            inv.setItem(i, items.get(i).item);
    }

    public void openInventoryForPlayer(final Player player) {
        player.openInventory(this.inv);
    }
    
    public void addCloseAction(final GUIAction action) {
        this.closeActions.add(action);
    }
    
    public void addCloseActions(final ArrayList<GUIAction> actions) {
        for (final GUIAction action : actions)
            this.addCloseAction(action);
    }
    
}
