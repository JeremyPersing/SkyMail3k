package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * The TurnLeftCommand class to turn the Helicopter left
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class TurnLeftCommand extends Command {
    private GameWorld gameWorld;

    TurnLeftCommand(GameWorld gameWorld) {
        super("Turn Left");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.assignTickAction('l');
        gameWorld.notifyObservers();
    }
}
