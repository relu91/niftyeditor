package jada.ngeditor.model.exception;

/**
 * This class rapresent an illegal drop in gui view 
 * Ex: No screen found , no layer found
 * @version 0.5
 * @author Aguzzi Cristiano
 */
public class IllegalDropException extends IllegalArgumentException{
    
    /**
     * Create an illegalDropExcetpion 
     * @see IllegalArgumentException
     * @param mess 
     */
    public IllegalDropException(String mess){
        super(mess);
        
    }
}
