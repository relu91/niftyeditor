/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.renderUtil;

import de.lessvoid.nifty.sound.SoundSystem;
import de.lessvoid.nifty.spi.sound.SoundDevice;
import de.lessvoid.nifty.spi.sound.SoundHandle;
import de.lessvoid.nifty.tools.resourceloader.NiftyResourceLoader;

/**
 *
 * @author cris
 */
public class SoudDevicenull implements SoundDevice {

    
    public void setResourceLoader(NiftyResourceLoader nrl) {
        
    }

    @Override
    public SoundHandle loadSound(SoundSystem ss, String string) {
        return new SoundHandleNullImpl();
    }

    @Override
    public SoundHandle loadMusic(SoundSystem ss, String string) {
        return new SoundHandleNullImpl();
    }

    @Override
    public void update(int i) {
        
    }
    
}
