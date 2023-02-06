package managers.gui;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.chat.TextComponent;

public class GUIAction {

    public Object[] actionData = null;
    public GUIActionType actionType;
    
    public GUIAction(final GUIActionType actionType) {
        this.actionType = actionType;
    }
    
    public GUIAction(final GUIActionType actionType, final Object... actionData) {
        this.actionType = actionType;
        this.actionData = actionData;
    }
    
    // Templates for actions that require data
    
    public static GUIAction createRedirect(final GUI redirect) {
        return new GUIAction(GUIActionType.REDIRECT, redirect);
    }
    
    public static GUIAction createAddItem(final GUIItem item) {
        return new GUIAction(GUIActionType.ADD_ITEM, item);
    }
    
    public static GUIAction createMoveItemOverwrite(final int from, final int to) {
        return new GUIAction(GUIActionType.MOVE_ITEM_OVERWRITE, from, to);
    }
    
    public static GUIAction createMoveItemSwap(final int from, final int to) {
        return new GUIAction(GUIActionType.MOVE_ITEM_SWAP, from, to);
    }
    
    public static GUIAction createSetItem(final int index, final GUIItem item) {
        return new GUIAction(GUIActionType.SET_ITEM, index, item);
    }
    
    public static GUIAction createSetItemMeta(final int index, final ItemMeta meta) {
        return new GUIAction(GUIActionType.SET_ITEM_META, index, meta);
    }
    
    public static GUIAction createPlayNoteForPlayer(final Location location, final Instrument instrument, final Note note) {
        return new GUIAction(GUIActionType.PLAY_NOTE_FOR_PLAYER, location, instrument, note);
    }
    
    public static GUIAction createPlaySoundForPlayer(final Location location, final Sound sound, final float volume, final float pitch) {
        return new GUIAction(GUIActionType.PLAY_SOUND_FOR_PLAYER, location, sound, volume, pitch);
    }
    
    public static GUIAction createKillPlayer(final Location location, final Player player) {
        return new GUIAction(GUIActionType.KILL_PLAYER, player);
    }
    
    public static GUIAction createSendMessage(final Location player, final String msg) {
        return new GUIAction(GUIActionType.SEND_MESSAGE, player, msg);
    }
    
    public static GUIAction createSendTextComponent(final Player player, final TextComponent component) {
        return new GUIAction(GUIActionType.SEND_TEXT_COMPONENT, player, component);
    }
    
    public static GUIAction createExecuteSQLUpdateStatement(final PreparedStatement statement) {
        return new GUIAction(GUIActionType.EXECUTE_SQL_UPDATE_STATEMENT, statement);
    }
    
    public static GUIAction createExecuteSQLQueryStatement(final PreparedStatement statement, final Pointer<ResultSet> resultPointer) {
        return new GUIAction(GUIActionType.EXECUTE_SQL_QUERY_STATEMENT, statement, resultPointer);
    }
    
    public static GUIAction createExecuteFunction(final Class<?> methodClass, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters, final Pointer<?> returnValuePointer) {
        return new GUIAction(GUIActionType.EXECUTE_FUNCTION, methodClass, methodName, parameterTypes, parameters, returnValuePointer);
    }
    
}
