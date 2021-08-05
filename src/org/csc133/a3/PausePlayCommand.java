package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * The BrakeCommand class is used for pausing and unpausing the game
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class PausePlayCommand extends Command {
    private GameWorld gameWorld;
    private boolean soundWasOff;

    PausePlayCommand(GameWorld gameWorld) {
        super("Pause/Play");
        this.gameWorld = gameWorld;
        soundWasOff = gameWorld.isSoundOff();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!gameWorld.isGamePaused()) {
            gameWorld.pauseGame();
            if (gameWorld.isSoundOff()) {
                soundWasOff = true;
            }
            else {
                soundWasOff = false;
            }
            gameWorld.turnSoundOff();
        }
        else {
            gameWorld.unPauseGame();
            if (!soundWasOff) {
                gameWorld.turnSoundOn();
            }
        }
        gameWorld.notifyObservers();
    }
}
