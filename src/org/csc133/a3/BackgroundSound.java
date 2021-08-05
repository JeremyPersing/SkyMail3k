package org.csc133.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

/**
 * The BackgroundSound class takes in an audio file and allows that sound to be
 * played on a loop using the play method
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class BackgroundSound implements Runnable{
    private Media media;

    public BackgroundSound(String fileName) {
        try {
            InputStream inputStream = Display.getInstance()
                    .getResourceAsStream(getClass(), "/" + fileName);
            media = MediaManager.createMedia(inputStream, "audio/wav",
                    this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() { media.play(); }

    public void pause() { media.pause(); }

    public void run() {
        media.setTime(0);
        media.play();
    }
}
