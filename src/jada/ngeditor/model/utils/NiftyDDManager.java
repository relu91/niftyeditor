/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.utils;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.tools.SizeValue;
import jada.ngeditor.model.elements.GElement;
import java.util.LinkedList;

/**
 *
 * @author cris
 */
public class NiftyDDManager {
    public static final String NIFTY_EDITOR_POPUP_SUPPORT = "NiftyEditorPopupSupport";
    private final Element popUp;
    private GElement dragged;
    private final Nifty nifty;
    private int previousX;
    private int previousY;
    private int previousIndex;
    
    public NiftyDDManager(Nifty nifty){
        Element temp = nifty.findPopupByName(NIFTY_EDITOR_POPUP_SUPPORT);
        if(temp == null){
            PopupBuilder builder = new PopupBuilder(NIFTY_EDITOR_POPUP_SUPPORT);
            builder.childLayoutAbsolute();
            builder.registerPopup(nifty);
            temp = nifty.createPopup(NIFTY_EDITOR_POPUP_SUPPORT);
        }
        popUp = temp;
        this.nifty = nifty;
        dragged = null;
    }
    
    
    public void startDrag(final GElement element){
        if(dragged != null){
            throw new IllegalStateException("You can't start more than one drag&drop");
        }
        
        dragged = element;
        nifty.showPopup(nifty.getCurrentScreen(),popUp.getId(), null);
        final Element ele = this.dragged.getNiftyElement();
        final SizeValue width = SizeValue.px(ele.getWidth());
        this.previousX = ele.getX();
        this.previousY = ele.getY();
        this.previousIndex = this.findIndex(dragged);
        final SizeValue height = SizeValue.px(ele.getHeight());
         ele.setConstraintX(SizeValue.px(previousX));
        ele.setConstraintY(SizeValue.px(previousY));
        ele.setConstraintHeight(height);
        ele.setConstraintWidth(width);
        element.getNiftyElement().markForMove(popUp);
    }
    /**
     * Move dragged element around
     * @param x
     * @param y 
     */
    public void dragAround(int x,int y){
            if(dragged == null){
                throw new IllegalStateException("You must start drag before!");
            }
            Element ele = this.dragged.getNiftyElement();
            ele.setConstraintX(SizeValue.px(x-ele.getWidth()/2));
            ele.setConstraintY(SizeValue.px(y-ele.getHeight()/2));
            
            popUp.layoutElements();
    }
    
    public void endDrag(){
        if(dragged == null){
            throw new IllegalStateException("You must start drag before!");
        }
        this.nifty.closePopup(popUp.getId());
        this.dragged = null;
    }
    
    
    public void revertDrag(){
        if(dragged == null){
            throw new IllegalStateException("You must start drag before!");
        }
        
         dragged.getNiftyElement().setConstraintX(SizeValue.px(previousX));
         dragged.getNiftyElement().setConstraintY(SizeValue.px(previousY));
         dragged.getNiftyElement().markForMove(dragged.getParent().getDropContext(),new EndNotify() {

            @Override
            public void perform() {
                dragged.getNiftyElement().setConstraintX(SizeValue.px(previousX));
                dragged.getNiftyElement().setConstraintY(SizeValue.px(previousY));
                dragged.getNiftyElement().setIndex(previousIndex);
                dragged.refresh();
                dragged = null;
            }
        });
         
    }

    private int findIndex(GElement dragged) {
        LinkedList<GElement> elements = dragged.getParent().getElements();
        return elements.indexOf(dragged);
    }
    
}
