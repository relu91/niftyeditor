/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package jada.ngeditor.renderUtil;

import de.lessvoid.nifty.spi.sound.SoundHandle;

/**
 * No sound required
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
