package jada.ngeditor;

import com.google.common.annotations.Beta;
import jada.ngeditor.controller.CommandProcessor;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GuiEditorModel;
import jada.ngeditor.model.elements.specials.GUseControls;
import jada.ngeditor.model.elements.specials.GUseStyle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
@Beta
public class AddControlsAction extends AbstractAction implements Observer {
    private transient GUI gui;
    
    public AddControlsAction() {
        super();
        CommandProcessor.getInstance().getObservable().addObserver(AddControlsAction.this);
    }
    @Override
    public void actionPerformed(final ActionEvent e) {
        final JFileChooser chooser = new JFileChooser(gui.getAssetFolder());
        final int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            GUseControls controls = new GUseControls();
            controls.setFilename(this.createReletive(chooser.getSelectedFile()));
            gui.addLoadUseControls(controls);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof GuiEditorModel){
            GuiEditorModel meditor = (GuiEditorModel) o;
            gui = meditor.getCurrent();
        }
    }
    
     private String createReletive(File selected) {
        File assets = gui.getAssetFolder();
        String res = "";
       String parentPath = selected.getParent();
       String absAssets = assets.getAbsolutePath();
       if (!parentPath.contains(absAssets)) {
           try {
               absAssets = assets.getCanonicalPath();
               if (!parentPath.contains(absAssets)) {
                   JOptionPane.showMessageDialog(null, "Sorry you can't relativize this file. Tip : the file must be inside the assets folder");
               } else {
                   res = assets.toURI().relativize(selected.toURI()).getPath();
               }
           } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Sorry you can't relativize this file");
           }
       } else {
           res = assets.toURI().relativize(selected.toURI()).getPath();
       }
       return res;      
    }

}