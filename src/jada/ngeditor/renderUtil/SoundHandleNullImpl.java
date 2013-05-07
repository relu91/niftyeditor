/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.renderUtil;

import de.lessvoid.nifty.spi.sound.SoundHandle;

/**
 *
 * @author cris
 */
class SoundHandleNullImpl implements SoundHandle {

    public SoundHandleNullImpl() {
    }

    @Override
    public void play() {
        
    }

    @Override
    public void stop() {
       
    }

    @Override
    public void setVolume(float f) {
        
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public boolean isPlaying() {
       return false;
    }

    @Override
    public void dispose() {
        
    }
    
}
