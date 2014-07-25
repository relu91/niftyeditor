/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller;

import jada.ngeditor.controller.commands.Command;
import jada.ngeditor.controller.commands.CommandAction;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.undo.UndoManager;

/**
 * This class is the entry-point for the editor. It holds all the logic.
 * @author cris
 */
public class MainCrontroller {
    private UndoManager undoManager;
    private final GUIEditor editor;
    
    private MainCrontroller(){
        this.editor = new GUIEditor();
    }
    /**
     * Get an Observable that fire the events in the 
     * @see jada.ngeditor.listeners.events
     * @return 
     */
    public Observable getObservable(){
        return this.editor;
    }
    
    /**
     *
     * @param manager
     */
    public void setUndoManager(UndoManager manager){
        this.undoManager = manager;
    }
    
    public  <T extends Command> T  getCommand(Class<T> command){
        try {
            Constructor<T> constructor = command.getConstructor(GUIEditor.class,UndoManager.class);
            T newInstance = constructor.newInstance(this.editor,this.undoManager);
            return newInstance;
        } catch (InstantiationException ex) {
            Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, "WARING command not created"+ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Action createAction(String name,Command command){
        return new CommandAction(name, command);
    }
    
   
}
