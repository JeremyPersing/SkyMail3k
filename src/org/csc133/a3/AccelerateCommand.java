package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * The AccelerateCommand class accelerates the helicopter
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class AccelerateCommand extends Command {
    private GameWorld gameWorld;

    AccelerateCommand(GameWorld gameWorld) {
        super("Accelerate");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorld.accelerateHelicopter();
        gameWorld.notifyObservers();
    }
}
