package managers.gui;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class GUIItem implements Cloneable {
    
    public ArrayList<GUIAction> globalClickActions = new ArrayList<GUIAction>();
    
    public ArrayList<GUIAction> globalLeftClickActions = new ArrayList<GUIAction>();
    public ArrayList<GUIAction> globalRightClickActions = new ArrayList<GUIAction>();
    
    public ArrayList<GUIAction> regularLeftClickActions = new ArrayList<GUIAction>();
    public ArrayList<GUIAction> regularRightClickActions = new ArrayList<GUIAction>();
    
    public ArrayList<GUIAction> shiftLeftClickActions = new ArrayList<GUIAction>();
    public ArrayList<GUIAction> shiftRightClickActions = new ArrayList<GUIAction>();
    
    public ItemStack item;
    
    public GUIItem(final ItemStack item) {
        this.item = item;
    }
    
    public void addGlobalClickAction(final GUIAction action) {
        globalClickActions.add(action);
    }
    
    public void addGlobalLeftClickAction(final GUIAction action) {
        globalLeftClickActions.add(action);
    }
    
    public void addGlobalRightClickAction(final GUIAction action) {
        globalRightClickActions.add(action);
    }
    
    public void addShiftLeftClickAction(final GUIAction action) {
        shiftLeftClickActions.add(action);
    }
    
    public void addShiftRightClickAction(final GUIAction action) {
        shiftRightClickActions.add(action);
    }
    
    public void addRegularLeftClickAction(final GUIAction action) {
        regularLeftClickActions.add(action);
    }
    
    public void addRegularRightClickAction(final GUIAction action) {
        regularRightClickActions.add(action);
    }
    
    public GUIItem clone() throws CloneNotSupportedException
    {
        return (GUIItem)super.clone();
    }
    
}
