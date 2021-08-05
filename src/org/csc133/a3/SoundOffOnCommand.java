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

public class SoundOffOnCommand extends Command {
    private GameWorld gameWorld;

    SoundOffOnCommand(GameWorld gameWorld) {
        super("Sound On/Off");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // The sound can only be turned on if the game isn't paused
        if (gameWorld.isSoundOff() && !gameWorld.isGamePaused()) {
            gameWorld.turnSoundOn();
        }
        else {
            gameWorld.turnSoundOff();
        }
        gameWorld.notifyObservers();
    }
}
