/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller;

import com.google.common.annotations.Beta;
import jada.ngeditor.controller.commands.CommandAction;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * This class is the entry-point for the editor. It holds all the logic.
 * @author cris
 */
public class MainCrontroller {
    private UndoManager undoManager = new UndoManager();
    private final GUIEditor editor;
    private static MainCrontroller instance;
    
    public static MainCrontroller getInstance(){
        if(instance == null){
           instance = new MainCrontroller();
        }
        return instance;
    }
    private LinkedList<Command> list;
    private CompoundEdit compoundEdit;
    
    private MainCrontroller(){
        this.editor = new GUIEditor();
        list = new LinkedList<Command>();
        this.compoundEdit = new CompoundEdit();
    }
    /**
     * Get an Observable that fire the events in the 
     * @see jada.ngeditor.listeners.events
     * @return 
     */
    @Beta
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
    
    public Action createAction(String name,Class<? extends Command> clazz){
        Command command = this.getCommand(clazz);
        return new CommandAction(name, command);
    }
    /**
     * 
     * @param command 
     * @deprecated Cause some Thread related problems with nifty. Use executeCommand instead
     */
    @Deprecated
    public void executeAsynCommand(final Command command){
      SwingWorker worker =  new SwingWorker<Void, Void>(){

            @Override
            protected Void doInBackground() throws Exception {
                command.perform();
                return null;
            }

            @Override
            protected void done() {
                try{
                    this.get();
                }catch(ExecutionException e){
                       Logger.getLogger(MainCrontroller.class.getName()).log(Level.WARNING, null, e);
                     JOptionPane.showMessageDialog(null,"Command "+command.getName()+" failed with this message:"+e.getCause().getMessage(),"Nifty-Editor error",JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException ex) {
                        Logger.getLogger(MainCrontroller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            
        };
      worker.execute();
    }
    
    public void excuteCommand(final Command command) throws Exception{
        command.perform();
        if(command instanceof UndoableEdit){
            this.undoManager.addEdit((UndoableEdit)command);
        }
    }
    /**
     * Batch some undoableEdit so can be reverted at once
     * @param command 
     * NOTE:"Should be changed in mergeCommands."
     */
    public void batchCommand(Command command){
        if(compoundEdit == null){
            compoundEdit = new CompoundEdit();
        }
        this.list.add(command);
        if(command instanceof UndoableEdit){
            this.compoundEdit.addEdit((UndoableEdit)command);
        }
        
    }
    /**
     * Execute all the last commands
     */
    public void executeBatch() throws Exception{
        for(Command c : list){
            c.perform();
        }
        list.clear();
        this.compoundEdit.end();
        this.undoManager.addEdit(compoundEdit);
        this.compoundEdit = null;
    }
    /**
     * 
     * @return
     * @deprecated Use getObserver instead
     */
    @Deprecated
    public GUIEditor getGuiEditor(){
        return this.editor;
    }
   
}
