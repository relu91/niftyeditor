/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.exception;


/**
 * Idicates a no product for your request in ElementFactory
 * @see GUIFactory
 * @author Aguzzi Cristiano
 */
public class NoProductException extends Exception{
    private String pr;
    
    public NoProductException(String product){
        super("No product for tag: " + product );
        this.pr=product;
    }
    /**
     * Request for the product not found
     * @return the product name 
     */
    public String getProduct(){
        return this.pr;
    }
}
