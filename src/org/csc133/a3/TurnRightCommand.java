package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * The TurnRightCommand class to turn the Helicopter right
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class TurnRightCommand extends Command {
    private GameWorld gameWorld;

    TurnRightCommand(GameWorld gameWorld) {
        super("Turn Right");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.assignTickAction('r');
        gameWorld.notifyObservers();
    }
}
