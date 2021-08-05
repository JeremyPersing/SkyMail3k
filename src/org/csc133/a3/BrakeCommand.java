package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * The BrakeCommand class is used for braking the helicopter
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class BrakeCommand extends Command {
    private GameWorld gameWorld;

    BrakeCommand(GameWorld gameWorld) {
        super("Brake");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.brakeHelicopter();
        gameWorld.notifyObservers();
    }
}
