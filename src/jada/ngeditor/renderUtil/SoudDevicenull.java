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
