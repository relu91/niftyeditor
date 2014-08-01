/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller;

/**
 * 
 * @author cris
 */
public interface Command {
    
    void perform() throws Exception;
    /**
     * A command will return false if in any case it can't perform his operation 
     * with its current data.
     * @return 
     */
    boolean isActive();
    
    public String getName();
    
}
